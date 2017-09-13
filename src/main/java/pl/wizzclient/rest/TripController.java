package pl.wizzclient.rest;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import pl.wizzclient.FlightManager;
import pl.wizzclient.model.BestOffers;
import pl.wizzclient.model.Route;

@RestController
public class TripController {

	@Autowired
	FlightManager manager;
	
	@RequestMapping("/routes")
    public Set<Route> prepare(@RequestParam(value="dep") String departure,
    		@RequestParam(value="dest") String destination) {
		Set<Route> routes = manager.prepareParameters(departure, destination);
		return routes;
	}
	
    @RequestMapping("/trip")
    public DeferredResult<BestOffers> searchTrip(@RequestParam(value="dep") String departure,
    		@RequestParam(value="dest") String destination, 
    		@RequestParam(value="from") String from,
    		@RequestParam(value="to") String to) {
    	DeferredResult<BestOffers> message = new DeferredResult<>();
    	CompletableFuture
    		.supplyAsync(new Supplier<BestOffers>() {
				@Override
				public BestOffers get() {
					return manager.supplyBestOffers(departure, destination, from, to);
				}  			
    		})
    		.whenCompleteAsync((result, throwable) -> message.setResult(result));
        return message;
    }
}