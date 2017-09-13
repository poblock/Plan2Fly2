package pl.wizzclient.ryan;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.wizzclient.connection.ConnParams;
import pl.wizzclient.decoder.Decoder;
import pl.wizzclient.model.Currency;
import pl.wizzclient.model.Offer;
import pl.wizzclient.model.Offers;
import pl.wizzclient.repository.CurrencyRepository;
import pl.wizzclient.ryan.response.Date;
import pl.wizzclient.ryan.response.Fare;
import pl.wizzclient.ryan.response.Flight;
import pl.wizzclient.ryan.response.RyanResponse;
import pl.wizzclient.ryan.response.Trip;

@Service
public class RyanDecoder implements IRyanDecoder {

	private ObjectMapper objectMapper;
	
	@Autowired
	CurrencyRepository currencies;
	
	@Autowired
	public RyanDecoder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Offers decode(String response) {
		if(response!=null) {
			try {
				RyanResponse rResponse = objectMapper.readValue(response, RyanResponse.class);
				return ryanAdapter(rResponse);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private Offers ryanAdapter(RyanResponse response) {
		if(response!=null) {
			String currency = response.getCurrency();
			if(currency!=null) {
				double rate = 1.0;
				if(!currency.equals(Decoder.polishCurrency)) {
					Currency result = currencies.findByCode(currency);
					if(result!=null) {
						rate = result.getRate();
					} else {
						System.err.println("WRONG CURRENCY, NOT IN REPOSITORY "+currency);
						System.err.println(response);
					}
				}
				List<Trip> trips = response.getTrips();
				if(trips!=null && trips.size()==2) {
					List<Offer> there = ryanTripAdapter(trips.get(0), rate);
					List<Offer> back = ryanTripAdapter(trips.get(1), rate);
					return new Offers(there, back);
				} else {
					System.err.println("WRONG TRIPS "+trips);
					System.err.println(response);
				}
			} else {
				System.err.println("WRONG CURRENCY "+currency);
				System.err.println(response);
			}
		}
		return null;
	}
	
	private List<Offer> ryanTripAdapter(Trip trip, double rate) {
		List<Date> dates = trip.getDates();
		List<Offer> offers = new ArrayList<>();
		for(Date ryanDate : dates) {
			List<Flight> ryanFlights = ryanDate.getFlights();
			if(ryanFlights!=null && ryanFlights.size()>0) {
				for(Flight ryanFlight : ryanFlights) {
					Offer offer = new Offer(trip.getOrigin(), trip.getDestination(), ConnParams.RYANAIR);
					offer.setFlightDate(ryanFlight.getTime().get(0));
					
					List<Fare> fares = ryanFlight.getRegularFare().getFares();
					if(fares.size()==1) {
						 double price = fares.get(0).getAmount();
						 BigDecimal rated = new BigDecimal(price*rate).setScale(2, BigDecimal.ROUND_HALF_DOWN);
						 offer.setPrice(rated.doubleValue());
					} else {
						System.err.println("WRONG FARES "+fares);
						System.err.println(trip);
					}
					offers.add(offer);
				}
			}
		}
		return offers;
	}
}
