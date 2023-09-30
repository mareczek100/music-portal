package mareczek100.musiccontests.integration.integration_test_support;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import mareczek100.musiccontests.api.dto.HeadmasterDto;
import mareczek100.musiccontests.api.dto.MusicSchoolWithAddressDto;
import mareczek100.musiccontests.api.dto.StudentDto;
import mareczek100.musiccontests.api.dto.TeacherDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.MusicSchoolsDto;
import org.springframework.http.HttpStatus;

import static mareczek100.musiccontests.api.controller.rest_controller.MusicContestsUserRestController.*;

public interface MusicContestsUserRestControllerITSupport {

    RequestSpecification requestSpecification();

    default HeadmasterDto createHeadmaster(HeadmasterDto headmasterDto, String password){
        return requestSpecification()
                .given()
                .body(headmasterDto)
                .queryParam("password", password)
                .when()
                .post(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + CREATE_HEADMASTER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(HeadmasterDto.class);
    }
    default TeacherDto createTeacher(TeacherDto teacherDto, String password){
        return requestSpecification()
                .given()
                .body(teacherDto)
                .queryParam("password", password)
                .when()
                .post(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + CREATE_TEACHER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(TeacherDto.class);
    }
    default StudentDto createStudent(StudentDto studentDto, String password){
        return requestSpecification()
                .given()
                .body(studentDto)
                .queryParam("password", password)
                .when()
                .post(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + CREATE_STUDENT)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(StudentDto.class);
    }
    default MusicSchoolWithAddressDto findMusicSchoolById(String musicSchoolId){
        return requestSpecification()
                .given()
                .param("musicSchoolId", musicSchoolId)
                .when()
                .get(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + FIND_MUSIC_SCHOOL_BY_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(MusicSchoolWithAddressDto.class);

    }
    default MusicSchoolWithAddressDto findMusicSchoolByPatron(String patron){
        return requestSpecification()
                .given()
                .param("patron", patron)
                .when()
                .get(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + FIND_MUSIC_SCHOOL_BY_PATRON)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(MusicSchoolWithAddressDto.class);
    }
    default Response findMusicSchoolByPatronResponseMessageIfSchoolWithSuchPatronDoesNotExists(String patron){
        return requestSpecification()
                .given()
                .param("patron", patron)
                .when()
                .get(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + FIND_MUSIC_SCHOOL_BY_PATRON)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract()
                .response();
    }
    default MusicSchoolsDto findAllMusicSchools(){
        return requestSpecification()
                .when()
                .get(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + FIND_ALL_MUSIC_SCHOOLS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(MusicSchoolsDto.class);
    }
    default MusicSchoolWithAddressDto createMusicSchoolWithAddress(
            MusicSchoolWithAddressDto musicSchoolWithAddressDto
    ){
        return requestSpecification()
                .given()
                .body(musicSchoolWithAddressDto)
                .when()
                .post(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + CREATE_MUSIC_SCHOOL)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .as(MusicSchoolWithAddressDto.class);
    }
    default Response deleteMusicContestsUserAccount(String userEmail)
    {
        return requestSpecification()
                .given()
                .param("userEmail", userEmail)
                .when()
                .delete(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + MUSIC_CONTESTS_USER_DELETE_ACCOUNT)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .and()
                .extract()
                .response();
    }
    default Response deleteMusicContestsUserThrowsExceptionIfBadInput(String userEmail)
    {
        return requestSpecification()
                .given()
                .param("userEmail", userEmail)
                .when()
                .delete(MUSIC_CONTESTS_USER_REST_MAIN_PAGE + MUSIC_CONTESTS_USER_DELETE_ACCOUNT)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .and()
                .extract()
                .response();
    }
}