package mareczek100.musiccontests.integration.integration_test_support;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mareczek100.musiccontests.api.dto.ApplicationFormDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static mareczek100.musiccontests.api.controller.rest_controller.HeadmasterRestController.*;
import static mareczek100.musiccontests.api.controller.rest_controller.TeacherRestController.TEACHER_REST_MAIN_PAGE;

public interface TeacherRestControllerITSupport {

    RequestSpecification requestSpecification();

    default InstrumentsDto findAllAvailableInstruments(){
        return requestSpecification()
                .when()
                .get(TEACHER_REST_MAIN_PAGE + FIND_ALL_INSTRUMENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(InstrumentsDto.class);

    }
    default CitiesDto findAllAvailableCompetitionCities(){
        return requestSpecification()
                .when()
                .get(TEACHER_REST_MAIN_PAGE + FIND_ALL_CITIES)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CitiesDto.class);

    }
    default ClassLevels findAllClassLevels(){
        return requestSpecification()
                .when()
                .get(TEACHER_REST_MAIN_PAGE + FIND_AVAILABLE_CLASS_LEVELS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(ClassLevels.class);
    }

    default CompetitionsDto findAllAvailableCompetitions(){
        return requestSpecification()
                .when()
                .get(TEACHER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS)
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
                .get(TEACHER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS_PAGEABLE, currentPage)
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
                .get(TEACHER_REST_MAIN_PAGE + FIND_ALL_COMPETITIONS_PAGEABLE, currentPage)
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
                .get(TEACHER_REST_MAIN_PAGE + FIND_AVAILABLE_COMPETITIONS_BY_FILTERS)
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
                .get(TEACHER_REST_MAIN_PAGE + FIND_AVAILABLE_COMPETITIONS_BY_INSTRUMENT)
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
                .get(TEACHER_REST_MAIN_PAGE + FIND_FINISHED_COMPETITIONS_BY_FILTERS)
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
                .get(TEACHER_REST_MAIN_PAGE + FIND_FINISHED_COMPETITIONS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(CompetitionsDto.class);
    }
    default StudentsDto findAllTeacherStudents(
            String teacherEmail
    )
    {
        return requestSpecification()
                .given()
                .param("teacherEmail", teacherEmail)
                .when()
                .get(TEACHER_REST_MAIN_PAGE + FIND_ALL_TEACHER_STUDENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(StudentsDto.class);
    }
    default Response thatFindAllTeacherStudentsThrowsExceptionIfTeacherDoesNotExist(
            String teacherEmail
    )
    {
        return requestSpecification()
                .given()
                .param("teacherEmail", teacherEmail)
                .when()
                .get(TEACHER_REST_MAIN_PAGE + FIND_ALL_TEACHER_STUDENTS)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .extract()
                .response();
    }

    default ApplicationFormDto announceStudentToCompetition(
            String teacherEmail,
            String studentId,
            String competitionId,
            String classLevel,
            String performancePieces
    )
    {
        return requestSpecification()
                .given()
                .queryParams(Map.of(
                        "teacherEmail", teacherEmail,
                        "studentId", studentId,
                        "competitionId", competitionId,
                        "classLevel", classLevel,
                        "performancePieces", performancePieces
                ))
                .when()
                .post(TEACHER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_TO_COMPETITION)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .as(ApplicationFormDto.class);
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
                .patch(TEACHER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_UPDATE, applicationFormId)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .and()
                .extract()
                .response();
    }

    default ApplicationFormsDto findTeacherApplicationsToCompetition(
            String teacherEmail,
            String competitionId
    )
    {
        return requestSpecification()
                .given()
                .params(Map.of(
                        "teacherEmail", teacherEmail,
                        "competitionId", competitionId
                ))
                .when()
                .get(TEACHER_REST_MAIN_PAGE + FIND_TEACHER_APPLICATIONS)
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
                .delete(TEACHER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_CANCEL)
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
                .delete(TEACHER_REST_MAIN_PAGE + ANNOUNCE_STUDENT_CANCEL)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .extract()
                .response();
    }

}