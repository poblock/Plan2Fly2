package pl.wizzclient.offers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;

import pl.wizzclient.model.Offer;
import pl.wizzclient.model.Route;

public class OfferBiConsumer implements BiConsumer<Map<String, List<Offer>>,Offer> {

	private boolean isReturnTrip;
	private Route route;
	
	public OfferBiConsumer(Route route, boolean isReturnTrip) {
		this.route = route;
		this.isReturnTrip = isReturnTrip;
	}
	
	private boolean isFirstStage(Offer offer) {
		String rDestinaton = route.getAirportDestination();
		String rDeparture = route.getAirportDeparture();
		return offer.getDeparture().equals(isReturnTrip ? rDestinaton : rDeparture);
	}
	
	@Override
	public void accept(Map<String, List<Offer>> map, Offer offer) {
		if(isFirstStage(offer)) {
			List<Offer> list = new ArrayList<>();
			list.add(offer);
			map.put(offer.getFlightDate(), list);
		} else {
			String oDeparture = offer.getDeparture();
			String flightDate = offer.getFlightDate();
			LocalDateTime candidate = new LocalDateTime(flightDate);
			Iterator<Entry<String, List<Offer>>> it = map.entrySet().iterator();
			String foundKey = null;
			while(it.hasNext()) {
				Entry<String, List<Offer>> entry = it.next();
				
				List<Offer> offers = entry.getValue();
				Offer last = offers.get(offers.size()-1);
				if(last.getDestination().equals(oDeparture)) {
					LocalDateTime accepted = new LocalDateTime(last.getFlightDate());
					int days = Days.daysBetween(accepted, candidate).getDays();
					int hours = Hours.hoursBetween(accepted, candidate).getHours();
					if(candidate.isAfter(accepted) && (days==0 || days==1) && (hours > 2)) {
						foundKey = entry.getKey();
						break;
					}
				}
			}
			
			if(foundKey!=null) {
				map.get(foundKey).add(offer);
			}
		}
	}

}
