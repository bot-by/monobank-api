package uk.bot_by.monobank.api_json.example;

import feign.Feign;
import feign.http2client.Http2Client;
import feign.json.JsonDecoder;
import org.json.JSONObject;
import uk.bot_by.monobank.api_json.Currency;

public class GetExchangeRates {

	public static void main(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("At least one currency code is required");
		}

		int currencyCodeA = Integer.parseInt(args[0]);
		int currencyCodeB = (args.length > 1) ?
				                    Integer.parseInt(args[1]) :
				                    980; // Hryvnia (UAH)
		Currency currency = Feign.builder()
		                         .client(new Http2Client())
		                         .decoder(new JsonDecoder())
		                         .target(Currency.class, "https://api.monobank.ua/");

		for (Object item : currency.getRates()) {
			JSONObject pair = (JSONObject) item;

			if (pair.getInt("currencyCodeA") == currencyCodeA && pair.getInt("currencyCodeB") == currencyCodeB) {
				System.out.format("Exchange rates %s/%s:\n\tbuy %s,\n\tcross %s,\n\tsell %s\n",
						pair.getInt("currencyCodeA"), pair.getInt("currencyCodeB"),
						pair.optBigDecimal("rateBuy", null),
						pair.optBigDecimal("rateCross", null),
						pair.optBigDecimal("rateSell", null));
			}
		}

	}

}
