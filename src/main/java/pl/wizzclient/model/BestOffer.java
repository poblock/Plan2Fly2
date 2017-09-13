package pl.wizzclient.model;

import java.util.List;

public class BestOffer {
	private final Route route;
	private final double price;
	private final List<Offer> content;

    public BestOffer(double price, List<Offer> content) {
        this.price = price;
        this.content = content;
        this.route = null;
    }
    
    public BestOffer(Route route, double price, List<Offer> content) {
    	this.route = route;
    	this.price = price;
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public List<Offer> getContent() {
        return content;
    }

	public Route getRoute() {
		return route;
	}

	@Override
	public String toString() {
		return "BestOffer [price=" + price + ", content=" + content + "]";
	}
}
