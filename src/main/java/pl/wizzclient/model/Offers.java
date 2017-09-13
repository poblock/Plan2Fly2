package pl.wizzclient.model;

import java.util.List;

public class Offers {
	private List<Offer> flightsDep;
	private List<Offer> flightsReturn;
	public Offers(List<Offer> flightsDep, List<Offer> flightsReturn) {
		this.flightsDep = flightsDep;
		this.flightsReturn = flightsReturn;
	}
	public List<Offer> getFlightsDep() {
		return flightsDep;
	}
	public void setFlightsDep(List<Offer> flightsDep) {
		this.flightsDep = flightsDep;
	}
	public List<Offer> getFlightsReturn() {
		return flightsReturn;
	}
	public void setFlightsReturn(List<Offer> flightsReturn) {
		this.flightsReturn = flightsReturn;
	}
	@Override
	public String toString() {
		return "Offers [flightsDep=" + flightsDep + ", flightsReturn=" + flightsReturn + "]";
	}
	
}
