
import helpers.DataProvider;
import org.assertj.core.api.Assertions;
import org.openapitools.client.model.CategoriesDto;
import org.openapitools.client.model.CategoryDto;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest extends BasicTest {

    @Test
    public void checkResponseForCategoryByIdTest(){
        final String categoryId = "5";
        final CategoryDto categoryDto = allegroApiClient.callGetCategoryAsDto(categoryId);
        assertThat(categoryDto).isEqualTo(provider.getExpectedCategoryDto());
    }

    @Test
    public void checkIntegrityListWithSingleCategoryTest(){
        final CategoriesDto categoriesDto = allegroApiClient.callGetCategoriesAsDto();
        if (categoriesDto.getCategories().isEmpty())
            Assertions.fail("Categories are empty!");
        categoriesDto.getCategories().stream().forEach(cat -> {
            CategoryDto categoryDto = allegroApiClient.callGetCategoryAsDto(cat.getId());
            assertThat(cat).usingRecursiveComparison().isEqualTo(categoryDto);
        });
    }
}
