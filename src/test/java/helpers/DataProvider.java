package helpers;

import lombok.Getter;
import org.openapitools.client.model.CategoryDto;
import org.openapitools.client.model.CategoryOptionsDto;

@Getter
public class CategoryDataConstans {

    private static CategoryDataConstans instance = null;
    private CategoryDto expectedCategoryDto;
    private final String id = "5";
    private final String name = "Dom i Ogród";
    private final boolean leaf = false;

    private CategoryDataConstans(){
        expectedCategoryDto = new CategoryDto().id(id).name(name).leaf(leaf).parent(null).options(new CategoryOptionsDto().variantsByColorPatternAllowed(true)
                .advertisement(false).advertisementPriceOptional(false).offersWithProductPublicationEnabled(false).offersWithProductPublicationEnabled(false).productCreationEnabled(false).productEANRequired(false).customParametersEnabled(true));
    }

    public static CategoryDataConstans getInstance(){
        if (instance == null){
            instance = new CategoryDataConstans();
        }
        return instance;
    }
}
