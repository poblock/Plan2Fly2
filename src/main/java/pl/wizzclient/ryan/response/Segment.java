package pl.wizzclient.ryan.response;

import java.util.List;

public class Segment {
	private long segmentNr;
	private String origin;
	private String destination;
	private String flightNumber;
	private List<String> time;
	private List<String> timeUTC;
	private String duration;
	public long getSegmentNr() {
		return segmentNr;
	}
	public void setSegmentNr(long segmentNr) {
		this.segmentNr = segmentNr;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
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
		return "Segment [segmentNr=" + segmentNr + ", origin=" + origin + ", destination=" + destination
				+ ", flightNumber=" + flightNumber + ", time=" + time + ", timeUTC=" + timeUTC + ", duration="
				+ duration + "]";
	}
}
