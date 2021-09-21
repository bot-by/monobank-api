# Monobank API

[![English](https://img.shields.io/badge/%F0%9F%93%84-English-blue)](readme.md)
[![Українська](https://img.shields.io/badge/%F0%9F%93%84-%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D1%81%D1%8C%D0%BA%D0%BE%D1%8E-blue)](readme.uk.md)

Unofficial Java wrapper for [Monobank API][monobank-api]: get exchange rates (public),
get client info and statements and set a webhook to receive transaction
events (personal).

It implements:

-   currency rates

There are four implementations for different JSON libraries:

-   [monobank-api-gson](monobank-api-gson) for [Google Gson][gson], with dependencies about 448K,
-   [monobank-api-jackson](monobank-api-jackson) for [Jackson][jackson], with dependencies about 2.1M,
-   [monobank-api-jackson-jr](monobank-api-jackson-jr) for [Jackson Jr.][jackson-jr], with dependencies about 672K,
-   [monobank-api-json](monobank-api-json) for [JSON-java][json], with dependencies about 280K

Another package [monobank-api-token](monobank-api-token) provides `TokenInterceptor` for Personal API.

## Contributing

Please read [Contributing](contributing.md).

## History

See [ChangeLog](changelog.md)

## License

[Apache License v2.0](LICENSE)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

[monobank-api]: https://api.monobank.ua/docs/ "Monobank API to get statements and account balances"
[gson]: https://github.com/google/gson "A Java serialization/deserialization library to convert Java Objects into JSON and back"
[jackson]: https://github.com/FasterXML/jackson "JSON for Java"
[jackson-jr]: https://github.com/FasterXML/jackson-jr "A compact alternative to full Jackson Databind component"
[json]: https://github.com/stleary/JSON-java "A reference implementation of a JSON package in Java"
