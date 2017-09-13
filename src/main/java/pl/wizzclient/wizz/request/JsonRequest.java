package pl.wizzclient.wizz.request;

import java.util.List;

public class JsonRequest {
	private List<WizzFlightRequest> flightList;
	private String priceType;
	public List<WizzFlightRequest> getFlightList() {
		return flightList;
	}
	public void setFlightList(List<WizzFlightRequest> flightList) {
		this.flightList = flightList;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
}
