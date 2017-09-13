package pl.wizzclient.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.wizzclient.model.Airport;
import pl.wizzclient.model.Route;
import pl.wizzclient.model.TripForm;
import pl.wizzclient.repository.AirportRepository;
import pl.wizzclient.repository.RouteRepository;

@Controller
public class WebController {
	
	@Autowired
	RouteRepository routes;
	
	@Autowired
	AirportRepository airports;
	
	@PostMapping("/")
    String check(@Valid TripForm tripForm, BindingResult bindingResult) {
		System.out.println(tripForm);
		System.out.println(bindingResult);
		return "redirect:/hello";
	}
	
	@GetMapping("/")
	String index(TripForm tripForm, Map<String, Object> model) {
		ArrayList<String> outputFrom = new ArrayList<String>();
		Map<String,List<String>> allR = new HashMap<>();
		
		Map<String,List<String>> routesDetails = new HashMap<>();
		
		outputFrom.add(" ");
		
		List<Route> all = routes.findAll();
		for(Route route : all) {
			String from = getAirportString(route.getAirportDeparture());
			String to = getAirportString(route.getAirportDestination());
			if(!outputFrom.contains(from)) {
				outputFrom.add(from);
			}
			
			if(!allR.containsKey(from)) {
				List<String> list = new ArrayList<>();
				list.add(to);
				allR.put(from, list);
			} else {
				List<String> list = allR.get(from);
				if(!list.contains(to)) {
					list.add(to);
				}
			}
			
			String details = convertRoute(route);
			if(!routesDetails.containsKey(from+","+to)) {
				List<String> list = new ArrayList<>();
				list.add(details);
				routesDetails.put(from+","+to, list);
			} else {
				List<String> list = routesDetails.get(from+","+to);
				list.add(details);
			}
		}

		model.put("recordsFrom", outputFrom);
		model.put("allRoutes", allR);
		model.put("routesDetails", routesDetails);
		return "index";
	}
	
	private String getAirportString(String code) {
		Airport a = airports.findByAirportCode(code);
		if(a!=null) {
			return a.getAirportName()+" ("+a.getAirportCode()+")";
		}
		return null;
	}
	
	private String convertRoute(Route route) {
		int type = getRouteType(route);
		String result = null;
		if(type==0) {
			result = route.getAirlineDestination();
		} else if(type==1) {
			result = getAirportString(route.getAirportTransfer1())
					+","+route.getAirlineTransfer1()
					+","+route.getAirlineDestination();
		} else if(type==2) {
			result = getAirportString(route.getAirportTransfer1())
					+","+route.getAirlineTransfer1()
					+","+getAirportString(route.getAirportTransfer2())
					+","+route.getAirlineTransfer2()
					+","+route.getAirlineDestination();
		}
		return result;
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
}
