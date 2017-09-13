package pl.wizzclient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wizzclient.connection.Connection;
import pl.wizzclient.decoder.Decoder;
import pl.wizzclient.model.BestOffer;
import pl.wizzclient.model.BestOffers;
import pl.wizzclient.model.Offer;
import pl.wizzclient.model.Offers;
import pl.wizzclient.model.Route;
import pl.wizzclient.model.SingleFlight;
import pl.wizzclient.offers.OfferBiConsumer;
import pl.wizzclient.offers.OfferComparator;
import pl.wizzclient.repository.RouteRepository;

@Service
public class FlightManager {

	@Autowired
	Connection connection;
	
	@Autowired
	RouteRepository routes;
	
	@Autowired
	Decoder decoder;
	
	public Set<Route> prepareParameters(String departure, String destination) {
		HashSet<Route> allRoutes = new HashSet<>();
		List<Route> result = routes.findByAirportDepartureAndAirportDestination(departure, destination);
		List<Route> another = routes.findByAirportDepartureAndAirportDestination(destination, departure);
		if(result!=null && result.size()>0) {
			for(Route route : result) {
				if(!allRoutes.contains(route)) {
					allRoutes.add(route);
				}
			}
		}
		if(another!=null && another.size()>0) {
			for(Route reverted : another) {
				Route route = new Route(reverted.getAirportDestination(),
						reverted.getAirportTransfer2(), reverted.getAirlineTransfer2(),
						reverted.getAirportTransfer1(), reverted.getAirlineTransfer1(),
						reverted.getAirportDeparture(), reverted.getAirlineDestination());
				if(!allRoutes.contains(route)) {
					allRoutes.add(route);
				}
			}
		}
		return allRoutes;
	}
	
	public BestOffers supplyBestOffers(String departure, String destination, String from, String to) {
		try {
			Set<Route> allRoutes = prepareParameters(departure, destination);
			System.out.println("ALL ROUTES :"+allRoutes);
			Map<SingleFlight, List<Long>> uniqueMap = prepareMap(allRoutes, from, to);
			System.out.println("UNIQUE: "+uniqueMap);
			Map<SingleFlight, String> reqMap = connection.prepareRequestsMap(uniqueMap,from,to);
			System.out.println("REQ: "+reqMap);
			Map<SingleFlight, String> resMap = connection.connect(reqMap);
			System.out.println("RES: "+resMap);
			Map<SingleFlight, Offers> offersMap = prepareOffersMap(resMap);
			System.out.println("OFFERS: "+offersMap);
			Map<Route, List<Offers>> results = combineOffersWithRoutes(allRoutes, uniqueMap, offersMap);
			System.out.println("RESULTS "+results);
			BestOffers bestOffers = processResults(results);
			System.out.println(bestOffers);
			return bestOffers;
		} catch(Exception e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	private BestOffers processResults(Map<Route, List<Offers>> results) {
		TreeMap<Double, List<BestOffer>> offersThere = new TreeMap<>();
		TreeMap<Double, List<BestOffer>> offersBack = new TreeMap<>();
		
		if(results!=null && results.size()>0) {
			Iterator<Entry<Route, List<Offers>>> it = results.entrySet().iterator();
			while(it.hasNext()) {
				Entry<Route, List<Offers>> entry = it.next();
				Route route = entry.getKey();
				List<Offers> offers = entry.getValue();
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
						validateOffers(list, route, routeType, offersThere);
					});
		    	
				// ---- RETURN ----
		    	offers.stream()
					.map(o -> o.getFlightsReturn())
					.flatMap(list -> list.stream())
					.sorted(new OfferComparator(routeType, route, true))
					.collect(() -> new HashMap<>(), 
						new OfferBiConsumer(route, true), 
						(map1, map2) -> map1.putAll(map2))
					.forEach((date, list) -> {
						validateOffers(list, route, routeType, offersBack);
					});
			}
		}
		
		List<BestOffer> there = offersThere.values().stream().flatMap(list -> list.stream()).collect(Collectors.toList());
		List<BestOffer> back = offersBack.values().stream().flatMap(list->list.stream()).collect(Collectors.toList());
		return new BestOffers(there, back);
	}
	
	private int getRouteType(Route route) {
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
	
	private void validateOffers(List<Offer> list, Route route, int type, TreeMap<Double, List<BestOffer>> offersMap) {
    	if(list.size() == type+1) {
			double price0 = list.stream().mapToDouble(Offer::getPrice).sum();
			BigDecimal bd = new BigDecimal(price0).setScale(2, BigDecimal.ROUND_HALF_DOWN);
			double price = bd.doubleValue();
			BestOffer bo = new BestOffer(route, price, list);
			if(offersMap.containsKey(price)) {
				offersMap.get(price).add(bo);
			} else {
				List<BestOffer> oferty = new LinkedList<>();
				oferty.add(bo);
				offersMap.put(price, oferty);
			}
		}
    }
	
	private Map<Route, List<Offers>> combineOffersWithRoutes(Set<Route> allRoutes, Map<SingleFlight, List<Long>> uniqueMap,
			Map<SingleFlight, Offers> offersMap) {
		Map<Route, List<Offers>> results = new HashMap<>();
		Iterator<Entry<SingleFlight, List<Long>>> it = uniqueMap.entrySet().iterator();
		while(it.hasNext()) {
			Entry<SingleFlight, List<Long>> uniqueF = it.next();
			SingleFlight sFlight = uniqueF.getKey();
			List<Long> listID = uniqueF.getValue();
			Offers offers = offersMap.get(sFlight);
			if(offers!=null) {
				for(Long id : listID) {
					Route route = routes.findOne(id);
					if(!results.containsKey(route)) {
						List<Offers> list = new ArrayList<>();
						list.add(offers);
						results.put(route, list);
					} else {
						results.get(route).add(offers);
					}
				}
			}
		}
		System.out.println(results);
		return results;
	}

	private Map<SingleFlight, Offers> prepareOffersMap(Map<SingleFlight, String> resMap) {
		if(resMap!=null && resMap.size()>0) {
			Map<SingleFlight, Offers> offersMap = new HashMap<>();
			Iterator<Entry<SingleFlight, String>> it = resMap.entrySet().iterator();
			while(it.hasNext()) {
				Entry<SingleFlight, String> entry = it.next();
				String airline = entry.getKey().getAirline();
				String response = entry.getValue();
				Offers offers = decoder.decode(airline, response);
				offersMap.put(entry.getKey(), offers);
			}
			return offersMap;
		}
		return null;
	}
	
	private Map<SingleFlight, List<Long>> prepareMap(Set<Route> allRoutes, String from, String to) {
		Map<SingleFlight, List<Long>> uniqueMap = new HashMap<>();
		Iterator<Route> it = allRoutes.iterator();
		while(it.hasNext()) {
			Route route = it.next();
			SingleFlight sf1 = prepareSingleFlight(route.getAirportDeparture(), route.getAirportTransfer1(), route.getAirlineTransfer1(), from, to);
			SingleFlight sf2 = null;
			SingleFlight sf = null;
			if(sf1!=null) {
				sf2 = prepareSingleFlight(route.getAirportTransfer1(), route.getAirportTransfer2(), route.getAirlineTransfer2(), from, to);
				if(sf2!=null) {
					sf = prepareSingleFlight(route.getAirportTransfer2(), route.getAirportDestination(), route.getAirlineDestination(), from, to);
				} else {
					sf = prepareSingleFlight(route.getAirportTransfer1(), route.getAirportDestination(), route.getAirlineDestination(), from, to);
				}
			} else {
				sf = prepareSingleFlight(route.getAirportDeparture(), route.getAirportDestination(), route.getAirlineDestination(), from, to);
			}
			addToMap(uniqueMap, route, sf);
			addToMap(uniqueMap, route, sf1);
			addToMap(uniqueMap, route, sf2);
		}
		return uniqueMap;
	}
	
	private void addToMap(Map<SingleFlight, List<Long>> uniqueMap, Route route, SingleFlight sf) {
		if(sf!=null) {
			long id = route.getId();
			if(uniqueMap.containsKey(sf)) {
				uniqueMap.get(sf).add(id);
			} else {
				LinkedList<Long> list = new LinkedList<Long>();
				list.add(id);
				uniqueMap.put(sf, list);
			}
		}
	}
	
	private SingleFlight prepareSingleFlight(String departure, String destination, String airline, String from, String to) {
		if(destination!=null && !destination.equals("NULL")) {
			return new SingleFlight(departure, destination, airline, from, to);
		}
		return null;
	}
//	private BestOffers processResponse(WizzResponse lot) {
//		List<BestOffer> list = new ArrayList<>();
//		List<WizzFlight> lotyTam = lot.getOutboundFlights();
//		Collections.sort(lotyTam, new WizzFlightComparator());
//		for(WizzFlight wf : lotyTam) {
//			BestOffer bo = convert(wf);
//			list.add(bo);
//		}
//		list.add(new BestOffer(Double.MAX_VALUE, "XXXXXXXXX"));
//		List<WizzFlight> lotyPowrot = lot.getReturnFlights();
//		Collections.sort(lotyPowrot, new WizzFlightComparator());
//		for(WizzFlight wf : lotyPowrot) {
//			BestOffer bo = convert(wf);
//			list.add(bo);
//		}
//		return new BestOffers(list);
//	}
	
//	private BestOffer convert(WizzFlight wf) {
//		double cena = wf.getPrice().getAmount();
//		StringBuffer sb = new StringBuffer();
//		sb.append("Dates: "+wf.getDepartureDates().toString()+" ");
//		return new BestOffer(cena, sb.toString());
//	}
}
