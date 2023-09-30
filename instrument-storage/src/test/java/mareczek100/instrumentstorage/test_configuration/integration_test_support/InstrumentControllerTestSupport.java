package mareczek100.instrumentstorage.test_configuration.integration_test_support;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mareczek100.instrumentstorage.api.controller.InstrumentRestController;
import mareczek100.instrumentstorage.api.dto.InstrumentDto;
import mareczek100.instrumentstorage.api.dto.InstrumentsDto;
import org.springframework.http.HttpStatus;

public interface InstrumentControllerTestSupport {

    RequestSpecification requestSpecification();
    default InstrumentsDto listOfAllInstruments() {
        return requestSpecification()
                .get(InstrumentRestController.API_INSTRUMENT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentsDto.class);
    }

    default InstrumentDto insertInstrument(final InstrumentDto instrumentDto) {
        return requestSpecification()
                .given()
                .body(instrumentDto)
                .when()
                .post(InstrumentRestController.API_INSTRUMENT +
                        InstrumentRestController.ADD_INSTRUMENT)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .as(InstrumentDto.class);
    }
    default InstrumentDto updateExistingInstrument(final String oldInstrumentName, final String newInstrumentName) {
        return requestSpecification()
                .given()
                .params("oldInstrumentName", oldInstrumentName,
                        "newInstrumentName", newInstrumentName)
                .when()
                .patch(InstrumentRestController.API_INSTRUMENT +
                        InstrumentRestController.UPDATE_INSTRUMENT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentDto.class);
    }

    default InstrumentDto findInstrumentById(final Integer instrumentId) {
        return requestSpecification()
                .given()
                .when()
                .get(InstrumentRestController.API_INSTRUMENT +
                        InstrumentRestController.FIND_INSTRUMENT_BY_ID, instrumentId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentDto.class);
    }
    default InstrumentDto findInstrumentByName(final String instrumentName) {
        return requestSpecification()
                .given()
                .when()
                .get(InstrumentRestController.API_INSTRUMENT +
                        InstrumentRestController.FIND_INSTRUMENT_BY_NAME, instrumentName)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentDto.class);
    }
    default InstrumentsDto findInstrumentsByCategory(final String instrumentCategory) {
        return requestSpecification()
                .given()
                .when()
                .get(InstrumentRestController.API_INSTRUMENT +
                        InstrumentRestController.FIND_INSTRUMENTS_BY_CATEGORY, instrumentCategory)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentsDto.class);
    }
    default Response deleteInstrumentByName(final String instrumentName) {
        return requestSpecification()
                .given()
                .param("instrumentName", instrumentName)
                .when()
                .delete(InstrumentRestController.API_INSTRUMENT +
                        InstrumentRestController.DELETE_INSTRUMENT_BY_NAME)
                .thenReturn();
    }

}