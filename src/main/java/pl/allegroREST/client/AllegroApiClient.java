package pl.allegroREST.client;

import io.restassured.response.ValidatableResponse;
import org.openapitools.client.model.CategoriesDto;
import org.openapitools.client.model.CategoryDto;
import org.openapitools.client.model.CategoryParameterList;

import java.util.HashMap;
import java.util.Map;

/***
 * Client used to communication with Allegro API
 */
public class AllegroApiClient extends AbstractApiClient {

    public AllegroApiClient(String baseURL) {
        super(baseURL);
    }

    /***
     * Get IDs of Allegro categories
     * @return CategoriesDto
     */
    public final CategoriesDto callGetCategoriesAsDto(){
        return getCategories().extract().as(CategoriesDto.class);
    }

    /***
     * Get a category by ID
     * @return CategoryDto
     */
    public final CategoryDto callGetCategoryAsDto(final String categoryId){
        return getCategory(categoryId).extract().as(CategoryDto.class);
    }

    /***
     * Get parameters supported by a category
     * @return CategoryParameterList
     */
    public final CategoryParameterList callGetCategoryParametersAsDto(final String categoryId){
        return getCategoryParameters(categoryId).extract().as(CategoryParameterList.class);
    }

    private final ValidatableResponse getCategories(){
        return callGet(AllegroEndpoints.GET_CATEGORIES_PATH.getPath(), getHeadersWithAccept());
    }

    private final ValidatableResponse getCategory(final String categoryId){
        return callGetWithPathParams(AllegroEndpoints.GET_CATEGORIES_BY_ID_PATH.getPath(), Map.of("categoryId", categoryId), getHeadersWithAccept());
    }

    private final ValidatableResponse getCategoryParameters(final String categoryId){
        return callGetWithPathParams(AllegroEndpoints.GET_PARAMETERS_BY_CATEGORY_ID_PATH.getPath(), Map.of("categoryId", categoryId), getHeadersWithAccept());
    }

    /***
     * Generates default headers for Allegro REST Request
     * @return headers as Map<String, Object>
     */
    protected static Map<String, Object> getHeadersWithAccept()
    {
        final Map<String, Object> headers = new HashMap<>();
        headers.put("Accept", "application/vnd.allegro.public.v1+json");
        return headers;
    }
}
