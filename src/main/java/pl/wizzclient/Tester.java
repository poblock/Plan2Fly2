package pl.wizzclient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import pl.wizzclient.model.BestOffer;
import pl.wizzclient.model.Offer;
import pl.wizzclient.model.Offers;
import pl.wizzclient.model.Route;
import pl.wizzclient.offers.OfferBiConsumer;
import pl.wizzclient.offers.OfferComparator;

public class Tester {

	public static void execute() {
    	Route route = new Route("GDN","BGY","W6","ATH","FR","JTR","FR");
    	List<Offers> offers = new ArrayList<>();
    	prepareData(route, offers);
    	System.out.println(route);
    	System.out.println(offers);
    	
    	TreeMap<Double, List<BestOffer>> offersThere = new TreeMap<>();
		TreeMap<Double, List<BestOffer>> offersBack = new TreeMap<>();
		
    	int routeType = getRouteType(route);
    	
    	offers.stream()
		.map(o -> o.getFlightsDep())
		.flatMap(list -> list.stream())
		.sorted(new OfferComparator(routeType, route))
		.collect(
				() -> new HashMap<>(), 
				new OfferBiConsumer(route, false), 
				(map1, map2) -> map1.putAll(map2))
		.forEach((date, list) -> {
			validateOffers(list, routeType, offersThere);
		});
    	System.out.println(offersThere);
    	
    	System.out.println("POWROT");
    	offers.stream()
		.map(o -> o.getFlightsReturn())
		.flatMap(list -> list.stream())
		.sorted(new OfferComparator(routeType, route, true))
		.collect(() -> new HashMap<>(), 
				new OfferBiConsumer(route, true), 
				(map1, map2) -> map1.putAll(map2))
		.forEach((date, list) -> {
			validateOffers(list, routeType, offersBack);
		});
    	System.out.println(offersBack);
	}
	
	private static void prepareData(Route route, List<Offers> offers) {
    	List<Offer> flightsDep1 = new ArrayList<>();
    	flightsDep1.add(new Offer("GDN", "BGY", "W6", "2017-12-12T19:25:00", 39.0));
    	flightsDep1.add(new Offer("GDN", "BGY", "W6", "2017-12-16T19:25:00", 38.6)); 
    	flightsDep1.add(new Offer("GDN", "BGY", "W6", "2017-12-19T19:25:00", 38.6)); 
    	flightsDep1.add(new Offer("GDN", "BGY", "W6", "2017-12-22T19:20:00", 54.6));
    	List<Offer> flightsReturn1 = new ArrayList<>();
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-12T21:55:00", 38.6)); 
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-16T21:55:00", 230.6)); 
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-19T21:55:00", 94.6)); 
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-22T21:55:00", 174.6));
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-23T21:55:00", 174.6));
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-24T21:55:00", 174.6));
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-25T21:55:00", 174.6));
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-26T21:55:00", 174.6));
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-27T21:55:00", 174.6));
    	flightsReturn1.add(new Offer("BGY", "GDN", "W6", "2017-12-28T21:55:00", 174.6));
    	offers.add(new Offers(flightsDep1, flightsReturn1));
    
    	List<Offer> flightsDep2 = new ArrayList<>();
    	flightsDep2.add(new Offer("BGY", "ATH", "FR", "2017-12-12T11:30:00.000", 110.21)); 
    	flightsDep2.add(new Offer("BGY", "ATH", "FR", "2017-12-13T11:30:00.000", 105.8)); 
    	flightsDep2.add(new Offer("BGY", "ATH", "FR", "2017-12-14T11:30:00.000", 151.66)); 
    	flightsDep2.add(new Offer("BGY", "ATH", "FR", "2017-12-15T11:30:00.000", 110.21)); 
    	flightsDep2.add(new Offer("BGY", "ATH", "FR", "2017-12-16T11:30:00.000", 110.21)); 
    	flightsDep2.add(new Offer("BGY", "ATH", "FR", "2017-12-17T11:30:00.000", 110.21)); 
    	flightsDep2.add(new Offer("BGY", "ATH", "FR", "2017-12-18T11:30:00.000", 110.21));
    	List<Offer> flightsReturn2 = new ArrayList<>();
    	flightsReturn2.add(new Offer("ATH", "BGY", "FR", "2017-12-23T09:30:00.000", 321.89)); 
    	flightsReturn2.add(new Offer("ATH", "BGY", "FR", "2017-12-24T09:30:00.000", 158.72)); 
    	flightsReturn2.add(new Offer("ATH", "BGY", "FR", "2017-12-26T10:00:00.000", 268.97)); 
    	flightsReturn2.add(new Offer("ATH", "BGY", "FR", "2017-12-27T09:30:00.000", 110.21)); 
    	flightsReturn2.add(new Offer("ATH", "BGY", "FR", "2017-12-27T15:10:00.000", 110.21)); 
    	flightsReturn2.add(new Offer("ATH", "BGY", "FR", "2017-12-28T09:30:00.000", 132.26)); 
    	flightsReturn2.add(new Offer("ATH", "BGY", "FR", "2017-12-29T09:30:00.000", 158.72)); 
    	flightsReturn2.add(new Offer("ATH", "BGY", "FR", "2017-12-29T18:10:00.000", 110.21)); 
    	offers.add(new Offers(flightsDep2, flightsReturn2));
    	
    	List<Offer> flightsDep3 = new ArrayList<>();
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-12T06:55:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-12T20:20:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-13T06:55:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-13T18:50:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-14T06:55:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-14T20:20:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-15T06:55:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-15T18:50:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-16T06:55:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-16T20:20:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-17T06:55:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-17T20:20:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-18T06:55:00.000", 73.12)); 
    	flightsDep3.add(new Offer("ATH", "JTR", "FR", "2017-12-18T20:20:00.000", 73.12));
    	List<Offer> flightsReturn3 = new ArrayList<>();
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-23T08:15:00.000", 153.07)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-23T21:40:00.000", 120.26)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-24T08:15:00.000", 153.07)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-26T21:40:00.000", 153.07)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-27T08:15:00.000", 92.92)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-27T20:10:00.000", 92.92)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-28T08:15:00.000", 73.12)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-28T21:40:00.000", 73.12)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-29T08:15:00.000", 120.26)); 
    	flightsReturn3.add(new Offer("JTR", "ATH", "FR", "2017-12-29T20:10:00.000", 120.26));
    	offers.add(new Offers(flightsDep3, flightsReturn3));
    }
    
    private static int getRouteType(Route route) {
		if(route!=null) {
			String t1 = route.getAirportTransfer1();
			String t2 = route.getAirportTransfer2();
			if(t1!=null && !t1.equals("NULL")) {
				if(t2!=null && !t2.equals("NULL")) {
					return 2;
				} else {
					return 1;
				}
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}
    

    private static void validateOffers(List<Offer> list, int type, TreeMap<Double, List<BestOffer>> offersMap) {
    	if(list.size() == type+1) {
			double price0 = list.stream().mapToDouble(Offer::getPrice).sum();
			BigDecimal bd = new BigDecimal(price0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			double price = bd.doubleValue();
			BestOffer bo = new BestOffer(price, list);
			if(offersMap.containsKey(price)) {
				offersMap.get(price).add(bo);
			} else {
				List<BestOffer> oferty = new LinkedList<>();
				oferty.add(bo);
				offersMap.put(price, oferty);
			}
		}
    }
    
}
