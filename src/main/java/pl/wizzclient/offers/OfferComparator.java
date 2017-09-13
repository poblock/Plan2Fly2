package pl.wizzclient.offers;

import java.util.Comparator;

import pl.wizzclient.model.Offer;
import pl.wizzclient.model.Route;

public class OfferComparator implements Comparator<Offer> {

	private int routeType;
	private Route route;
	private final boolean isReversed;
	public OfferComparator(int routeType, Route route, boolean isReversed) {
		this.routeType = routeType;
		this.route = route;
		this.isReversed = isReversed;
	}
	
	public OfferComparator(int routeType, Route route) {
		this.routeType = routeType;
		this.route = route;
		this.isReversed = false;
	}
	
	@Override
	public int compare(Offer o1, Offer o2) {
		if(isReversed) {
			Integer s1 = getReturnStage(o1);
			Integer s2 = getReturnStage(o2);
			return s1.compareTo(s2);
		} else {
			Integer s1 = getStage(o1);
			Integer s2 = getStage(o2);
			return s1.compareTo(s2);
		}
	}

	private int getStage(Offer offer) {
		int result = -1;
		String departure = offer.getDeparture();
		if(departure.equals(route.getAirportDeparture())) {
			result = 0;
		} else if(routeType == 1) {
			if(departure.equals(route.getAirportTransfer1())) {
				result = 1;
			}
		} else if(routeType == 2) {
			if(departure.equals(route.getAirportTransfer2())) {
				result = 2;
			} else if(departure.equals(route.getAirportTransfer1())) {
				result = 1;
			}
		}
		return result;
	}
	
	private int getReturnStage(Offer offer) {
		int result = -1;
		String departure = offer.getDeparture();
		if(departure.equals(route.getAirportDestination())) {
			result = 0;
		} else if(routeType == 1) {
			if(departure.equals(route.getAirportTransfer1())) {
				result = 1;
			}
		} else if(routeType == 2) {
			if(departure.equals(route.getAirportTransfer2())) {
				result = 1;
			} else if(departure.equals(route.getAirportTransfer1())) {
				result = 2;
			}
		}
		return result;
	}
}
