package mareczek100.musiccontests.integration;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.api.dto.*;
import mareczek100.musiccontests.api.dto.dto_class_support.CompetitionResultListDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.*;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.HeadmasterDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.StudentDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.TeacherDtoMapper;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.domain.*;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.integration.integration_test_support.HeadmasterRestControllerITSupport;
import mareczek100.musiccontests.integration.integration_test_support.WireMockInstrumentStorageITSupport;
import mareczek100.musiccontests.test_configuration.RestAssuredITConfig;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDtoTestData;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDomainTestData;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDomainTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static mareczek100.musiccontests.api.dto.CompetitionWithLocationDto.DATE_TIME_FORMAT;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HeadmasterIT extends RestAssuredITConfig
        implements HeadmasterRestControllerITSupport, WireMockInstrumentStorageITSupport {

    private final CompetitionLocationService competitionLocationService;
    private final CompetitionService competitionService;
    private final CompetitionDtoMapper competitionDtoMapper;
    private final TeacherService teacherService;
    private final TeacherDtoMapper teacherDtoMapper;
    private final HeadmasterService headmasterService;
    private final HeadmasterDtoMapper headmasterDtoMapper;
    private final MusicSchoolService musicSchoolService;
    private final StudentService studentService;
    private final StudentDtoMapper studentDtoMapper;

    @BeforeEach
    void thatSetUpWorksCorrectly() {
        org.junit.jupiter.api.Assertions.assertNotNull(competitionLocationService);
        org.junit.jupiter.api.Assertions.assertNotNull(competitionService);
        org.junit.jupiter.api.Assertions.assertNotNull(competitionDtoMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(teacherService);
        org.junit.jupiter.api.Assertions.assertNotNull(teacherDtoMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(headmasterService);
        org.junit.jupiter.api.Assertions.assertNotNull(headmasterDtoMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(musicSchoolService);
        org.junit.jupiter.api.Assertions.assertNotNull(studentService);
        org.junit.jupiter.api.Assertions.assertNotNull(studentDtoMapper);
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
    void thatCreateCompetitionAtSchoolWorksCorrectly() {
        //given
        Headmaster headmaster = headmasterService.findAllHeadmasters().stream().findAny().orElseThrow();
        HeadmasterDto headmasterDto = headmasterDtoMapper.mapFromDomainToDto(headmaster);
        CompetitionWithLocationDto competitionToSaveDto
                = CompetitionDtoTestData.competitionAtOrganizerSchoolToSaveDto1()
                .withCompetitionOrganizer(headmasterDto)
                .withCompetitionLocationName(headmasterDto.musicSchool().musicSchoolName())
                .withAddressCountry(headmasterDto.musicSchool().addressCountry())
                .withAddressCity(headmasterDto.musicSchool().addressCity())
                .withAddressPostalCode(headmasterDto.musicSchool().addressPostalCode())
                .withAddressStreet(headmasterDto.musicSchool().addressStreet())
                .withAddressBuildingNumber(headmasterDto.musicSchool().addressBuildingNumber())
                .withAddressAdditionalInfo(headmasterDto.musicSchool().addressAdditionalInfo());

        Map<String, Object> competitionParameters = new HashMap<>();
        competitionParameters.put("headmasterOrganizerEmail", headmaster.email());
        competitionParameters.put("competitionName",
                competitionToSaveDto.competitionName());
        competitionParameters.put("competitionInstrument",
                competitionToSaveDto.competitionInstrument());
        competitionParameters.put("competitionOnline",
                competitionToSaveDto.competitionOnline());
        competitionParameters.put("competitionPrimaryDegree",
                competitionToSaveDto.competitionPrimaryDegree());
        competitionParameters.put("competitionSecondaryDegree",
                competitionToSaveDto.competitionSecondaryDegree());
        competitionParameters.put("competitionBeginning",
                competitionToSaveDto.competitionBeginning().format(
                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        competitionParameters.put("competitionEnd",
                competitionToSaveDto.competitionEnd().format(
                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        competitionParameters.put("competitionResultAnnouncement",
                competitionToSaveDto.competitionResultAnnouncement().format(
                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        competitionParameters.put("competitionApplicationDeadline",
                competitionToSaveDto.competitionApplicationDeadline().format(
                        DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        competitionParameters.put("competitionRequirementsDescription",
                competitionToSaveDto.competitionRequirementsDescription());

        //when
        CompetitionWithLocationDto competitionAtSchool = createCompetitionAtSchool(competitionParameters);

        //then
        Assertions.assertThat(competitionToSaveDto).usingRecursiveComparison()
                .ignoringFields("competitionId").isEqualTo(competitionAtSchool);
    }

    @Test
    void thatCreateCompetitionAtOtherLocationWorksCorrectly() {
        //given
        Headmaster headmaster = headmasterService.findAllHeadmasters().stream().findAny().orElseThrow();
        HeadmasterDto headmasterDto = headmasterDtoMapper.mapFromDomainToDto(headmaster);
        String organizerEmail = headmaster.email();
        CompetitionWithLocationDto competitionToSaveDto
                = CompetitionDtoTestData.competitionAtOtherLocationToSaveDto1()
                .withCompetitionOrganizer(headmasterDto);

        //when
        CompetitionWithLocationDto competitionAtOtherLocation
                = createCompetitionAtOtherLocation(competitionToSaveDto, organizerEmail);

        //then
        Assertions.assertThat(competitionToSaveDto).usingRecursiveComparison()
                .ignoringFields("competitionId").isEqualTo(competitionAtOtherLocation);
    }
    @Test
    void thatUpdateExistingCompetitionToNewOneWorksCorrectly() {
        //given
        CompetitionWithLocationDto existingCompetition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        Headmaster headmaster = headmasterService.findAllHeadmasters().stream().findAny().orElseThrow();
        String organizerEmail = headmaster.email();
        CompetitionWithLocationRestDto newCompetitionToSaveDto = CompetitionDtoTestData.competitionToUpdateDto();

        CompetitionWithLocationDto competitionToUpdateDto = CompetitionWithLocationDto.builder()
                .competitionId(existingCompetition.competitionId())
                .competitionName(newCompetitionToSaveDto.competitionName())
                .competitionInstrument(newCompetitionToSaveDto.competitionInstrument())
                .competitionOnline(newCompetitionToSaveDto.competitionOnline())
                .competitionPrimaryDegree(newCompetitionToSaveDto.competitionPrimaryDegree())
                .competitionSecondaryDegree(newCompetitionToSaveDto.competitionSecondaryDegree())
                .competitionBeginning(newCompetitionToSaveDto.competitionBeginning())
                .competitionEnd(newCompetitionToSaveDto.competitionEnd())
                .competitionResultAnnouncement(newCompetitionToSaveDto.competitionResultAnnouncement())
                .competitionApplicationDeadline(newCompetitionToSaveDto.competitionApplicationDeadline())
                .competitionRequirementsDescription(newCompetitionToSaveDto.competitionRequirementsDescription())
                .competitionLocationName(newCompetitionToSaveDto.competitionLocationName())
                .addressCountry(newCompetitionToSaveDto.addressCountry())
                .addressCity(newCompetitionToSaveDto.addressCity())
                .addressPostalCode(newCompetitionToSaveDto.addressPostalCode())
                .addressStreet(newCompetitionToSaveDto.addressStreet())
                .addressBuildingNumber(newCompetitionToSaveDto.addressBuildingNumber())
                .addressAdditionalInfo(newCompetitionToSaveDto.addressAdditionalInfo())
                .build();

        //when
        CompetitionWithLocationDto updatedCompetitionWithNewLocation
                = createCompetitionAtOtherLocation(competitionToUpdateDto, organizerEmail);

        //then
        Assertions.assertThat(newCompetitionToSaveDto).usingRecursiveComparison()
                .ignoringFields("competitionId").isEqualTo(updatedCompetitionWithNewLocation);
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
        Integer currentPage = 20;
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
    void thatCreateHeadmasterTeacherRightsWorksCorrectly() {
        //given
        Headmaster headmaster = headmasterService.findAllHeadmasters().stream().findAny().orElseThrow();
        String headmasterEmail = headmaster.email();
        stubForAllInstrumentList(wireMockServer);
        InstrumentDto instrumentDto
                = findAllAvailableInstruments().instrumentDtoList().stream().findAny().orElseThrow();

        //then
        TeacherDto headmasterTeacherRightsCorrectly
                = createHeadmasterTeacherRightsCorrectly(headmasterEmail, instrumentDto.name());
        Teacher teacherByEmail = teacherService.findTeacherByEmail(headmasterEmail);
        TeacherDto teacherDtoByHeadmasterEmail = teacherDtoMapper.mapFromDomainToDto(teacherByEmail);
        TeachersDto allTeachers = findAllTeachers(headmasterEmail);

        //then
        Assertions.assertThatCollection(allTeachers.TeacherDtoList()).contains(headmasterTeacherRightsCorrectly);
        Assertions.assertThat(teacherDtoByHeadmasterEmail).isEqualTo(headmasterTeacherRightsCorrectly);
        Assertions.assertThat(teacherByEmail).usingRecursiveComparison()
                .ignoringFields("teacherId", "headmasterId", "instrument", "applicationForms", "students")
                .isEqualTo(headmaster);
    }

    @Test
    void thatCreateHeadmasterTeacherRightsAccountAlreadyExistsResponseProblemDetailWorksCorrectly() {
        //given
        String headmasterTeacherEmail = "dyrektor3@mejl.com";
        Teacher teacher = teacherService.findTeacherByEmail(headmasterTeacherEmail);
        String teacherEmail = teacher.email();
        String problemDetailMessage = "Teacher with email [%s] already exist!".formatted(teacherEmail);
        stubForAllInstrumentList(wireMockServer);
        InstrumentDto instrumentDto
                = findAllAvailableInstruments().instrumentDtoList().stream().findAny().orElseThrow();

        //then
        Response responseProblemDetail = createHeadmasterTeacherRightsAccountAlreadyExistsResponseProblemDetail(
                teacherEmail, instrumentDto.name());

        //then
        Assertions.assertThat(responseProblemDetail.asString()).contains(problemDetailMessage);

    }

    @Test
    void thatFindAllTeachersWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasTeachers = headmasterService.findHeadmasterByEmail(headmasterEmail);

        //when
        TeachersDto allTeachers = findAllTeachers(headmasterEmail);

        //then
        Assertions.assertThatCollection(allTeachers.TeacherDtoList()).isNotEmpty();
        Assertions.assertThat(allTeachers.TeacherDtoList().get(0).musicSchool().musicSchoolId())
                .isEqualTo(headmasterThatHasTeachers.musicSchool().musicSchoolId());
    }

    @Test
    void thatFindAllTeachersResponseProblemDetailIfHasNoTeachersWorksCorrectly() {
        //given
        MusicSchool musicSchoolToSave1 = MusicSchoolDomainTestData.musicSchoolToSave1();
        MusicSchool insertedMusicSchool = musicSchoolService.insertMusicSchool(musicSchoolToSave1);
        Headmaster headmaster = HeadmasterDomainTestData.headmasterToSave1();
        Headmaster insertedHeadmasterWithNewMusicSchool = headmasterService.insertHeadmaster(
                headmaster.withMusicSchool(insertedMusicSchool));
        String headmasterEmail = insertedHeadmasterWithNewMusicSchool.email();
        String problemDetailMessage = "Your school [%s] has no teachers at all!"
                .formatted(insertedHeadmasterWithNewMusicSchool.musicSchool().name());

        //when
        Response responseMessageIfNoTeachers = findAllTeachersResponseMessageIfNoTeachers(headmasterEmail);

        //then
        Assertions.assertThat(responseMessageIfNoTeachers.asString()).contains(problemDetailMessage);
    }

    @Test
    void thatFindAllTeacherStudentsWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);

        //when
        StudentsDto allHeadmasterTeacherStudents = findAllTeacherStudents(headmasterEmail);

        //then
        Assertions.assertThatCollection(allHeadmasterTeacherStudents.StudentDtoList()).isNotEmpty();
        Assertions.assertThat(allHeadmasterTeacherStudents.StudentDtoList().get(0).musicSchool().musicSchoolId())
                .isEqualTo(headmasterThatHasStudents.musicSchool().musicSchoolId());
    }

    @Test
    void thatFindAllTeacherStudentsResponseProblemDetailIfHasNoTeachersWorksCorrectly() {
        //given
        MusicSchool musicSchoolToSave1 = MusicSchoolDomainTestData.musicSchoolToSave1();
        MusicSchool insertedMusicSchool = musicSchoolService.insertMusicSchool(musicSchoolToSave1);
        Headmaster headmaster = HeadmasterDomainTestData.headmasterToSave1();
        Headmaster insertedHeadmasterWithNewMusicSchool = headmasterService.insertHeadmaster(
                headmaster.withMusicSchool(insertedMusicSchool));
        String headmasterEmail = insertedHeadmasterWithNewMusicSchool.email();
        stubForAllInstrumentList(wireMockServer);
        InstrumentDto instrumentDto
                = findAllAvailableInstruments().instrumentDtoList().stream().findAny().orElseThrow();
        TeacherDto insertedHeadmasterTeacherWithNewMusicSchool
                = createHeadmasterTeacherRightsCorrectly(headmasterEmail, instrumentDto.name());
        String headmasterTeacherEmail = insertedHeadmasterTeacherWithNewMusicSchool.email();
        String problemDetailMessage = "You have no students at all!";

        //when
        Response responseMessageIfNoStudents
                = findAllTeacherStudentsResponseMessageIfNoStudents(headmasterTeacherEmail);

        //then
        Assertions.assertThat(responseMessageIfNoStudents.asString()).contains(problemDetailMessage);
    }

    @Test
    void thatFindAllCompetitionStudentsWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Teacher teacherHeadmasterThatHasStudents = teacherService.findTeacherByEmail(headmasterEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                teacherHeadmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        //when
        StudentsDto allCompetitionStudents = findAllCompetitionStudents(competitionId);

        //then
        Assertions.assertThatCollection(allCompetitionStudents.StudentDtoList()).contains(studentDto);
    }

    @Test
    void thatFindAllCompetitionStudentsEmptyListResponseNotFoundStatusWorksCorrectly() {
        //given
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();

        //when
        Response responseNotFoundStatus = findAllCompetitionStudentsEmptyListResponseNotFoundStatus(competitionId);

        //then
        Assertions.assertThat(responseNotFoundStatus.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void thatFindAllSchoolStudentsWorksCorrectly() {
        //given
        Headmaster headmaster = headmasterService.findAllHeadmasters().stream().findAny().orElseThrow();
        String headmasterEmail = headmaster.email();

        //when
        StudentsDto allSchoolStudents = findAllSchoolStudents(headmasterEmail);

        //then
        Assertions.assertThatCollection(allSchoolStudents.StudentDtoList()).isNotEmpty();
        Assertions.assertThat(allSchoolStudents.StudentDtoList().get(0).musicSchool().musicSchoolId())
                .isEqualTo(headmaster.musicSchool().musicSchoolId());
    }

    @Test
    void thatAnnounceStudentToCompetitionWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        Teacher teacherHeadmasterThatHasStudents = teacherService.findTeacherByEmail(headmasterEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";

        //when
        ApplicationFormDto applicationFormDto = announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        //then
        Assertions.assertThat(applicationFormDto.competition().competitionId()).isEqualTo(competitionId);
        Assertions.assertThat(applicationFormDto.teacher().teacherId())
                .isEqualTo(teacherHeadmasterThatHasStudents.teacherId());
        Assertions.assertThat(applicationFormDto.student().studentId()).isEqualTo(studentId);
        Assertions.assertThat(applicationFormDto.classLevel().name()).isEqualTo(classLevel);
        Assertions.assertThat(applicationFormDto.performancePieces()).isEqualTo(performancePieces);
    }

    @Test
    void thatAnnounceStudentToCompetitionResponseMessageIfItIsAfterDeadline() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        HeadmasterDto headmasterDto = headmasterDtoMapper.mapFromDomainToDto(headmasterThatHasStudents);
        LocalDateTime localDateTimeAfterDeadlineToAnnounceStudentToCompetition
                = LocalDateTime.now().minusDays(1L);
        CompetitionWithLocationDto competitionToSaveDto
                = CompetitionDtoTestData.competitionAtOtherLocationToSaveDto1()
                .withCompetitionOrganizer(headmasterDto)
                .withCompetitionApplicationDeadline(
                        localDateTimeAfterDeadlineToAnnounceStudentToCompetition);
        CompetitionWithLocationDto competition
                = createCompetitionAtOtherLocation(competitionToSaveDto, headmasterEmail);
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        String problemDetailMessage = "Too late! It is after announce deadline, " +
                "You can't no longer put Your student to competition [%s].".formatted(competition.competitionName());

        //when
        Response response = announceStudentToCompetitionResponseMessageIfItIsAfterDeadline(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.REQUEST_TIMEOUT.value());
        Assertions.assertThat(response.asString()).contains(problemDetailMessage);
    }

    @Test
    void thatFindTeacherApplicationsToCompetitionWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Teacher teacherHeadmasterThatHasStudents = teacherService.findTeacherByEmail(headmasterEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        ApplicationFormDto applicationFormDto = announceStudentToCompetition(
                teacherHeadmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        //when
        ApplicationFormsDto teacherApplicationsToCompetition
                = findTeacherApplicationsToCompetition(teacherHeadmasterThatHasStudents.email(), competitionId);

        //then
        Assertions.assertThatCollection(teacherApplicationsToCompetition.applicationFormDtoList())
                .contains(applicationFormDto);
    }

    @Test
    void thatFindAllTeachersApplicationsToCompetitionWorksCorrectly() {
        //given
        String headmasterFromSameSchoolAsTeacherEmail = "dyrektor1@mejl.com";
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
                = findAllTeachersApplicationsToCompetition(headmasterFromSameSchoolAsTeacherEmail, competitionId);

        //then
        Assertions.assertThatCollection(teacherApplicationsToCompetition.applicationFormDtoList()).isNotEmpty();
        Assertions.assertThatCollection(teacherApplicationsToCompetition.applicationFormDtoList())
                .contains(applicationFormDto);
    }

    @Test
    void thatUpdateAnnounceStudentToCompetitionWithNewPerformancePiecesWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        ApplicationFormDto existingApplicationFormDto = announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        String newClassLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String newPerformancePieces = "some updated performance pieces";

        //when
        Response response = updateStudentApplicationForm(
                existingApplicationFormDto.applicationFormId(), newClassLevel, newPerformancePieces);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void thatAnnounceStudentToCompetitionCancelWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        CompetitionWithLocationDto competition
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competition.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        //when
        Response response = announceStudentToCompetitionCancel(competitionId, studentId);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void thatAnnounceStudentToCompetitionCancelThrowsExceptionIfStudentIsNotAnnouncedToCompetition() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        CompetitionWithLocationDto competitionDto
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competitionDto.competitionId();
        StudentDto studentDto1
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();

        Student studentDoesNotAnnouncedToCompetition = studentService.findStudentByEmail("uczeń1@mejl.com");
        StudentDto studentDto2 = studentDtoMapper.mapFromDomainToDto(studentDoesNotAnnouncedToCompetition);
        String studentId1 = studentDto1.studentId();
        String studentId2 = studentDto2.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId1, competitionId, classLevel, performancePieces);

        String exceptionMessage = "Sorry, student [%s] [%s] isn't announced to competition [%s]!"
                .formatted(studentDto2.name(), studentDto2.surname(), competitionDto.competitionName());

        //when
        Response response = announceStudentToCompetitionCancelThrowsExceptionIfStudentIsNotAnnouncedToCompetition(
                competitionId, studentId2
        );

        //then
        Assertions.assertThat(response.asString()).contains(exceptionMessage);
    }

    @Test
    void announceStudentToCompetitionCancelResponseMessageIfItIsTooLateToCancel() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        HeadmasterDto headmasterDto = headmasterDtoMapper.mapFromDomainToDto(headmasterThatHasStudents);

        LocalDateTime localDateTimeTooLateToCancelAnnounceStudent = LocalDateTime.now();
        CompetitionWithLocationDto competitionToSaveDto
                = CompetitionDtoTestData.competitionAtOtherLocationToSaveDto1()
                .withCompetitionOrganizer(headmasterDto)
                .withCompetitionBeginning(localDateTimeTooLateToCancelAnnounceStudent);
        CompetitionWithLocationDto competitionAtOtherLocation
                = createCompetitionAtOtherLocation(competitionToSaveDto, headmasterEmail);

        String competitionId = competitionAtOtherLocation.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        String problemDetailMessage = "Too late! It is less than 2 hours before competition starts";

        //when
        Response response
                = announceStudentToCompetitionCancelResponseMessageIfItIsTooLateToCancel(
                competitionId, studentId
        );

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.REQUEST_TIMEOUT.value());
        Assertions.assertThat(response.asString()).contains(problemDetailMessage);
    }

    @Test
    void thatFindCompetitionsCreatedByHeadmasterWorksCorrectly() {
        //given
        Headmaster headmaster = headmasterService.findAllHeadmasters().stream().findAny().orElseThrow();

        //when
        CompetitionsDto competitionsCreatedByHeadmaster = findCompetitionsCreatedByHeadmaster(headmaster.email());

        //then
        Assertions.assertThat(competitionsCreatedByHeadmaster.competitionDtoList()).isNotEmpty();
        Assertions.assertThat(
                competitionsCreatedByHeadmaster.competitionDtoList().get(0).competitionOrganizer().headmasterId())
                .isEqualTo(headmaster.headmasterId());
    }
    @Test
    void thatFindCompetitionsCreatedByHeadmasterResponseMessageIfHeadmasterDidNotCreatedAnyCompetitions() {
        //given
        String headmasterFakeEmail = "headmaster@headmaster.com";

        //when
        Response response
                = findCompetitionsCreatedByHeadmasterResponseMessageIfHeadmasterDidNotCreatedAnyCompetitions(
                        headmasterFakeEmail
        );

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void thatAnnounceCompetitionResultsWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        CompetitionWithLocationDto competitionDto
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competitionDto.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        String competitionPlace = "test place";
        String specialAward = "test special award";
        CompetitionResultListDto competitionResultListDto = CompetitionResultDtoTestData.competitionResultsDtoToSave();
        competitionResultListDto.setCompetitionResultsSupport(
                List.of(CompetitionResultListDto.CompetitionResultSupport.builder()
                        .studentId(studentId)
                        .competitionPlace(competitionPlace)
                        .specialAward(specialAward)
                        .build()));

        //when
        CompetitionResultsDto competitionResultsDto = announceCompetitionResults(competitionId, competitionResultListDto);

        //then
        Assertions.assertThatCollection(competitionResultsDto.competitionResultDtoList()).isNotEmpty();
        Assertions.assertThat(competitionResultsDto.competitionResultDtoList().get(0).competition())
                .usingRecursiveComparison().ignoringFields("competitionFinished")
                .isEqualTo(competitionDto);
        Assertions.assertThat(competitionResultsDto.competitionResultDtoList().get(0).student())
                .isEqualTo(studentDto);
        Assertions.assertThat(competitionResultsDto.competitionResultDtoList().get(0).competitionPlace())
                .isEqualTo(competitionPlace);
        Assertions.assertThat(competitionResultsDto.competitionResultDtoList().get(0).specialAward())
                .isEqualTo(specialAward);
    }
    @Test
    void thatAnnounceCompetitionResultsResponseMessageIfResultsAreEmpty() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        CompetitionWithLocationDto competitionDto
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competitionDto.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        CompetitionResultListDto competitionResultListDto = CompetitionResultDtoTestData.competitionResultsDtoToSave();
        competitionResultListDto.setCompetitionResultsSupport(Collections.emptyList());

        //when
        Response response
                = announceCompetitionResultsResponseMessageIfResultsAreEmpty(competitionId, competitionResultListDto);

        //then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void thatCheckTeacherStudentsResultsWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        CompetitionWithLocationDto competitionDto
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competitionDto.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        String competitionPlace = "test place";
        String specialAward = "test special award";
        CompetitionResultListDto competitionResultListDto = CompetitionResultDtoTestData.competitionResultsDtoToSave();
        competitionResultListDto.setCompetitionResultsSupport(
                List.of(CompetitionResultListDto.CompetitionResultSupport.builder()
                        .studentId(studentId)
                        .competitionPlace(competitionPlace)
                        .specialAward(specialAward)
                        .build()));

        CompetitionResultsDto competitionResultsDto = announceCompetitionResults(competitionId, competitionResultListDto);

        //when
        CompetitionResultsDto resultsDto = checkTeacherStudentsResults(competitionId, headmasterEmail);

        //then
        Assertions.assertThatCollection(competitionResultsDto.competitionResultDtoList())
                .isEqualTo(resultsDto.competitionResultDtoList());

    }

    @Test
    void thatCheckAllCompetitionResultsWorksCorrectly() {
        //given
        String headmasterEmail = "dyrektor3@mejl.com";
        Headmaster headmasterThatHasStudents = headmasterService.findHeadmasterByEmail(headmasterEmail);
        CompetitionWithLocationDto competitionDto
                = findAllAvailableCompetitions().competitionDtoList().stream().findAny().orElseThrow();
        String competitionId = competitionDto.competitionId();
        StudentDto studentDto
                = findAllTeacherStudents(headmasterEmail).StudentDtoList().stream().findAny().orElseThrow();
        String studentId = studentDto.studentId();
        String classLevel = findAllClassLevels().classLevelList().stream().findAny().orElseThrow().name();
        String performancePieces = "some test performance pieces";
        announceStudentToCompetition(
                headmasterThatHasStudents.email(), studentId, competitionId, classLevel, performancePieces);

        String competitionPlace = "test place";
        String specialAward = "test special award";
        CompetitionResultListDto competitionResultListDto = CompetitionResultDtoTestData.competitionResultsDtoToSave();
        competitionResultListDto.setCompetitionResultsSupport(
                List.of(CompetitionResultListDto.CompetitionResultSupport.builder()
                        .studentId(studentId)
                        .competitionPlace(competitionPlace)
                        .specialAward(specialAward)
                        .build()));

        CompetitionResultsDto resultsDto
                = announceCompetitionResults(competitionId, competitionResultListDto);

        //when
        CompetitionResultsDto competitionResultsDto = checkAllCompetitionResults(competitionId);

        //then
        Assertions.assertThatCollection(competitionResultsDto.competitionResultDtoList())
                .containsExactly(resultsDto.competitionResultDtoList().get(0));

    }
}