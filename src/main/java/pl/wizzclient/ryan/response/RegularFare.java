package pl.wizzclient.ryan.response;

import java.util.List;

public class RegularFare {
	private String fareKey;
	private String fareClass;
	private List<Fare> fares;
	public String getFareKey() {
		return fareKey;
	}
	public void setFareKey(String fareKey) {
		this.fareKey = fareKey;
	}
	public List<Fare> getFares() {
		return fares;
	}
	public void setFares(List<Fare> fares) {
		this.fares = fares;
	}
	public String getFareClass() {
		return fareClass;
	}
	public void setFareClass(String fareClass) {
		this.fareClass = fareClass;
	}
	@Override
	public String toString() {
		return "RegularFare [fareKey=" + fareKey + ", fareClass=" + fareClass + ", fares=" + fares + "]";
	}
}
