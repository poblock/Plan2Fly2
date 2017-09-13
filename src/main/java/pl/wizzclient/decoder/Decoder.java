package pl.wizzclient.decoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.wizzclient.connection.ConnParams;
import pl.wizzclient.model.Offers;
import pl.wizzclient.ryan.IRyanDecoder;
import pl.wizzclient.wizz.IWizzDecoder;

@Service
public class Decoder {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private IWizzDecoder wizzDecoder;
	
	@Autowired
	private IRyanDecoder ryanDecoder;
	
	public static final String polishCurrency = "PLN";
	
	public Offers decode(String airline, String response) {
		if(airline.equals(ConnParams.WIZZAIR)) {
			return wizzDecoder.decode(response);
		} else if(airline.equals(ConnParams.RYANAIR)) {
			return ryanDecoder.decode(response);
		}
		return null;
	}
}
