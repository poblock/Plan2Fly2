package pl.wizzclient.ryan.response;

import java.util.List;

public class RyanResponse {
	private String termsOfUse;
	private String currency;
	private String currPrecision;
	private List<Trip> trips;
	private String serverTimeUTC;
	public String getTermsOfUse() {
		return termsOfUse;
	}
	public void setTermsOfUse(String termsOfUse) {
		this.termsOfUse = termsOfUse;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrPrecision() {
		return currPrecision;
	}
	public void setCurrPrecision(String currPrecision) {
		this.currPrecision = currPrecision;
	}
	public List<Trip> getTrips() {
		return trips;
	}
	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}
	public String getServerTimeUTC() {
		return serverTimeUTC;
	}
	public void setServerTimeUTC(String serverTimeUTC) {
		this.serverTimeUTC = serverTimeUTC;
	}
	@Override
	public String toString() {
		return "Response [termsOfUse=" + termsOfUse + ", currency=" + currency + ", currPrecision=" + currPrecision
				+ ", trips=" + trips + ", serverTimeUTC=" + serverTimeUTC + "]";
	}
}
