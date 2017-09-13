package pl.wizzclient.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import pl.wizzclient.model.Route;

@Cacheable("routes")
public interface RouteRepository extends CrudRepository<Route, Long> {
	List<Route> findAll();
	List<Route> findByAirportDepartureAndAirportDestination(String airportDeparture, String airportDestination);
}
