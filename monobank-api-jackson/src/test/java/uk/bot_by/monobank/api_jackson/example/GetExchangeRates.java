package uk.bot_by.monobank.api_jackson.example;

import feign.Feign;
import feign.http2client.Http2Client;
import feign.jackson.JacksonDecoder;
import uk.bot_by.monobank.api_jackson.Currency;
import uk.bot_by.monobank.api_jackson.CurrencyInfo;

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
		                         .decoder(new JacksonDecoder())
		                         .target(Currency.class, "https://api.monobank.ua/");

		currency.getRates()
		        .stream()
		        .filter(pair -> pair.getCurrencyCodeA() == currencyCodeA && pair.getCurrencyCodeB() == currencyCodeB)
		        .findFirst()
		        .ifPresent(GetExchangeRates::printCurrencyInfo);
	}

	private static void printCurrencyInfo(CurrencyInfo currencyInfo) {
		System.out.format("Exchange rates %s/%s:\n\tbuy %s,\n\tcross %s,\n\tsell %s\n",
				currencyInfo.getCurrencyCodeA(), currencyInfo.getCurrencyCodeB(),
				currencyInfo.getRateBuy(), currencyInfo.getRateCross(), currencyInfo.getRateSell());
	}

}