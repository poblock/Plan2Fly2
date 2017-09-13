package pl.wizzclient.model;

public class Offer {
	private String departure; 
	private String destination; 
	private String airline;
	private String flightDate;
	private double price;
	
	public Offer() {
		
	}
	
	public Offer(String departure, String destination, String airline) {
		this.departure = departure;
		this.destination = destination;
		this.airline = airline;
	}
	
	public Offer(String departure, String destination, String airline, String flightDate, double price) {
		this.departure = departure;
		this.destination = destination;
		this.airline = airline;
		this.flightDate = flightDate;
		this.price = price;
	}

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

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

		public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Offer [departure=" + departure + ", destination=" + destination + ", airline=" + airline
				+ ", flightDate=" + flightDate + ", price=" + price + "]";
	}

}
