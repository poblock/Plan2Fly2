package pl.wizzclient.wizz.response;

import java.util.List;

public class WizzResponse {
	private List<WizzFlight> outboundFlights;
	private List<WizzFlight> returnFlights;
	public List<WizzFlight> getOutboundFlights() {
		return outboundFlights;
	}
	public void setOutboundFlights(List<WizzFlight> outboundFlights) {
		this.outboundFlights = outboundFlights;
	}
	public List<WizzFlight> getReturnFlights() {
		return returnFlights;
	}
	public void setReturnFlights(List<WizzFlight> returnFlights) {
		this.returnFlights = returnFlights;
	}
	@Override
	public String toString() {
		return "JsonResponse [outboundFlights=" + outboundFlights + ", returnFlights=" + returnFlights + "]";
	}
}
