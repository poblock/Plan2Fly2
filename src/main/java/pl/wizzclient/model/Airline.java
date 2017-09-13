package pl.wizzclient.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Airline {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String airlineName;
	private String airlineCode;
	
	public Airline() {
		
	}
	
	public Airline(String airlineName, String airlineCode) {
		this.airlineName = airlineName;
		this.airlineCode = airlineCode;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	@Override
	public String toString() {
		return "Airline [id=" + id + ", airlineName=" + airlineName 
				+ ", airlineCode=" + airlineCode + "]";
	}
}
