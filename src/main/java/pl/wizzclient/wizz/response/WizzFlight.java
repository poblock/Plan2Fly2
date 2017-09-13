package pl.wizzclient.wizz.response;

import java.util.List;

public class WizzFlight {
	private String departureStation;
	private String arrivalStation;
	private String departureDate;
	private WizzPrice price;
	private String priceType;
	private List<String> departureDates;
	private String classOfService;
	private boolean hasMacFlight;
	public String getDepartureStation() {
		return departureStation;
	}
	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}
	public String getArrivalStation() {
		return arrivalStation;
	}
	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public WizzPrice getPrice() {
		return price;
	}
	public void setPrice(WizzPrice price) {
		this.price = price;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public List<String> getDepartureDates() {
		return departureDates;
	}
	public void setDepartureDates(List<String> departureDates) {
		this.departureDates = departureDates;
	}
	public String getClassOfService() {
		return classOfService;
	}
	public void setClassOfService(String classOfService) {
		this.classOfService = classOfService;
	}
	public boolean isHasMacFlight() {
		return hasMacFlight;
	}
	public void setHasMacFlight(boolean hasMacFlight) {
		this.hasMacFlight = hasMacFlight;
	}
	@Override
	public String toString() {
		return "Flight [departureStation=" + departureStation + ", arrivalStation=" + arrivalStation
				+ ", departureDate=" + departureDate + ", price=" + price + ", priceType=" + priceType
				+ ", departureDates=" + departureDates + ", classOfService=" + classOfService + ", hasMacFlight="
				+ hasMacFlight + "]";
	}
}
