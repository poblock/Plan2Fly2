package pl.wizzclient.wizz;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.wizzclient.connection.ConnParams;
import pl.wizzclient.connection.IConnector;
import pl.wizzclient.wizz.request.JsonRequest;
import pl.wizzclient.wizz.request.WizzFlightRequest;

public class WizzConnection implements IConnector {

	private static final String URL = "https://be.wizzair.com/7.0.0/Api/search/timetable";
	private CloseableHttpClient httpclient;
	private ObjectMapper objectMapper;
	
	public WizzConnection(CloseableHttpClient httpclient) {
		this.httpclient = httpclient;
		this.objectMapper = new ObjectMapper();
	}
	
	@Override
	public String connect(String jsonString) throws Exception {	
		String response = null;
		HttpPost httppost = new HttpPost(URL);
		httppost.addHeader("accept", ConnParams.ACCEPT);
		httppost.addHeader("accept-encoding", ConnParams.ACCEPT_ENCODING);
		httppost.addHeader("content-type", ConnParams.CONTENT_TYPE);
		httppost.addHeader("origin", "https://wizzair.com");
		httppost.addHeader("user-agent", ConnParams.USER_AGENT);
		
		StringEntity entity = new StringEntity(jsonString, ContentType.create(ConnParams.CONTENT_TYPE, ConnParams.CHARSET));
		httppost.setEntity(entity);		
		CloseableHttpResponse httpResponse = httpclient.execute(httppost);
        try {
            response = EntityUtils.toString(httpResponse.getEntity());
        } finally {
        	httpResponse.close();
        }
        return response;
	}

	@Override
	public String prepareRequest(String departure, String destination, String from, String to) {
		WizzFlightRequest first = new WizzFlightRequest();
		first.setDepartureStation(departure);
		first.setArrivalStation(destination);
		first.setFrom(from); first.setTo(to);
		WizzFlightRequest second = new WizzFlightRequest();
		second.setDepartureStation(destination);
		second.setArrivalStation(departure);
		second.setFrom(from); second.setTo(to);
		List<WizzFlightRequest> list = new ArrayList<>();
		list.add(first);
		list.add(second);
		JsonRequest request = new JsonRequest();
		request.setFlightList(list);
		request.setPriceType("regular");
		StringWriter stringWriter = new StringWriter();
		try {
			objectMapper.writeValue(stringWriter, request);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}
}
