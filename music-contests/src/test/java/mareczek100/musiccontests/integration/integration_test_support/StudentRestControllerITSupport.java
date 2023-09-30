package mareczek100.musiccontests.integration.integration_test_support;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mareczek100.musiccontests.api.dto.dto_rest_support.CitiesDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CompetitionsDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.InstrumentsDto;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.CHECK_RESULT;
import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.FIND_ALL_CITIES;
import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.FIND_ALL_COMPETITIONS;
import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.FIND_ALL_COMPETITIONS_PAGEABLE;
import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.FIND_ALL_INSTRUMENTS;
import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.FIND_AVAILABLE_COMPETITIONS_BY_FILTERS;
import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.FIND_AVAILABLE_COMPETITIONS_BY_INSTRUMENT;
import static mareczek100.musiccontests.api.controller.rest_controller.StudentRestController.*;

public interface StudentRestControllerITSupport {

    RequestSpecification requestSpecification();

    default InstrumentsDto findAllAvailableInstruments(){
        return requestSpecification()
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_ALL_INSTRUMENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentsDto.class);

    }
    default CitiesDto findAllAvailableCompetitionCities(){
        return requestSpecification()
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_ALL_CITIES)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CitiesDto.class);

    }
    default CompetitionsDto findAllAvailableCompetitions(){
        return requestSpecification()
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }
    default CompetitionsDto findAllAvailableCompetitionsWithSortingAndPagingWorksCorrectly(
            Integer currentPage
    ){
        return requestSpecification()
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS_PAGEABLE, currentPage)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }
    default Response findAllAvailableCompetitionsResponseMessageIfPageIsEmpty(
            Integer currentPage
    ){
        return requestSpecification()
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS_PAGEABLE, currentPage)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();
    }
    default CompetitionsDto findAvailableCompetitionsByFilters(Map<String, ?> parameters)
    {
        return requestSpecification()
                .given()
                .params(parameters)
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_AVAILABLE_COMPETITIONS_BY_FILTERS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }
    default CompetitionsDto findAllAvailableCompetitionsByInstrument(
            String competitionInstrument
    )
    {
        return requestSpecification()
                .given()
                .param("competitionInstrument", competitionInstrument)
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_AVAILABLE_COMPETITIONS_BY_INSTRUMENT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }
    default Response thatFindFinishedStudentCompetitionsByFiltersResponseMessageIfNoSuchCompetitions(
            Map<String, ?> parameters
    )
    {
        return requestSpecification()
                .given()
                .params(parameters)
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_STUDENT_COMPETITIONS_BY_FILTERS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();
    }
    default Response thatFindAllFinishedStudentCompetitionsResponseMessageIfNoCompetitions(
            String studentPesel
    )
    {
        return requestSpecification()
                .given()
                .param("studentPesel", studentPesel)
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_STUDENT_COMPETITIONS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();
    }
    default Response thatFindFinishedStudentCompetitionsByFiltersThrowsExceptionIfPeselIsBadFormatted(
            Map<String, ?> parameters
    )
    {
        return requestSpecification()
                .given()
                .params(parameters)
                .when()
                .get(STUDENT_REST_MAIN_PAGE + FIND_STUDENT_COMPETITIONS_BY_FILTERS)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .extract()
                .response();
    }
    default Response checkStudentResultResponseMessageIfNoSuchCompetitions(
            String competitionId, String studentPesel
    )
    {
        return requestSpecification()
                .given()
                .param("competitionId", competitionId)
                .param("studentPesel", studentPesel)
                .when()
                .get(STUDENT_REST_MAIN_PAGE + CHECK_RESULT)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();
    }
    default Response checkStudentResultThrowsExceptionIfStudentPeselHasBadFormat(
            String competitionId, String studentPesel
    )
    {
        return requestSpecification()
                .given()
                .param("competitionId", competitionId)
                .param("studentPesel", studentPesel)
                .when()
                .get(STUDENT_REST_MAIN_PAGE + CHECK_RESULT)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .extract()
                .response();
    }
}