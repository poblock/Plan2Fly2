package pl.wizzclient.ryan.response;

import java.util.List;

public class Flight {
	private String faresLeft;
	private String flightKey;
	private String infantsLeft;
	private RegularFare regularFare;
	private List<Segment> segments;
	private String flightNumber;
	private List<String> time;
	private List<String> timeUTC;
	private String duration;
	public String getFaresLeft() {
		return faresLeft;
	}
	public void setFaresLeft(String faresLeft) {
		this.faresLeft = faresLeft;
	}
	public String getFlightKey() {
		return flightKey;
	}
	public void setFlightKey(String flightKey) {
		this.flightKey = flightKey;
	}
	public String getInfantsLeft() {
		return infantsLeft;
	}
	public void setInfantsLeft(String infantsLeft) {
		this.infantsLeft = infantsLeft;
	}
	public RegularFare getRegularFare() {
		return regularFare;
	}
	public void setRegularFare(RegularFare regularFare) {
		this.regularFare = regularFare;
	}
	public List<Segment> getSegments() {
		return segments;
	}
	public void setSegments(List<Segment> segments) {
		this.segments = segments;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public List<String> getTime() {
		return time;
	}
	public void setTime(List<String> time) {
		this.time = time;
	}
	public List<String> getTimeUTC() {
		return timeUTC;
	}
	public void setTimeUTC(List<String> timeUTC) {
		this.timeUTC = timeUTC;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	@Override
	public String toString() {
		return "Flight [faresLeft=" + faresLeft + ", flightKey=" + flightKey + ", infantsLeft=" + infantsLeft
				+ ", regularFare=" + regularFare + ", segments=" + segments + ", flightNumber=" + flightNumber
				+ ", time=" + time + ", timeUTC=" + timeUTC + ", duration=" + duration + "]";
	}
}
