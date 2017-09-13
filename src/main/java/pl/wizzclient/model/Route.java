package pl.wizzclient.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Route {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String airportDeparture;
	
	private String airportTransfer1;
	private String airlineTransfer1;	
	
	private String airportTransfer2;
	private String airlineTransfer2;
	
	private String airportDestination;
	private String airlineDestination;
	
	public Route() {
		
	}
	
	public Route(String airportDeparture, String airportTransfer1, String airlineTransfer1, String airportTransfer2,
			String airlineTransfer2, String airportDestination, String airlineDestination) {
		this.airportDeparture = airportDeparture;
		this.airportTransfer1 = airportTransfer1;
		this.airlineTransfer1 = airlineTransfer1;
		this.airportTransfer2 = airportTransfer2;
		this.airlineTransfer2 = airlineTransfer2;
		this.airportDestination = airportDestination;
		this.airlineDestination = airlineDestination;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAirportDeparture() {
		return airportDeparture;
	}

	public void setAirportDeparture(String airportDeparture) {
		this.airportDeparture = airportDeparture;
	}

	public String getAirportTransfer1() {
		return airportTransfer1;
	}

	public void setAirportTransfer1(String airportTransfer1) {
		this.airportTransfer1 = airportTransfer1;
	}

	public String getAirlineTransfer1() {
		return airlineTransfer1;
	}

	public void setAirlineTransfer1(String airlineTransfer1) {
		this.airlineTransfer1 = airlineTransfer1;
	}

	public String getAirportTransfer2() {
		return airportTransfer2;
	}

	public void setAirportTransfer2(String airportTransfer2) {
		this.airportTransfer2 = airportTransfer2;
	}

	public String getAirlineTransfer2() {
		return airlineTransfer2;
	}

	public void setAirlineTransfer2(String airlineTransfer2) {
		this.airlineTransfer2 = airlineTransfer2;
	}

	public String getAirportDestination() {
		return airportDestination;
	}

	public void setAirportDestination(String airportDestination) {
		this.airportDestination = airportDestination;
	}

	public String getAirlineDestination() {
		return airlineDestination;
	}

	public void setAirlineDestination(String airlineDestination) {
		this.airlineDestination = airlineDestination;
	}

	@Override
	public String toString() {
		return "Route [id=" + id + ", airportDeparture=" + airportDeparture + ", airportTransfer1=" + airportTransfer1
				+ ", airlineTransfer1=" + airlineTransfer1 + ", airportTransfer2=" + airportTransfer2
				+ ", airlineTransfer2=" + airlineTransfer2 + ", airportDestination=" + airportDestination
				+ ", airlineDestination=" + airlineDestination + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airlineDestination == null) ? 0 : airlineDestination.hashCode());
		result = prime * result + ((airlineTransfer1 == null) ? 0 : airlineTransfer1.hashCode());
		result = prime * result + ((airlineTransfer2 == null) ? 0 : airlineTransfer2.hashCode());
		result = prime * result + ((airportDeparture == null) ? 0 : airportDeparture.hashCode());
		result = prime * result + ((airportDestination == null) ? 0 : airportDestination.hashCode());
		result = prime * result + ((airportTransfer1 == null) ? 0 : airportTransfer1.hashCode());
		result = prime * result + ((airportTransfer2 == null) ? 0 : airportTransfer2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (airlineDestination == null) {
			if (other.airlineDestination != null)
				return false;
		} else if (!airlineDestination.equals(other.airlineDestination))
			return false;
		if (airlineTransfer1 == null) {
			if (other.airlineTransfer1 != null)
				return false;
		} else if (!airlineTransfer1.equals(other.airlineTransfer1))
			return false;
		if (airlineTransfer2 == null) {
			if (other.airlineTransfer2 != null)
				return false;
		} else if (!airlineTransfer2.equals(other.airlineTransfer2))
			return false;
		if (airportDeparture == null) {
			if (other.airportDeparture != null)
				return false;
		} else if (!airportDeparture.equals(other.airportDeparture))
			return false;
		if (airportDestination == null) {
			if (other.airportDestination != null)
				return false;
		} else if (!airportDestination.equals(other.airportDestination))
			return false;
		if (airportTransfer1 == null) {
			if (other.airportTransfer1 != null)
				return false;
		} else if (!airportTransfer1.equals(other.airportTransfer1))
			return false;
		if (airportTransfer2 == null) {
			if (other.airportTransfer2 != null)
				return false;
		} else if (!airportTransfer2.equals(other.airportTransfer2))
			return false;
		return true;
	}
}
