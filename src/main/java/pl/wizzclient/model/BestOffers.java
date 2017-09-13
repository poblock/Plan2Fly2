package pl.wizzclient.model;

import java.util.List;

public class BestOffers {
	private final List<BestOffer> offersThere;
	private final List<BestOffer> offersBack;
	public BestOffers(List<BestOffer> offersThere, List<BestOffer> offersBack) {
		this.offersThere = offersThere;
		this.offersBack = offersBack;
	}
	public List<BestOffer> getOffersThere() {
		return offersThere;
	}
	public List<BestOffer> getOffersBack() {
		return offersBack;
	} 
}
