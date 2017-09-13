package pl.wizzclient.connection;

public interface IConnector {
	String connect(String params) throws Exception;
	String prepareRequest(String departure, String destination, String from, String to);
}
