# Monobank4J

[![English](https://img.shields.io/badge/%F0%9F%93%84-English-blue)](readme.md)
[![Українською](https://img.shields.io/badge/%F0%9F%93%84-%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D1%81%D1%8C%D0%BA%D0%BE%D1%8E-blue)](readme.uk.md)

Неофіційна Java обгортка навколо [Monobank API][monobank-api]: отримуйте курси
обміну (загальнодоступне), інформацію про клієнта та виписки,
встановилюйте веб-хук для отримання подій транзакції (персональне).

Вже реалізовано:

-   курси валют

Існує чотири імплементації для різних JSON бібліотек

-   [monobank4j-gson](monobank4j-gson) для [Google Gson][gson], з залежностями приблизно 448K,
-   [monobank4j-jackson](monobank4j-jackson) для [Jackson][jackson], з залежностями приблизно 2.1M,
-   [monobank4j-jackson-jr](monobank4j-jackson-jr) для [Jackson Jr.][jackson-jr], з залежностями приблизно 672K,
-   [monobank4j-json](monobank4j-json) для [JSON-java][json], з залежностями приблизно 280K

Інший пакет [monobank4j-token](monobank4j-token) надає `TokenInterceptor` для використання у персональному API.

## Внесок

Будь ласка прочтіть [Contributing](contributing.md).

## Історія змін

Дивіться [ChangeLog](changelog.md)

## Ліцензія

[Apache License v2.0](LICENSE)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

[monobank-api]: https://api.monobank.ua/docs/ "Monobank API для отримання інформації про виписки та стан особистого рахунку"
[gson]: https://github.com/google/gson "A Java serialization/deserialization library to convert Java Objects into JSON and back"
[jackson]: https://github.com/FasterXML/jackson "JSON for Java"
[jackson-jr]: https://github.com/FasterXML/jackson-jr "A compact alternative to full Jackson Databind component"
[json]: https://github.com/stleary/JSON-java "A reference implementation of a JSON package in Java"
