package pl.wizzclient.model;
import javax.validation.constraints.NotNull;

public class TripForm {
	@NotNull
	private String departure;
	@NotNull
	private String destination;
	@NotNull
	private String month;
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "TripForm [departure=" + departure + ", destination=" + destination + ", month=" + month + "]";
	}
}
