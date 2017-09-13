package pl.wizzclient.ryan;

import pl.wizzclient.model.Offers;

public interface IRyanDecoder {
	Offers decode(String response);
}
