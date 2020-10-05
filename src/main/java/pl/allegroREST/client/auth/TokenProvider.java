package pl.allegroREST.client.utils;

import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import pl.allegroREST.client.allegroClient.AllegroEndpoints;
import pl.allegroREST.client.config.Config;
import pl.allegroREST.client.config.ConfigReader;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static io.restassured.RestAssured.given;

@Slf4j
public class TokensUtils {

    static{
        Config.initAuthorizationProperties();
    }

    private static String token = null;

    public static String getToken() {
        if(token == null){
            var getToken = given().filter(new ResponseLoggingFilter())
                    .auth()
                    .basic(ConfigReader.getAuthorizationPropertyValue("client_id"), ConfigReader.getAuthorizationPropertyValue("client_secret"))
                    .when()
                    .get(createAuthURL());
            token = getToken.jsonPath().getString("access_token");
        }
        return token;
    }

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
