package mareczek100.musiccontests.integration;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.api.dto.ApplicationFormDto;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.StudentDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.*;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.business.CompetitionLocationService;
import mareczek100.musiccontests.business.CompetitionService;
import mareczek100.musiccontests.business.TeacherService;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.CompetitionLocation;
import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.integration.integration_test_support.TeacherRestControllerITSupport;
import mareczek100.musiccontests.integration.integration_test_support.WireMockInstrumentStorageITSupport;
import mareczek100.musiccontests.test_configuration.RestAssuredITConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherIT extends RestAssuredITConfig
        implements TeacherRestControllerITSupport, WireMockInstrumentStorageITSupport {

    private final CompetitionLocationService competitionLocationService;
    private final CompetitionService competitionService;
    private final CompetitionDtoMapper competitionDtoMapper;
    private final TeacherService teacherService;

    @BeforeEach
    void thatSetUpWorksCorrectly() {
        org.junit.jupiter.api.Assertions.assertNotNull(competitionLocationService);
        org.junit.jupiter.api.Assertions.assertNotNull(competitionService);
        org.junit.jupiter.api.Assertions.assertNotNull(competitionDtoMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(teacherService);
        org.junit.jupiter.api.Assertions.assertNotNull(objectMapper);
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
    void thatFindAllClassLevelsWorksCorrectly() {
        //given
        List<ClassLevel> classLevels = Arrays.stream(ClassLevel.values()).toList();

        //when
        ClassLevels allClassLevels = findAllClassLevels();

        //then
        Assertions.assertThatCollection(allClassLevels.classLevelList()).isEqualTo(classLevels);

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
        Integer currentPage = 1;

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
        Integer currentPage = 204;
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
    void thatFindFinishedCompetitionsByFiltersWorksCorrectly() {
        //given
        CompetitionWithLocationDto competitionDto
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        Competition competitionToUpdate = competitionDtoMapper.mapFromDtoToDomain(competitionDto);
        CompetitionLocation competitionLocation = competitionLocationService.insertCompetitionLocation(
                competitionToUpdate.competitionLocation());
        Competition competitionUpdated = competitionService.updateCompetitionAfterResults(
                competitionToUpdate.withCompetitionLocation(competitionLocation));
        CompetitionWithLocationDto competitionFinished = competitionDtoMapper.mapFromDomainToDto(competitionUpdated);
        String competitionDateFrom = competitionFinished.competitionBeginning()
                .toLocalDate().minusDays(2L).toString();
        String competitionDateTo = competitionFinished.competitionBeginning()
                .toLocalDate().plusDays(2L).toString();
        String competitionCity = competitionFinished.addressCity();

        Map<String, String> competitionParameters = new HashMap<>();
        competitionParameters.put("competitionDateFrom", competitionDateFrom);
        competitionParameters.put("competitionDateTo", competitionDateTo);
        competitionParameters.put("competitionCity", competitionCity);

        //when
        CompetitionsDto finishedCompetitionsByFilters = findFinishedCompetitionsByFilters(competitionParameters);

        //then
        Assertions.assertThatCollection(finishedCompetitionsByFilters.competitionDtoList()).isNotEmpty();
        Assertions.assertThat(finishedCompetitionsByFilters.competitionDtoList()).contains(competitionFinished);
    }

    @Test
    void thatFindAllFinishedCompetitionsWorksCorrectly() {
        //given
        CompetitionWithLocationDto competitionDto
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        Competition competitionToUpdate = competitionDtoMapper.mapFromDtoToDomain(competitionDto);
        CompetitionLocation competitionLocation = competitionLocationService.insertCompetitionLocation(
                competitionToUpdate.competitionLocation());
        Competition competitionUpdated = competitionService.updateCompetitionAfterResults(
                competitionToUpdate.withCompetitionLocation(competitionLocation));
        CompetitionWithLocationDto competitionFinished = competitionDtoMapper.mapFromDomainToDto(competitionUpdated);

        //when
        CompetitionsDto finishedCompetitions = findAllFinishedCompetitions();

        //then
        Assertions.assertThatCollection(finishedCompetitions.competitionDtoList()).isNotEmpty();
        Assertions.assertThat(finishedCompetitions.competitionDtoList()).contains(competitionFinished);
    }

    @Test
    void thatFindAllTeacherStudentsWorksCorrectly() {
        //given
        String teacher = "nauczyciel1@mejl.com";
        Teacher teacherThatHasStudents = teacherService.findTeacherByEmail(teacher);

        //when
        StudentsDto allTeacherStudents = findAllTeacherStudents(teacher);

        //then
        Assertions.assertThatCollection(allTeacherStudents.StudentDtoList()).isNotEmpty();
        Assertions.assertThat(allTeacherStudents.StudentDtoList().get(0).musicSchool().musicSchoolId())
                .isEqualTo(teacherThatHasStudents.musicSchool().musicSchoolId());
    }
    @Test
    void thatFindAllTeacherStudentsThrowsExceptionIfTeacherWithThatEmailDoesNotExist() {
        //given
        String badTeacherEmail = "bad.teacherEmail@mejl.com";
        String exceptionMessage = "Teacher with email [%s] doesn't exist!".formatted(badTeacherEmail);

        //when
        Response response
                = thatFindAllTeacherStudentsThrowsExceptionIfTeacherDoesNotExist(badTeacherEmail);

        //then
        Assertions.assertThat(response.asPrettyString()).contains(exceptionMessage);
    }

    @Test
    void thatAnnounceStudentToCompetitionWorksCorrectly() {
        //given
        String teacherEmail = "nauczyciel1@mejl.com";
        Teacher teacherHeadmasterThatHasStudents = teacherService.findTeacherByEmail(teacherEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(teacherEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";

        //when
        ApplicationFormDto applicationFormDto = announceStudentToCompetition(
                teacherHeadmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        //then
        Assertions.assertThat(applicationFormDto.competition().competitionId()).isEqualTo(competitionId);
        Assertions.assertThat(applicationFormDto.teacher().teacherId())
                .isEqualTo(teacherHeadmasterThatHasStudents.teacherId());
        Assertions.assertThat(applicationFormDto.student().studentId()).isEqualTo(studentId);
        Assertions.assertThat(applicationFormDto.classLevel().name()).isEqualTo(classLevel);
        Assertions.assertThat(applicationFormDto.performancePieces()).isEqualTo(performancePieces);
    }
    @Test
    void thatUpdateAnnounceStudentToCompetitionWithNewPerformancePiecesWorksCorrectly() {
        //given
        String teacherEmail = "nauczyciel1@mejl.com";
        Teacher teacherHeadmasterThatHasStudents = teacherService.findTeacherByEmail(teacherEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(teacherEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        ApplicationFormDto existingApplicationFormDto = announceStudentToCompetition(
                teacherHeadmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        String newClassLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String newPerformancePieces = "some updated performance pieces";

        //when
        Response response = updateStudentApplicationForm(
                existingApplicationFormDto.applicationFormId(), newClassLevel, newPerformancePieces);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void thatFindTeacherApplicationsToCompetitionWorksCorrectly() {
        //given
        String teacherEmail = "nauczyciel1@mejl.com";
        Teacher teacherThatHasStudents = teacherService.findTeacherByEmail(teacherEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(teacherEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        ApplicationFormDto applicationFormDto = announceStudentToCompetition(
                teacherThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        //when
        ApplicationFormsDto teacherApplicationsToCompetition
                = findTeacherApplicationsToCompetition(teacherThatHasStudents.email(), competitionId);

        //then
        Assertions.assertThatCollection(teacherApplicationsToCompetition.applicationFormDtoList())
                .contains(applicationFormDto);
    }

    @Test
    void thatAnnounceStudentToCompetitionCancelWorksCorrectly() {
        //given
        String teacherEmail = "nauczyciel1@mejl.com";
        Teacher teacherThatHasStudents = teacherService.findTeacherByEmail(teacherEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(teacherEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                teacherThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        //when
        Response response = announceStudentToCompetitionCancel(competitionId, studentId);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void thatAnnounceStudentToCompetitionCancelThrowsExceptionIfStudentDoesNotExists() {
        //given
        String teacherEmail = "nauczyciel1@mejl.com";
        Teacher teacherThatHasStudents = teacherService.findTeacherByEmail(teacherEmail);
        CompetitionWithLocationDto competitionDto
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competitionDto.competitionId();
        StudentDto studentDto1
                = findAllTeacherStudents(teacherEmail).StudentDtoList().stream().findAny().orElseThrow();

        String studentDoesNotExistsId = "badStudentIdNumber";
        String studentId1 = studentDto1.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                teacherThatHasStudents.email(), studentId1, competitionId, classLevel, performancePieces);

        String exceptionError = "Student with id [%s] doesn't exist!".formatted(studentDoesNotExistsId);

        //when
        Response response = announceStudentToCompetitionCancelThrowsExceptionIfStudentIsNotAnnouncedToCompetition(
                competitionId, studentDoesNotExistsId
        );

        //then
        Assertions.assertThat(response.asPrettyString()).contains(exceptionError);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}