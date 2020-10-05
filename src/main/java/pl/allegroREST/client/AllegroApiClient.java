package pl.allegroREST.client.allegroClient;

import io.restassured.response.ValidatableResponse;
import pl.allegroREST.client.AbstractApiClient;

public class AllegroApiClient extends AbstractApiClient {

    public AllegroApiClient(String baseURL) {
        super(baseURL);
    }

//    public final CategoriesDto callGetCategoriesAsDto(){
//        return getCategories().statusCode(200).extract().as(CategoriesDto.class);
//    }


    public final ValidatableResponse getCategories(){
        return callGet(AllegroEndpoints.GET_CATEGORIES_PATH.getPath(), getHeadersWithAccept());
    }

}
