package mareczek100.musiccontests.integration;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CitiesDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CompetitionsDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.InstrumentsDto;
import mareczek100.musiccontests.business.StudentService;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.integration.integration_test_support.StudentRestControllerITSupport;
import mareczek100.musiccontests.integration.integration_test_support.WireMockInstrumentStorageITSupport;
import mareczek100.musiccontests.test_configuration.RestAssuredITConfig;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentIT extends RestAssuredITConfig
        implements StudentRestControllerITSupport, WireMockInstrumentStorageITSupport {

    private final StudentService studentService;

    @BeforeEach
    void thatSetUpWorksCorrectly() {
        org.junit.jupiter.api.Assertions.assertNotNull(studentService);
    }

    @Test
    void thatFindAllAvailableInstrumentsWorksCorrectly() {
        //given
        int instruments = 43;

        //when
        stubForAllInstrumentList(wireMockServer);
        InstrumentsDto allAvailableInstruments = findAllAvailableInstruments();

        //then
        Assertions.assertThatCollection(allAvailableInstruments.instrumentDtoList()).isNotEmpty();
        Assertions.assertThatCollection(allAvailableInstruments.instrumentDtoList()).hasSize(instruments);
    }

    @Test
    void thatFindAllAvailableCompetitionCitiesWorksCorrectly() {
        //given

        CitiesDto allAvailableCompetitionCities = findAllAvailableCompetitionCities();

        //then
        Assertions.assertThatCollection(allAvailableCompetitionCities.competitionCitiesDtoList()).isNotEmpty();

    }

    @Test
    void findAllAvailableCompetitionsWorksCorrectly()
    {
        //given, when
        CompetitionsDto allAvailableCompetitions = findAllAvailableCompetitions();

        //then
        Assertions.assertThatCollection(allAvailableCompetitions.competitionDtoList()).isNotEmpty();
    }

    @Test
    void findAllAvailableCompetitionsWithSortingAndPagingWorksCorrectly()
    {
        //given
        Integer currentPage = 2;

        //when
        CompetitionsDto allAvailableCompetitions
                = findAllAvailableCompetitionsWithSortingAndPagingWorksCorrectly(currentPage);

        //then
        Assertions.assertThatCollection(allAvailableCompetitions.competitionDtoList()).isNotEmpty();
        Assertions.assertThatCollection(allAvailableCompetitions.competitionDtoList())
                .hasSize(5);
    }
    @Test
    void findAllAvailableCompetitionsResponseMessageIfPageIsEmpty()
    {
        //given
        Integer currentPage = 33;
        String problemDetailMessage = "No more competitions, page doesn't exist!";

        //when
        Response response
                = findAllAvailableCompetitionsResponseMessageIfPageIsEmpty(currentPage);

        //then
        Assertions.assertThat(response.asString()).contains(problemDetailMessage);
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void thatFindAvailableCompetitionsByFiltersWorksCorrectly() {
        //given
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        Map<String, Object> competitionParameters = new HashMap<>();
        competitionParameters.put("competitionInstrument", competition.competitionInstrument());
        competitionParameters.put("competitionOnline", competition.competitionOnline());
        competitionParameters.put("competitionPrimaryDegree", competition.competitionPrimaryDegree());
        competitionParameters.put("competitionSecondaryDegree", competition.competitionSecondaryDegree());
        competitionParameters.put("competitionCity", competition.addressCity());

        //when
        CompetitionsDto availableCompetitionsByFilters = findAvailableCompetitionsByFilters(competitionParameters);

        //then
        Assertions.assertThatCollection(availableCompetitionsByFilters.competitionDtoList()).isNotEmpty();
        Assertions.assertThatCollection(availableCompetitionsByFilters.competitionDtoList())
                .usingRecursiveFieldByFieldElementComparatorOnFields(
                        "competitionInstrument", "competitionOnline", "competitionPrimaryDegree",
                        "competitionSecondaryDegree", "addressCity").contains(competition);
    }
    @Test
    void thatFindAllAvailableCompetitionsByInstrumentWorksCorrectly() {
        //given
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();

        //when
        CompetitionsDto allAvailableCompetitionsByInstrument
                = findAllAvailableCompetitionsByInstrument(competition.competitionInstrument());

        //then
        Assertions.assertThatCollection(allAvailableCompetitionsByInstrument.competitionDtoList()).isNotEmpty();
        Assertions.assertThatCollection(allAvailableCompetitionsByInstrument.competitionDtoList())
                .usingRecursiveFieldByFieldElementComparatorOnFields("competitionInstrument").contains(competition);
    }

    @Test
    void thatFindFinishedStudentCompetitionsByFiltersAndStudentPeselResponseMessageIfNoSuchCompetitions() {
        //given
        Student student = StudentDomainTestData.studentToSave1();

        Map<String, String> competitionParameters = new HashMap<>();
        competitionParameters.put("competitionDateFrom", LocalDate.now().toString());
        competitionParameters.put("competitionDateTo", LocalDate.now().toString());
        competitionParameters.put("competitionCity", "competitionCity");
        competitionParameters.put("studentPesel", student.pesel());

        String problemDetailMessage = "No such finished competitions in which You participated!";

        //when
        Response response
                = thatFindFinishedStudentCompetitionsByFiltersResponseMessageIfNoSuchCompetitions(competitionParameters);

        //then
        Assertions.assertThat(response.asPrettyString()).contains(problemDetailMessage);
    }
    @Test
    void thatFindAllFinishedStudentCompetitionsResponseMessageIfNoSuchCompetitions() {
        //given
        Student student = StudentDomainTestData.studentToSave1();

        String problemDetailMessage = "There is no finished competitions in which You participated!";

        //when
        Response response
                = thatFindAllFinishedStudentCompetitionsResponseMessageIfNoCompetitions(student.pesel());

        //then
        Assertions.assertThat(response.asPrettyString()).contains(problemDetailMessage);
    }
    @Test
    void thatFindFinishedStudentCompetitionsByFiltersAndStudentPeselThrowsExceptionIfPeselIsBadFormatted() {
        //given
        String badPesel = "bad pesel format";

        Map<String, String> competitionParameters = new HashMap<>();
        competitionParameters.put("competitionDateFrom", LocalDate.now().toString());
        competitionParameters.put("competitionDateTo", LocalDate.now().toString());
        competitionParameters.put("competitionCity", "competitionCity");
        competitionParameters.put("studentPesel", badPesel);

        String errorMessage = "studentPesel: must match \\\"^\\\\d{11}$\\\"";

        //when
        Response response
                = thatFindFinishedStudentCompetitionsByFiltersThrowsExceptionIfPeselIsBadFormatted(competitionParameters);

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.asPrettyString()).contains(errorMessage);
    }
    @Test
    void thatCheckStudentResultResponseMessageIfNoSuchCompetitions() {
        //given
        Student student = StudentDomainTestData.studentToSave1();
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();

        //when
        Response response
                = checkStudentResultResponseMessageIfNoSuchCompetitions(
                        competition.competitionId(), student.pesel()
        );

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void thatCheckStudentResultThrowsExceptionIfStudentPeselHasBadFormat() {
        //given
        String badPesel = "bad pesel format";
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();

        String errorMessage = "studentPesel: must match \\\"^\\\\d{11}$\\\"";

        //when
        Response response
                = checkStudentResultThrowsExceptionIfStudentPeselHasBadFormat(
                        competition.competitionId(), badPesel
        );

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(response.asPrettyString()).contains(errorMessage);
    }

}