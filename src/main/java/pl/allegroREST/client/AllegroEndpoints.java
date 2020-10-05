package pl.allegroREST.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/***
 * Endpoints path constants
 */
@RequiredArgsConstructor
public enum AllegroEndpoints {
    TOKEN_PATH("/token"), AUTHORIZE_PATH("/authorize"), GET_CATEGORIES_PATH("/sale/categories"),
    GET_CATEGORIES_BY_ID_PATH("/sale/categories/{categoryId}"), GET_PARAMETERS_BY_CATEGORY_ID_PATH("/sale/categories/{categoryId}/parameters");

    @Getter
    private final String path;
}
