package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.openapitools.client.model.CategoryDto;
import org.openapitools.client.model.CategoryOptionsDto;
import org.openapitools.client.model.CategoryParameterList;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

/***
 * Class responsible for managing the test data
 */

@Getter
public class DataProvider {

    private static DataProvider instance = null;
    private CategoryDto expectedCategoryDto;
    private final String id = "5";
    private final String name = "Dom i Ogród";
    private final boolean leaf = false;
    private static final String resourcesPath = "src/test/resources";
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private DataProvider(){
        expectedCategoryDto = new CategoryDto().id(id).name(name).leaf(leaf).parent(null).options(new CategoryOptionsDto().variantsByColorPatternAllowed(true)
                .advertisement(false).advertisementPriceOptional(false).offersWithProductPublicationEnabled(false).offersWithProductPublicationEnabled(false).productCreationEnabled(false).productEANRequired(false).customParametersEnabled(true));
    }

    /***
     * Not thread-safe implementation of singleton
     * @return instance of this class
     */
    public static DataProvider getInstance(){
        if (instance == null){
            instance = new DataProvider();
        }
        return instance;
    }

    /***
     * Saves CategoryDtoList to categories.json file in test/resources
     * @param categoryDtoList
     */
    public void initializeCategoriesJSON(List<CategoryDto> categoryDtoList){
        try (FileWriter writer = new FileWriter(resourcesPath + "/categories.json")){
            gson.toJson(categoryDtoList, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Saves CategoryParameters for each Category to src/test/resources/parameters/${categoryName}.json
     * @param categoryParameterList
     * @param categoryName
     */
    public void initializeCategoryParametrsJSON(CategoryParameterList categoryParameterList, String categoryName){
        try (FileWriter writer = new FileWriter(resourcesPath + "/parameters/" + categoryName + ".json", Charset.defaultCharset())){
            gson.toJson(categoryParameterList, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Gets categories list from categories.json file in test/resources
     * @return List<CategoryDto>
     */
    public List<CategoryDto> getCategoriesFromJSON() {
        try{
            FileReader reader = new FileReader(resourcesPath + "/categories.json", Charset.defaultCharset());
            List<CategoryDto> categories = gson.fromJson(reader, new TypeToken<List<CategoryDto>>(){}.getType());
            return categories;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Gets parameters list for Category from src/test/resources/parameters/${categoryName}.json
     * @param categoryName
     * @return CategoryParameterList
     */
    public CategoryParameterList getCategoryParametersFromJSON(String categoryName) {
        try{
            FileReader reader = new FileReader(resourcesPath + "/parameters/" + categoryName + ".json", Charset.defaultCharset());
            CategoryParameterList categoryParameters = gson.fromJson(reader, CategoryParameterList.class);
            return categoryParameters;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Returns random Category from categories.json file in test/resources
     * @return CategoryDto
     */
    public CategoryDto getRandomCategoryFromJSON(){
        var categoriesFromJSON = getCategoriesFromJSON();
        int randomIndex = new Random().nextInt(categoriesFromJSON.size());
        CategoryDto desiredCategory = categoriesFromJSON.get(randomIndex);
        return desiredCategory;
    }
}
