package pl.wizzclient.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import pl.wizzclient.model.Currency;

@Cacheable("currencies")
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
	List<Currency> findAll();
	Currency findByCode(String code);
}

