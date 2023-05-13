package uk.bot_by.monobank4j.api_token;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
class TokenInterceptorTest {

  private TokenInterceptor interceptor;
  private RequestTemplate requestTemplate;

  @BeforeEach
  void setUp() {
    interceptor = new TokenInterceptor("qwerty");
    requestTemplate = new RequestTemplate();
  }

  @DisplayName("Add the API token to a request template")
  @Test
  void happyPath() {
    // when
    interceptor.apply(requestTemplate);

    // then
    assertAll("HTTP header with API token",
        () -> assertThat("HTTP header with API token", requestTemplate.headers(),
            hasKey("X-Token")),
        () -> assertEquals("qwerty", requestTemplate.headers().get("X-Token").iterator().next(),
            "The API token was added to the request template"));
  }

  @DisplayName("The API token header exists")
  @Test
  void headerExists() {
    // given
    requestTemplate.header("X-Token", "abc");

    // when
    interceptor.apply(requestTemplate);

    // then
    assertAll("HTTP header with API token",
        () -> assertThat("HTTP header with API token", requestTemplate.headers(),
            hasKey("X-Token")),
        () -> assertEquals("abc", requestTemplate.headers().get("X-Token").iterator().next(),
            "The API token wasn't overwritten"));
  }

}
