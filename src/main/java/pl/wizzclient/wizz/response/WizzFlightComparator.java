package pl.wizzclient.wizz.response;

import java.util.Comparator;

public class WizzFlightComparator implements Comparator<WizzFlight> {

	@Override
	public int compare(WizzFlight f1, WizzFlight f2) {
		WizzPrice price1 = f1.getPrice();
		WizzPrice price2 = f2.getPrice();
		if(price1.getCurrencyCode().equals(price2.getCurrencyCode())) {
			Double d1 = price1.getAmount();
			Double d2 = price2.getAmount();
			return d1.compareTo(d2);
		} else {
			System.out.println("ROZNE WALUTY "+f1+" "+f2);
		}
		return 0;
	}

}
