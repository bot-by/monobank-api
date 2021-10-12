package uk.bot_by.monobank4j.api_gson.example;

import feign.FeignException;
import uk.bot_by.monobank4j.api_gson.Currency;
import uk.bot_by.monobank4j.api_gson.CurrencyInfo;

public class GetExchangeRates {

	public static void main(String[] args) {
		if (args.length < 1) {
			throw new IllegalArgumentException("At least one currency code is required");
		}

		int currencyCodeA = Integer.parseInt(args[0]);
		int currencyCodeB = (args.length > 1) ?
				                    Integer.parseInt(args[1]) :
				                    980; // Hryvnia (UAH)
		Currency currency = Currency.getInstance();

		try {
			currency.getRates()
			        .stream()
			        .filter(pair -> pair.getCurrencyCodeA() == currencyCodeA && pair.getCurrencyCodeB() == currencyCodeB)
			        .findFirst()
			        .ifPresent(GetExchangeRates::printCurrencyInfo);
		} catch (FeignException.TooManyRequests exception) {
			System.out.println("Too many request, try it later.");
			System.exit(1);
		}
	}

	private static void printCurrencyInfo(CurrencyInfo currencyInfo) {
		System.out.format("Exchange rates %s/%s:\n\tbuy %s,\n\tcross %s,\n\tsell %s\n",
				currencyInfo.getCurrencyCodeA(), currencyInfo.getCurrencyCodeB(),
				currencyInfo.getRateBuy(), currencyInfo.getRateCross(), currencyInfo.getRateSell());
	}

}
