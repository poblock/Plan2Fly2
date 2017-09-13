package pl.wizzclient.wizz;

import pl.wizzclient.model.Offers;

public interface IWizzDecoder {
	Offers decode(String response);
}
