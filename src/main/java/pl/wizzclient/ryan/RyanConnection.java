package pl.wizzclient.ryan;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import pl.wizzclient.connection.ConnParams;
import pl.wizzclient.connection.IConnector;

public class RyanConnection implements IConnector {
	private static final String URL = "https://desktopapps.ryanair.com/v3/pl-pl/availability?ADT=1&CHD=0"
			+ "&FlexDaysIn=6&FlexDaysOut=6&INF=0&RoundTrip=true&TEEN=0&ToUs=AGREED&exists=false&promoCode=";
	private CloseableHttpClient httpclient;

	public RyanConnection(CloseableHttpClient httpclient) {
		this.httpclient = httpclient;
	}
	
	@Override
	public String prepareRequest(String departure, String destination, String from, String to) {
		StringBuffer sb = new StringBuffer(URL);
		sb.append("&Origin="+departure);
		sb.append("&Destination="+destination);
		sb.append("&DateOut="+from);
		sb.append("&DateIn="+to);
		return sb.toString();
	}
	
	@Override
	public String connect(String request) throws Exception {	
		String response = null;
		HttpGet httpget = new HttpGet(request);
		httpget.addHeader("accept", "application/json, text/plain, */*");
		httpget.addHeader("accept-encoding", "gzip, deflate, br");
//		httppost.addHeader("content-type", Connection.CONTENT_TYPE);
//		httppost.addHeader("origin", "https://wizzair.com");
		httpget.addHeader("user-agent", ConnParams.USER_AGENT);
		BasicHttpContext context = new BasicHttpContext();
		System.out.println("Execute "+request);
		CloseableHttpResponse httpresponse = httpclient.execute(httpget, context);
        try {
            HttpEntity entity = httpresponse.getEntity();
            if (entity != null && httpresponse.getStatusLine().getStatusCode()==200) {
            	 response = EntityUtils.toString(entity);
            } else {
            	System.out.println(httpresponse.toString());
            }
        } finally {
        	httpresponse.close();
        }
        
        return response;
	}
}
