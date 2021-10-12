package uk.bot_by.monobank4j.util;

public final class MonobankConstants {

	public static final String API_MONOBANK_UA = "https://api.monobank.ua/";

	public static final String BANK_CURRENCY = "GET /bank/currency";
	public static final String PERSONAL_CLIENT_INFO = "GET /personal/client-info";
	public static final String PERSONAL_STATEMENT = "GET /personal/statement/{account}/{from}/{to}";
	public static final String PERSONAL_WEBHOOK = "POST /personal/webhook";

	public static final String ACCOUNT = "account";
	public static final String CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json";
	public static final String FROM = "from";
	public static final String TO = "to";
	public static final String WEBHOOK_BODY = "%7B \"webHookUrl\": \"{webhookLocator}\" %7D";
	public static final String WEBHOOK_LOCATOR = "webhookLocator";

	private MonobankConstants() {
	}

}
