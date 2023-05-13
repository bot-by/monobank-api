package uk.bot_by.monobank4j.api_jackson_jr.example;

import static java.util.Collections.singletonList;

import com.fasterxml.jackson.jr.ob.JacksonJrExtension;
import com.fasterxml.jackson.jr.ob.api.ExtensionContext;
import feign.FeignException;
import java.util.List;
import uk.bot_by.monobank4j.api_jackson_jr.Currency;
import uk.bot_by.monobank4j.api_jackson_jr.CurrencyInfo;
import uk.bot_by.monobank4j.api_jackson_jr.UnixTimeProvider;

public class GetExchangeRates {

  public static void main(String[] args) {
    if (args.length < 1) {
      throw new IllegalArgumentException("At least one currency code is required");
    }

    int currencyCodeA = Integer.parseInt(args[0]);
    int currencyCodeB = (args.length > 1) ?
        Integer.parseInt(args[1]) :
        980; // Hryvnia (UAH)
    List<JacksonJrExtension> extensions = singletonList(new JacksonJrExtension() {

      @Override
      private void register(ExtensionContext context) {
        context.insertProvider(new UnixTimeProvider());
      }

    });
    Currency currency = Currency.getInstance();

    try {
      currency.getRates()
          .stream()
          .filter(pair -> pair.getCurrencyCodeA() == currencyCodeA
              && pair.getCurrencyCodeB() == currencyCodeB)
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
