package pl.wizzclient;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import pl.wizzclient.model.Airline;
import pl.wizzclient.model.Airport;
import pl.wizzclient.model.Currency;
import pl.wizzclient.model.Route;
import pl.wizzclient.repository.AirlineRepository;
import pl.wizzclient.repository.AirportRepository;
import pl.wizzclient.repository.CurrencyRepository;
import pl.wizzclient.repository.RouteRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public AirportRepository airports;

    @Autowired
    public RouteRepository routes;
   
    @Autowired
    public AirlineRepository airlines;

    @Autowired
    public CurrencyRepository currencies;
  
    @Bean
    public FlatFileItemReader<Currency> readerCurrency() {
        FlatFileItemReader<Currency> reader = new FlatFileItemReader<Currency>();
        reader.setResource(new ClassPathResource("currencies.csv"));
        reader.setEncoding("UTF-8");
        reader.setLineMapper(new DefaultLineMapper<Currency>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "currencyCode", "currencyRate" });
            }});
        	
            setFieldSetMapper(new FieldSetMapper<Currency>() {
				@Override
				public Currency mapFieldSet(FieldSet fieldSet) throws BindException {
					String code = fieldSet.readString(0);
					double rate = fieldSet.readDouble(1);				
					return new Currency(code, rate);
				}
            	
            });
        }});
        return reader;
    }

    @Bean
    public RepositoryItemWriter<Currency> writerCurrency() {
    	RepositoryItemWriter<Currency> writer = new RepositoryItemWriter<Currency>();
    	writer.setRepository(currencies);
    	writer.setMethodName("save");
        return writer;
	}
    
    @Bean
    public FlatFileItemReader<Route> readerRoute() {
        FlatFileItemReader<Route> reader = new FlatFileItemReader<Route>();
        reader.setResource(new ClassPathResource("routes.csv"));
        reader.setEncoding("UTF-8");
        reader.setLineMapper(new DefaultLineMapper<Route>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "airportDeparture", "airportTransfer1", "airlineTransfer1",	"airportTransfer2",
                    	"airlineTransfer2", "airportDestination", "airlineDestination" });
            }});
            
        	
            setFieldSetMapper(new FieldSetMapper<Route>() {

				@Override
				public Route mapFieldSet(FieldSet fieldSet) throws BindException {
					String airportDeparture = fieldSet.readString(0);
					String airportTransfer1 = fieldSet.readString(1);
					String airlineTransfer1 = fieldSet.readString(2);
					String airportTransfer2 = fieldSet.readString(3);
					String airlineTransfer2 = fieldSet.readString(4);
					String airportDestination = fieldSet.readString(5);
					String airlineDestination = fieldSet.readString(6);					
					return new Route(airportDeparture, 
							airportTransfer1, airlineTransfer1, 
							airportTransfer2, airlineTransfer2, 
							airportDestination, airlineDestination);
				}
            	
            });
        }});
        return reader;
    }

    @Bean
    public RepositoryItemWriter<Route> writerRoute() {
    	RepositoryItemWriter<Route> writer = new RepositoryItemWriter<Route>();
    	writer.setRepository(routes);
    	writer.setMethodName("save");
        return writer;
	}

    
    @Bean
    public FlatFileItemReader<Airport> readerAirport() {
        FlatFileItemReader<Airport> reader = new FlatFileItemReader<Airport>();
        reader.setResource(new ClassPathResource("airports.csv"));
        reader.setEncoding("UTF-8");
        reader.setLineMapper(new DefaultLineMapper<Airport>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "airportName", "airportCountry", "airportCode", "airportLat", "airportLon" });
            }});
            setFieldSetMapper(new FieldSetMapper<Airport>() {

				@Override
				public Airport mapFieldSet(FieldSet fieldSet) throws BindException {
					String country = fieldSet.readString(1);
					String name = fieldSet.readString(0);
					String code = fieldSet.readString(2);
					String lat = fieldSet.readString(3);
					String lon = fieldSet.readString(4);
					return new Airport(code, name, country, lat, lon);
				}
            	
            });
        }});
        return reader;
    }

    @Bean
    public RepositoryItemWriter<Airline> writerAirline() {
    	RepositoryItemWriter<Airline> writer = new RepositoryItemWriter<Airline>();
    	writer.setRepository(airlines);
    	writer.setMethodName("save");
        return writer;
	}

    @Bean
    public FlatFileItemReader<Airline> readerAirline() {
    	FlatFileItemReader<Airline> reader = new FlatFileItemReader<Airline>();
        reader.setResource(new ClassPathResource("airlines.csv"));
        reader.setEncoding("UTF-8");
        reader.setLineMapper(new DefaultLineMapper<Airline>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "airlineName", "airlineCode" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Airline>() {{
                setTargetType(Airline.class);
            }});
        }});
        return reader;
	}

    @Bean
    public RepositoryItemWriter<Airport> writerAirport() {
    	RepositoryItemWriter<Airport> writer = new RepositoryItemWriter<>();
    	writer.setRepository(airports);
    	writer.setMethodName("save");
        return writer;
    }
    
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importAirportsData")
                .incrementer(new RunIdIncrementer())
                .flow(step2Airports())
                .next(step3Airlines())
                .next(step4Routes())
                .next(step5Currencies())
                .end()
                .build();
    }
    
    @Bean
    public Step step2Airports() {
        return stepBuilderFactory.get("step2")
                .<Airport, Airport> chunk(10)
                .reader(readerAirport())
                .writer(writerAirport())
                .build();
    }
    
    @Bean
    public Step step3Airlines() {
        return stepBuilderFactory.get("step3")
                .<Airline, Airline> chunk(10)
                .reader(readerAirline())
                .writer(writerAirline())
                .build();
    }

    @Bean
    public Step step4Routes() {
        return stepBuilderFactory.get("step4")
                .<Route, Route> chunk(10)
                .reader(readerRoute())
                .writer(writerRoute())
                .build();
    }
    
    @Bean
    public Step step5Currencies() {
        return stepBuilderFactory.get("step5")
                .<Currency, Currency> chunk(10)
                .reader(readerCurrency())
                .writer(writerCurrency())
                .build();
    }
}
