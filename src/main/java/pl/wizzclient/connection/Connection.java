package pl.wizzclient.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Service;

import pl.wizzclient.model.SingleFlight;
import pl.wizzclient.ryan.RyanConnection;
import pl.wizzclient.wizz.WizzConnection;

@Service
public class Connection {

	private PoolingHttpClientConnectionManager cm;
	private CloseableHttpClient httpclient;
	private BasicCookieStore cookieStore;
	private IConnector wizzConnector;
	private IConnector ryanConnector;
	
	public Connection() {
		try {
			cm = new PoolingHttpClientConnectionManager();
	        cm.setMaxTotal(100);
	        cookieStore = new BasicCookieStore();
	        httpclient = HttpClients.custom()
	        		.setDefaultCookieStore(cookieStore)
	        		.setConnectionManager(cm).build();
	        wizzConnector = new WizzConnection(httpclient); // TODO Factory
	        ryanConnector = new RyanConnection(httpclient); // TODO
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private List<Callable<String>> createTasks(Map<SingleFlight, String> reqMap) throws Exception {
		List<Callable<String>> tasks = new ArrayList<>();
		Iterator<Entry<SingleFlight, String>> it = reqMap.entrySet().iterator();
		while(it.hasNext()) {
			Entry<SingleFlight, String> entry = it.next();
			SingleFlight flight = entry.getKey();
			String request = entry.getValue();
			String airline = flight.getAirline();
			
			Callable<String> callable = new Callable<String>(){
				@Override
				public String call() throws Exception {
					if(airline.equals(ConnParams.WIZZAIR)) {
						return wizzConnector.connect(request);
					} else if(airline.equals(ConnParams.RYANAIR)) {
						return ryanConnector.connect(request);
					}
					return null;
				}
			};
			tasks.add(callable);
		}
		return tasks;
	}
	
	public Map<SingleFlight, String> connect(Map<SingleFlight, String> reqMap) throws Exception {
		Map<SingleFlight, String> resMap = new HashMap<>();
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Callable<String>> tasks = createTasks(reqMap);
		List<Future<String>> results = executor.invokeAll(tasks);
		int index = 0;
		Iterator<Entry<SingleFlight, String>> it = reqMap.entrySet().iterator();
		while(it.hasNext()) {
			Entry<SingleFlight, String> entry = it.next();
			SingleFlight flight = entry.getKey();
			Future<String> res = results.get(index);
			String response = res.get();
			resMap.put(flight, response);
			index++;
		}
		executor.shutdown();
		return resMap;
	}
	
	public Map<SingleFlight, String> prepareRequestsMap(Map<SingleFlight, List<Long>> uniqueMap, String from, String to) {
		if(uniqueMap!=null && uniqueMap.size()>0) {
			Map<SingleFlight, String> reqMap = new HashMap<>();
			Iterator<SingleFlight> it = uniqueMap.keySet().iterator();
			while(it.hasNext()) {
				SingleFlight flight = it.next();
				String departure = flight.getDeparture();
				String destination = flight.getDestination();
				String airline = flight.getAirline();
				String strRequest = null;
				if(airline.equals(ConnParams.WIZZAIR)) {
					strRequest = wizzConnector.prepareRequest(departure, destination, from, to);
				} else if(airline.equals(ConnParams.RYANAIR)) {
					strRequest = ryanConnector.prepareRequest(departure, destination, from, to);
				}
				if(strRequest!=null) {
					reqMap.put(flight, strRequest);
				}
			}
			return reqMap;
		}
		return null;
	}	
}
