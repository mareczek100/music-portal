package mareczek100.musiccontests.integration.integration_test_support;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mareczek100.musiccontests.api.dto.ApplicationFormDto;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.TeacherDto;
import mareczek100.musiccontests.api.dto.dto_class_support.CompetitionResultListDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.*;

public interface HeadmasterRestControllerITSupport {

    RequestSpecification requestSpecification();

    default InstrumentsDto findAllAvailableInstruments(){
        return requestSpecification()
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_INSTRUMENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentsDto.class);

    }
    default CitiesDto findAllAvailableCompetitionCities(){
        return requestSpecification()
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_CITIES)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CitiesDto.class);

    }
    default ClassLevels findAllClassLevels(){
        return requestSpecification()
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_AVAILABLE_CLASS_LEVELS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(ClassLevels.class);
    }

    default CompetitionWithLocationDto createCompetitionAtSchool(Map<String, Object> parameters)
    {
        return requestSpecification()
                .given()
                .queryParams(parameters)
                .when()
                .post(HEADMASTER_REST_MAIN_PAGE + CREATE_COMPETITION_AT_SCHOOL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .as(CompetitionWithLocationDto.class);
    }

    default CompetitionWithLocationDto createCompetitionAtOtherLocation(
            CompetitionWithLocationDto competitionDto,
            String headmasterOrganizerEmail
    )
    {
        return requestSpecification()
                .given()
                .body(competitionDto)
                .queryParam("headmasterOrganizerEmail", headmasterOrganizerEmail)
                .when()
                .post(HEADMASTER_REST_MAIN_PAGE + CREATE_COMPETITION_AT_OTHER_LOCATION)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .as(CompetitionWithLocationDto.class);

    }

    default CompetitionsDto findAllAvailableCompetitions(){
        return requestSpecification()
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS)
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
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS_PAGEABLE, currentPage)
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
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS_PAGEABLE, currentPage)
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
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_AVAILABLE_COMPETITIONS_BY_FILTERS)
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
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_AVAILABLE_COMPETITIONS_BY_INSTRUMENT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }

    default CompetitionsDto findFinishedCompetitionsByFilters(Map<String, ?> parameters)
    {
        return requestSpecification()
                .given()
                .params(parameters)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_FINISHED_COMPETITIONS_BY_FILTERS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }
    default CompetitionsDto findAllFinishedCompetitions()
    {
        return requestSpecification()
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_FINISHED_COMPETITIONS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }

    default TeacherDto createHeadmasterTeacherRightsCorrectly(
            String headmasterEmail,
            String instrument
    )
    {
        return requestSpecification()
                .given()
                .queryParam("headmasterEmail", headmasterEmail)
                .queryParam("instrument", instrument)
                .when()
                .post(HEADMASTER_REST_MAIN_PAGE + CREATE_TEACHER_RIGHTS)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .as(TeacherDto.class);

    }
    default Response createHeadmasterTeacherRightsAccountAlreadyExistsResponseProblemDetail(
            String headmasterEmail,
            String instrument
    )
    {
        return requestSpecification()
                .given()
                .queryParam("instrument", instrument)
                .queryParam("headmasterEmail", headmasterEmail)
                .when()
                .post(HEADMASTER_REST_MAIN_PAGE + CREATE_TEACHER_RIGHTS)
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .and()
                .extract()
                .response();

    }

    default TeachersDto findAllTeachers(String headmasterEmail) {
        return requestSpecification()
                .given()
                .param("headmasterEmail", headmasterEmail)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_TEACHERS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(TeachersDto.class);

    }
    default Response findAllTeachersResponseMessageIfNoTeachers(String headmasterEmail) {
        return requestSpecification()
                .given()
                .param("headmasterEmail", headmasterEmail)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_TEACHERS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();

    }

    default StudentsDto findAllTeacherStudents(
            String headmasterEmail
    )
    {
        return requestSpecification()
                .given()
                .param("headmasterEmail", headmasterEmail)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_TEACHER_STUDENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(StudentsDto.class);
    }
    default Response findAllTeacherStudentsResponseMessageIfNoStudents(
            String headmasterEmail
    )
    {
        return requestSpecification()
                .given()
                .param("headmasterEmail", headmasterEmail)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_TEACHER_STUDENTS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();
    }

    default StudentsDto findAllCompetitionStudents(String competitionId) {
        return requestSpecification()
                .given()
                .param("competitionId", competitionId)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_COMPETITION_STUDENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(StudentsDto.class);

    }

    default Response findAllCompetitionStudentsEmptyListResponseNotFoundStatus(String competitionId) {
        return requestSpecification()
                .given()
                .param("competitionId", competitionId)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_COMPETITION_STUDENTS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();
    }

    default StudentsDto findAllSchoolStudents(String headmasterEmail) {
        return requestSpecification()
                .given()
                .param("headmasterEmail", headmasterEmail)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_SCHOOL_STUDENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(StudentsDto.class);
    }

    default ApplicationFormDto announceStudentToCompetition(
            String headmasterEmail,
            String studentId,
            String competitionId,
            String classLevel,
            String performancePieces
    )
    {
        return requestSpecification()
                .given()
                .queryParams(Map.of(
                        "headmasterEmail", headmasterEmail,
                        "studentId", studentId,
                        "competitionId", competitionId,
                        "classLevel", classLevel,
                        "performancePieces", performancePieces
                ))
                .when()
                .post(HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_TO_COMPETITION)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .as(ApplicationFormDto.class);
    }
    default Response announceStudentToCompetitionResponseMessageIfItIsAfterDeadline(
            String headmasterEmail,
            String studentId,
            String competitionId,
            String classLevel,
            String performancePieces
    )
    {
        return requestSpecification()
                .given()
                .queryParams(Map.of(
                        "headmasterEmail", headmasterEmail,
                        "studentId", studentId,
                        "competitionId", competitionId,
                        "classLevel", classLevel,
                        "performancePieces", performancePieces
                ))
                .when()
                .post(HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_TO_COMPETITION)
                .then()
                .statusCode(HttpStatus.REQUEST_TIMEOUT.value())
                .and()
                .extract()
                .response();
    }
    default Response updateStudentApplicationForm(
            String applicationFormId,
            String classLevel,
            String performancePieces
    )
    {
        return requestSpecification()
                .given()
                .queryParams(Map.of(
                        "classLevel", classLevel,
                        "performancePieces", performancePieces
                ))
                .when()
                .patch(HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_UPDATE, applicationFormId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .and()
                .extract()
                .response();
    }

    default ApplicationFormsDto findTeacherApplicationsToCompetition(
            String headmasterEmail,
            String competitionId
    )
    {
        return requestSpecification()
                .given()
                .params(Map.of(
                        "headmasterEmail", headmasterEmail,
                        "competitionId", competitionId
                ))
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_TEACHER_APPLICATIONS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(ApplicationFormsDto.class);
    }

    default ApplicationFormsDto findAllTeachersApplicationsToCompetition(
            String headmasterEmail,
            String competitionId
    )
    {
        return requestSpecification()
                .given()
                .param("headmasterEmail", headmasterEmail)
                .param("competitionId", competitionId)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_ALL_TEACHERS_APPLICATIONS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(ApplicationFormsDto.class);

    }
    default Response announceStudentToCompetitionCancel(
            String competitionId,
            String studentId
    )
    {
        return requestSpecification()
                .given()
                .param("studentId", studentId)
                .param("competitionId", competitionId)
                .when()
                .delete(HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_CANCEL)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .and()
                .extract()
                .response();
    }
    default Response announceStudentToCompetitionCancelThrowsExceptionIfStudentIsNotAnnouncedToCompetition(
            String competitionId,
            String studentId
    )
    {
        return requestSpecification()
                .given()
                .param("studentId", studentId)
                .param("competitionId", competitionId)
                .when()
                .delete(HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_CANCEL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .extract()
                .response();
    }
    default Response announceStudentToCompetitionCancelResponseMessageIfItIsTooLateToCancel(
            String competitionId,
            String studentId
    )
    {
        return requestSpecification()
                .given()
                .param("studentId", studentId)
                .param("competitionId", competitionId)
                .when()
                .delete(HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_CANCEL)
                .then()
                .statusCode(HttpStatus.REQUEST_TIMEOUT.value())
                .and()
                .extract()
                .response();
    }

    default CompetitionsDto findCompetitionsCreatedByHeadmaster(String headmasterEmail) {
        return requestSpecification()
                .given()
                .param("headmasterEmail", headmasterEmail)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_HEADMASTER_COMPETITIONS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }
    default Response findCompetitionsCreatedByHeadmasterResponseMessageIfHeadmasterDidNotCreatedAnyCompetitions(
            String headmasterEmail
    ) {
        return requestSpecification()
                .given()
                .param("headmasterEmail", headmasterEmail)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + FIND_HEADMASTER_COMPETITIONS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();
    }

    default CompetitionResultsDto announceCompetitionResults(
            String competitionId,
            CompetitionResultListDto resultListDto
    )
    {
        return requestSpecification()
                .given()
                .body(resultListDto)
                .queryParam("competitionId", competitionId)
                .when()
                .post(HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_RESULT)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .as(CompetitionResultsDto.class);
    }
    default Response announceCompetitionResultsResponseMessageIfResultsAreEmpty(
            String competitionId,
            CompetitionResultListDto resultListDto
    )
    {
        return requestSpecification()
                .given()
                .body(resultListDto)
                .queryParam("competitionId", competitionId)
                .when()
                .post(HEADMASTER_REST_MAIN_PAGE + ANNOUNCE_RESULT)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .extract()
                .response();
    }

    default CompetitionResultsDto checkTeacherStudentsResults(
            String competitionId,
            String headmasterEmail
    )
    {
        return requestSpecification()
                .given()
                .param("competitionId", competitionId)
                .param("headmasterEmail", headmasterEmail)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + CHECK_RESULT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionResultsDto.class);
    }

    default CompetitionResultsDto checkAllCompetitionResults(String competitionId) {
        return requestSpecification()
                .given()
                .param("competitionId", competitionId)
                .when()
                .get(HEADMASTER_REST_MAIN_PAGE + CHECK_ALL_RESULT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionResultsDto.class);
    }

}
