package pl.allegroREST.client.auth;

import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import pl.allegroREST.client.AllegroEndpoints;
import pl.allegroREST.config.Config;
import pl.allegroREST.config.ConfigReader;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static io.restassured.RestAssured.given;

@Slf4j
public class TokenProvider {

    /**
     * Loads REST API Authorization properties and sets them as System properties
     */
    static{
        Config.initAuthorizationProperties();
    }

    /***
     * Uses credetionals from configuration.properties to get token
     * @return Bearer token as String
     */
    public static String getToken() {
        final String token;
        var getToken = given().filter(new ResponseLoggingFilter())
                .auth()
                .basic(ConfigReader.getAuthorizationPropertyValue("client_id"), ConfigReader.getAuthorizationPropertyValue("client_secret"))
                .when()
                .get(createAuthURL());
        token = getToken.jsonPath().getString("access_token");
        return token;
    }

    /***
     * Creates an auth URL
     * @return URL to token
     */
    private static URL createAuthURL(){
        URL url;
        try{
            URIBuilder b = new URIBuilder(ConfigReader.getAuthorizationPropertyValue("url") + AllegroEndpoints.TOKEN_PATH.getPath());
            b.addParameter("grant_type", "client_credentials");

            url = b.build().toURL();
            return url;
        }catch (URISyntaxException | MalformedURLException e){
          log.info("Exception building URL" + e);
          return null;
        }
    }
}
