# Monobank API with JSON-java

Unofficial Java wrapper for [Monobank API][monobank-api]: get exchange rates (public),
get client info and statements and set a webhook to receive transaction
events (personal).

It uses [JSON-java][json] to encode and decode JSON.
[There are][parent] other packages for Gson, Jackson, and Jackson Jr.

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
  <artifactId>monobank-api-json</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Usage

Instantiate an instance of Monobank API with [Feign][feign]

```java
currency = Feign.builder()
                .client(new Http2Client())
                .decoder(new JsonDecoder())
                .requestInterceptor(new TokenInterceptor("abc-api-token"))
                .target(Currency.class, "https://api.monobank.ua/");
```

This is a minimal configuration.
The `TokenInterceptor` is required for personal API only
and it is provided by the package <strong>monobank-api-token</strong>.
But your could use your own implementation to add the [token header][token].

### Get currency exchange rates

It's easy:

```java
JSONArray currencyExchangeRates = currency.getRates();
```

To run the example [GetExchangeRates][example]:

1.  Build the whole project:
    `mvn`

2.  Go to the **monobank-api-json** and get all dependencies:
    `mvn dependency:copy-dependencies`

3.  Then go to **target/dependency** and run the following to get rates of pound and zloty:

    -   java -cp json-20210307.jar:feign-core-11.6.jar:feign-json-11.6.jar:feign-java11-11.6.jar:../classes:../test-classes uk.bot_by.monobank.api_json.example.GetExchangeRates 826
    -   java -cp json-20210307.jar:feign-core-11.6.jar:feign-json-11.6.jar:feign-java11-11.6.jar:../classes:../test-classes uk.bot_by.monobank.api_json.example.GetExchangeRates 985

## License

[Apache License v2.0](../LICENSE)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

[monobank-api]: https://api.monobank.ua/docs/ "Monobank API to get statements and account balances"
[json]: https://github.com/stleary/JSON-java "A reference implementation of a JSON package in Java"
[parent]: https://gitlab.com/bot-by/monobank-api/ "Java wrapper for Monobank API"
[feign]: https://github.com/OpenFeign/feign "Feign makes writing java http clients easier."
[example]: src/test/java/uk/bot_by/monobank/api_json/example/GetExchangeRates.java
[token]: https://api.monobank.ua/docs/#tag---------------------------- "Monobank Personal API"
