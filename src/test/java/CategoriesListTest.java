
import org.testng.annotations.Test;

public class CategoriesListTest extends BasicTest {

    @Test
    public void checkRandomCategoryTest() {
        var categoriesFromJSON = provider.getCategoriesFromJSON();
        var result = allegroApiClient.callGetCategoriesAsDto();
        var desiredCategory = provider.getRandomCategoryFromJSON();
        softly.assertThat(categoriesFromJSON.size()).isEqualTo(result.getCategories().size());
        var actualCategory = result.getCategories().stream().filter(cat -> cat.getId().equals(desiredCategory.getId())).findAny().get();
        softly.assertThat(desiredCategory).usingRecursiveComparison().isEqualTo(actualCategory);
        softly.assertAll();
    }
}
