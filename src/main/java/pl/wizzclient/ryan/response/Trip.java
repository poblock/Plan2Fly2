package pl.wizzclient.ryan.response;

import java.util.List;

public class Trip {
	private String origin;
	private String originName;
	private String destination;
	private String destinationName;
	private List<Date> dates;
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public List<Date> getDates() {
		return dates;
	}
	public void setDates(List<Date> dates) {
		this.dates = dates;
	}
	@Override
	public String toString() {
		return "Trip [origin=" + origin + ", originName=" + originName + ", destination=" + destination
				+ ", destinationName=" + destinationName + ", dates=" + dates + "]";
	}
}
