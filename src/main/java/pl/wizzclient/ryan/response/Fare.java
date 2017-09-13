package pl.wizzclient.ryan.response;

public class Fare {
	private String type;
	private double amount;
	private long count;
	private boolean hasDiscount;
	private double publishedFare;
	private long discountInPercent;
	private boolean hasPromoDiscount;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public boolean isHasDiscount() {
		return hasDiscount;
	}
	public void setHasDiscount(boolean hasDiscount) {
		this.hasDiscount = hasDiscount;
	}
	public double getPublishedFare() {
		return publishedFare;
	}
	public void setPublishedFare(double publishedFare) {
		this.publishedFare = publishedFare;
	}
	public long getDiscountInPercent() {
		return discountInPercent;
	}
	public void setDiscountInPercent(long discountInPercent) {
		this.discountInPercent = discountInPercent;
	}
	public boolean isHasPromoDiscount() {
		return hasPromoDiscount;
	}
	public void setHasPromoDiscount(boolean hasPromoDiscount) {
		this.hasPromoDiscount = hasPromoDiscount;
	}
	@Override
	public String toString() {
		return "Fare [type=" + type + ", amount=" + amount + ", count=" + count + ", hasDiscount=" + hasDiscount
				+ ", publishedFare=" + publishedFare + ", discountInPercent=" + discountInPercent
				+ ", hasPromoDiscount=" + hasPromoDiscount + "]";
	}
}
