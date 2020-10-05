import helpers.DataProvider;
import org.assertj.core.api.SoftAssertions;
import org.openapitools.client.model.CategoryParameterList;
import org.testng.annotations.BeforeClass;
import pl.allegroREST.client.AllegroApiClient;
import pl.allegroREST.client.auth.TokenProvider;
import pl.allegroREST.config.Config;
import pl.allegroREST.config.ConfigReader;

public class BasicTests {

    protected AllegroApiClient allegroApiClient = new AllegroApiClient("https://api.allegro.pl");
    protected DataProvider provider = DataProvider.getInstance();
    protected SoftAssertions softly = new SoftAssertions();

    @BeforeClass
    public void setUp(){
        Config.initTestsProperties();
        allegroApiClient.setToken(TokenProvider.getToken());
        boolean updateData = Boolean.parseBoolean(ConfigReader.getTestPropertyValue("update_data"));
        if(updateData){
            var categories = allegroApiClient.callGetCategoriesAsDto().getCategories();
            provider.initializeCategoriesJSON(categories);
            categories.stream().forEach(categoryDto -> {
                final CategoryParameterList categoryParameterList = allegroApiClient.callGetCategoryParametersAsDto(categoryDto.getId());
                provider.initializeCategoryParametrsJSON(categoryParameterList, categoryDto.getName());
            });
        }
    }
}
