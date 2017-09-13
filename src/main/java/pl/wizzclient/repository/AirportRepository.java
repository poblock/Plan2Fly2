package pl.wizzclient.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.wizzclient.model.Airport;


@Repository
@Cacheable("airports")
public interface AirportRepository extends CrudRepository<Airport, Long> {
	List<Airport> findAll();
	List<Airport> findByAirportCodeIn(List<String> codes);
	List<Airport> findByCountryCodeIn(List<String> codes);
	Airport findByAirportCode(String airportCode);
}
