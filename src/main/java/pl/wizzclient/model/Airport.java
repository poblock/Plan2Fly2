package pl.wizzclient.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Airport {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String airportCode;
	private String airportName;
	
	private String countryCode;
	
	private String latitude;
	private String longitude;
	
	public Airport() {
		
	}

	public Airport(String airportCode, String airportName, String airportCountry, String latitude, String longitude) {
		this.airportCode = airportCode;
		this.airportName = airportName;
		this.countryCode = airportCountry;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String airportCountry) {
		this.countryCode = airportCountry;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Airport [id=" + id + ", airportCode=" + airportCode + ", airportName=" + airportName + ", countryCode="
				+ countryCode + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	
}
