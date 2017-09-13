package pl.wizzclient.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import pl.wizzclient.model.Airline;

@Cacheable("airlines")
public interface AirlineRepository extends CrudRepository<Airline, Long> {
	List<Airline> findAll();
	Airline findByAirlineCode(String code);
}
