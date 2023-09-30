package mareczek100.instrumentstorage.test_configuration.integration_test_support;

import io.restassured.specification.RequestSpecification;
import mareczek100.instrumentstorage.api.controller.InstrumentCategoryRestController;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoriesDto;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoryDto;
import org.springframework.http.HttpStatus;

public interface InstrumentCategoryControllerTestSupport {

    RequestSpecification requestSpecification();
    default InstrumentCategoriesDto listOfAllInstrumentCategories() {
        return requestSpecification()
                .get(InstrumentCategoryRestController.API_INSTRUMENT_CATEGORY)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentCategoriesDto.class);
    }
    default InstrumentCategoryDto findInstrumentCategoryById(final Short instrumentCategoryId) {
        return requestSpecification()
                .given()
                .when()
                .get(InstrumentCategoryRestController.API_INSTRUMENT_CATEGORY +
                        InstrumentCategoryRestController.FIND_INSTRUMENT_CATEGORY_BY_ID,
                        instrumentCategoryId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentCategoryDto.class);
    }
    default InstrumentCategoryDto findInstrumentCategoryByCategoryName(final String instrumentCategoryName) {
        return requestSpecification()
                .given()
                .when()
                .get(InstrumentCategoryRestController.API_INSTRUMENT_CATEGORY +
                        InstrumentCategoryRestController.FIND_INSTRUMENT_CATEGORY_BY_CATEGORY_NAME,
                        instrumentCategoryName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentCategoryDto.class);
    }
}
