package pl.wizzclient.wizz;

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
import pl.wizzclient.wizz.response.WizzFlight;
import pl.wizzclient.wizz.response.WizzPrice;
import pl.wizzclient.wizz.response.WizzResponse;

@Service
public class WizzDecoder implements IWizzDecoder {

	private ObjectMapper objectMapper;
	
	@Autowired
	CurrencyRepository currencies;
	
	@Autowired
	public WizzDecoder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public Offers decode(String response) {
		if(response!=null) {
			try {
				WizzResponse wResponse = objectMapper.readValue(response, WizzResponse.class);
				return wizzAdapter(wResponse);
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

	private Offers wizzAdapter(WizzResponse response) {
		if(response!=null) {
			List<Offer> there = adaptWizzFlights(response.getOutboundFlights());
			List<Offer> back = adaptWizzFlights(response.getReturnFlights());
			return new Offers(there, back);
		}
		return null;
	}
	
	private List<Offer> adaptWizzFlights(List<WizzFlight> list) {
		if(list!=null && list.size()>0) {
			List<Offer> adapter = new ArrayList<>();
			for(WizzFlight wf : list) {
				Offer o = new Offer(wf.getDepartureStation(), wf.getArrivalStation(), ConnParams.WIZZAIR);
				String wizzDate = wf.getDepartureDates().get(0);
				o.setFlightDate(wizzDate);
				WizzPrice price = wf.getPrice();
				double rate = 1.0;
				String currency = price.getCurrencyCode();
				if(!currency.equals(Decoder.polishCurrency)) {
					Currency result = currencies.findByCode(currency);
					if(result!=null) {
						rate = result.getRate();
					} else {
						System.err.println("WRONG CURRENCY, NOT IN REPOSITORY "+currency);
						System.err.println("WRONG CURRENCY "+wf);
					}
				}
				BigDecimal rated = new BigDecimal(price.getAmount()*rate).setScale(2, BigDecimal.ROUND_HALF_DOWN);
				o.setPrice(rated.doubleValue());
				
				adapter.add(o);
			}
			return adapter;
		}
		return null;
	}
}
