
import org.testng.annotations.Test;

public class CategoryParametersTests extends BasicTests {

    @Test
    public void checkRandomCategoryParametersTest() {
        var randomCategory = provider.getRandomCategoryFromJSON();
        var categoryParametersFromJSON = provider.getCategoryParametersFromJSON(randomCategory.getName());
        var actualCategoryParameters = allegroApiClient.callGetCategoryParametersAsDto(randomCategory.getId());
        softly.assertThat(categoryParametersFromJSON).usingRecursiveComparison().isEqualTo(actualCategoryParameters);
        softly.assertAll();
    }
}
