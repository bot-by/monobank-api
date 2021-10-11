# Monobank API with Jackson

Unofficial Java wrapper for [Monobank API][monobank-api]: get exchange rates (public),
get client info and statements and set a webhook to receive transaction
events (personal).

It uses [Jackson][jackson] to encode and decode JSON.
[There are][parent] other packages for Gson, Jackson Jr., and JSON-java.

It implements:

-   currency rates

## Acquire

The package is not published to Maven Central yet.
Use project's GitLab repository instead, please:

```xml
<repositories>
  <repository>
    <id>monobank-api-maven</id>
    <url>https://gitlab.example.com/api/v4/projects/28690779/packages/maven</url>
  </repository>
</repositories>
```

Please add dependency to your project:

```xml
<dependency>
  <groupId>uk.bot-by.monobank</groupId>
  <artifactId>monobank-api-jackson</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Usage

### Currency

Create an instance of currency Monobank API using `Currency.getInstance()`
or with [Feign][feign] manually, e.g. if you want to use custom client.

```java
currency = Feign.builder()
                .client(new Http2Client())
                .decoder(new JacksonDecoder())
                .target(Currency.class, "https://api.monobank.ua/");
```

Then just get exchange rates, it's easy:

```java
List<CurrencyInfo> currencyExchangeRates = currency.getRates();
```

To run the example [GetExchangeRates][example]:

1.  Build the whole project:
    `mvn`

2.  Go to the **monobank-api-jackson** and get all dependencies:
    `mvn dependency:copy-dependencies`

3.  Then go to **target/dependency** and run the following to get rates of pound and zloty:

    -   java -cp jackson-annotations-2.12.4.jar:jackson-core-2.12.4.jar:jackson-databind-2.12.4.jar:feign-core-11.6.jar:feign-jackson-11.6.jar:feign-java11-11.6.jar:../classes:../test-classes uk.bot_by.monobank.api_jackson.example.GetExchangeRates 826
    -   java -cp jackson-annotations-2.12.4.jar:jackson-core-2.12.4.jar:jackson-databind-2.12.4.jar:feign-core-11.6.jar:feign-jackson-11.6.jar:feign-java11-11.6.jar:../classes:../test-classes uk.bot_by.monobank.api_jackson.example.GetExchangeRates 985

## License

[Apache License v2.0](../LICENSE)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

[monobank-api]: https://api.monobank.ua/docs/ "Monobank API to get statements and account balances"
[jackson]: https://github.com/FasterXML/jackson "JSON for Java"
[parent]: https://gitlab.com/bot-by/monobank-api/ "Java wrapper for Monobank API"
[feign]: https://github.com/OpenFeign/feign "Feign makes writing java http clients easier."
[example]: src/test/java/uk/bot_by/monobank/api_jackson/example/GetExchangeRates.java
