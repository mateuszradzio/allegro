package pl.allegroREST.client;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import lombok.Getter;
import lombok.Setter;


/***
 * Facade for RestAssured
 */
public abstract class AbstractApiClient {

    protected static final List<Filter> filters = List.of(new RequestLoggingFilter(), new ResponseLoggingFilter());
    private final String baseURL;
    @Getter
    @Setter
    protected String token;

    public AbstractApiClient(final String baseURL) {
        this.baseURL = baseURL;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(new ObjectMapperConfig().jackson2ObjectMapperFactory(
                ((type, s) -> objectMapper)
        )).encoderConfig(new EncoderConfig().defaultContentCharset("UTF-8").defaultCharsetForContentType("UTF-8", "application/vnd.allegro.public.v1+json")).decoderConfig(new DecoderConfig().defaultContentCharset("UTF-8").defaultCharsetForContentType("UTF-8", "application/vnd.allegro.public.v1+json"));
    }

    /***
     * Configures the request parameters with OAuth2 token from ApiClient field and sends GET request
     * @param path endpoint URI e.g. /sale/categories
     * @param headers parameters as Map<String, Object>
     * @return ValidatableResponse
     */
    public ValidatableResponse callGet(final String path, final Map<String, Object> headers){
        return RestAssured.given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .headers(headers)
                .filters(filters)
                .auth()
                .oauth2(token)
                .when()
                .request(Method.GET, baseURL + path)
                .then();
    }

    /***
     * Configures the request parameters with OAuth2 token from ApiClient field and sends GET request with path params
     * @param path endpoint URI e.g. /sale/categories
     * @param pathParams parameters as Map<String, Object>
     * @param headers parameters as Map<String, Object>
     * @return ValidatableResponse
     */
    public ValidatableResponse callGetWithPathParams(final String path, final Map<String, Object> pathParams, final Map<String, Object> headers){
        return RestAssured.given().relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParams(pathParams)
                .headers(headers)
                .filters(filters)
                .auth()
                .oauth2(token)
                .when()
                .request(Method.GET, baseURL + path)
                .then();
    }
}
