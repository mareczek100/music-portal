package mareczek100.musiccontests.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.*;
import mareczek100.musiccontests.api.dto.dto_class_support.CompetitionResultListDto;
import mareczek100.musiccontests.api.dto.mapper.*;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService;
import mareczek100.musiccontests.domain.*;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

import static mareczek100.musiccontests.api.controller.HeadmasterController.HEADMASTER_MAIN_PAGE;

@Validated
@Controller
@AllArgsConstructor
@RequestMapping(HEADMASTER_MAIN_PAGE)
public class HeadmasterController {

    public static final String HEADMASTER_MAIN_PAGE = "/headmaster";
    public static final String HEADMASTER_COMPETITION_CREATE = "/competition/create";
    public static final String HEADMASTER_COMPETITION_CREATE_LOCATION = "/competition/create/location";
    public static final String HEADMASTER_COMPETITION_ANNOUNCE_RESULT = "/competition/result/announce";
    public static final String HEADMASTER_COMPETITION_RESULT = "/competition/result";
    public static final String HEADMASTER_COMPETITION_STUDENT = "/competition/student";
    public static final String HEADMASTER_COMPETITION_TEACHER = "/competition/teacher";
    public static final String HEADMASTER_COMPETITION_TEACHER_RIGHTS = "/competition/teacher/rights";
    public static final String HEADMASTER_COMPETITION_SELECT_STUDENT = "/competition/student/select";
    public static final String HEADMASTER_COMPETITION_STUDENT_PUT = "/competition/student/put";
    public static final String HEADMASTER_COMPETITION_STUDENT_CANCEL = "/competition/student/cancel";
    public static final String HEADMASTER_COMPETITION_STUDENT_CANCEL_SELECT = "/competition/student/cancel/select";
    public static final String HEADMASTER_COMPETITION_STUDENT_CANCEL_CONFIRM = "/competition/student/cancel/confirm";
    public static final String HEADMASTER_COMPETITION_FILTERS_SEARCH = "/competition/student/search/filters";
    public static final String HEADMASTER_COMPETITION_INSTRUMENT_SEARCH = "/competition/student/search/instrument";
    public static final String HEADMASTER_COMPETITION_ALL_SEARCH = "/competition/student/search/all/{currentPage}";
    public static final String HEADMASTER_COMPETITION_CHECK = "/competition/check";
    public static final String HEADMASTER_COMPETITION_CHECK_RESULT = "/competition/check/result";

    private final InstrumentApiService instrumentApiService;
    private final ApplicationFormService applicationFormService;
    private final ApplicationFormDtoMapper applicationFormDtoMapper;
    private final CompetitionService competitionService;
    private final CompetitionResultService competitionResultService;
    private final HeadmasterService headmasterService;
    private final MusicSchoolService musicSchoolService;
    private final MusicSchoolDtoMapper musicSchoolDtoMapper;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final StudentDtoMapper studentDtoMapper;
    private final TeacherDtoMapper teacherDtoMapper;
    private final HeadmasterDtoMapper headmasterDtoMapper;
    private final InstrumentDtoMapper instrumentDtoMapper;
    private final CompetitionDtoMapper competitionDtoMapper;
    private final CompetitionResultDtoMapper competitionResultDtoMapper;

    @GetMapping
    public String headmasterHomePage() {

        return "headmaster/headmaster";
    }

    @GetMapping(HEADMASTER_COMPETITION_CREATE)
    public String headmasterCreateCompetition(Model model) {
        List<InstrumentDto> instrumentDTOs = instrumentApiService.findAllInstruments().stream()
                .map(instrumentDtoMapper::mapFromDomainToDto)
                .toList();
        CompetitionWithLocationDto competitionDto = CompetitionWithLocationDto.builder().build();

        model.addAttribute("competitionDto", competitionDto);
        model.addAttribute("instrumentDTOs", instrumentDTOs);

        return "headmaster/headmaster_competition_create";
    }

    @PostMapping(HEADMASTER_COMPETITION_CREATE)
    public String headmasterCreateCompetitionProcess(
            @ModelAttribute("competitionDto") CompetitionWithLocationDto competitionDto,
            @RequestParam("competitionOrganizerEmail") String competitionOrganizerEmail,
            @RequestParam("competitionSchoolLocation") Boolean competitionSchoolLocation,
            Model model
    )
    {
        competitionOrganizerEmail = competitionOrganizerEmail.strip();
        model.addAllAttributes(Map.of(
                "competitionDto", competitionDto,
                "competitionOrganizerEmail", competitionOrganizerEmail
        ));

        if (competitionSchoolLocation) {
            CompetitionWithLocationDto createdCompetitionDto = createCompetition(competitionDto, competitionOrganizerEmail);
            model.addAttribute("createdCompetitionDto", createdCompetitionDto);
            return "headmaster/headmaster_competition_create_done";
        }

        return "headmaster/headmaster_competition_create_location";
    }


    @PostMapping(HEADMASTER_COMPETITION_CREATE_LOCATION)
    public String headmasterCreateCompetitionInOtherLocation(
            @Valid @ModelAttribute("competitionDto") CompetitionWithLocationDto competitionDto,
            @RequestParam("competitionOrganizerEmail") String competitionOrganizerEmail,
            Model model
    )
    {
        CompetitionWithLocationDto createdCompetitionDto = createCompetition(competitionDto, competitionOrganizerEmail);
        model.addAttribute("createdCompetitionDto", createdCompetitionDto);
        return "headmaster/headmaster_competition_create_done";
    }

    @GetMapping(HEADMASTER_COMPETITION_STUDENT)
    public String headmasterStudentAnnounceOrCancelHomePage() {

        return "headmaster/headmaster_competition_student";
    }


    @GetMapping(HEADMASTER_COMPETITION_TEACHER)
    public String headmasterCreateTeacherRightsHomePage(
            @RequestParam("headmasterTeacherEmail") @Email String headmasterTeacherEmail,
            Model model
    )
    {
        List<InstrumentDto> instrumentDTOs = instrumentApiService.findAllInstruments().stream()
                .map(instrumentDtoMapper::mapFromDomainToDto)
                .toList();
        Headmaster headmaster = headmasterService.findHeadmasterByEmail(headmasterTeacherEmail);
        HeadmasterDto headmasterDto = headmasterDtoMapper.mapFromDomainToDto(headmaster);
        TeacherDto teacherDto = TeacherDto.builder().build();

        model.addAttribute("headmasterDto", headmasterDto);
        model.addAttribute("instrumentDTOs", instrumentDTOs);
        model.addAttribute("headmasterTeacherDto", teacherDto);

        return "headmaster/headmaster_competition_teacher";
    }

    @PostMapping(HEADMASTER_COMPETITION_TEACHER_RIGHTS)
    public String headmasterCreateTeacherRightsProcess(
            @ModelAttribute("headmasterTeacherDto") TeacherDto headmasterTeacherDto,
            @RequestParam("musicSchoolId") String musicSchoolId,
            Model model
    )
    {
        MusicSchool musicSchool = musicSchoolService.findMusicSchoolById(musicSchoolId);
        MusicSchoolWithAddressDto musicSchoolDto = musicSchoolDtoMapper.mapFromDomainToDto(musicSchool);
        Teacher headmasterTeacherToSave = teacherDtoMapper.mapFromDtoToDomain(
                headmasterTeacherDto.withMusicSchool(musicSchoolDto));

        Optional<Teacher> anyTeacher = teacherService.findAllTeachers().stream()
                .filter(teacher -> headmasterTeacherToSave.pesel().equals(teacher.pesel()))
                .findAny();

        if (anyTeacher.isPresent()) {
            model.addAttribute("teacherExist", true);
            return "headmaster/headmaster_competition_teacher";
        }

        Teacher insertedHeadmasterTeacher = teacherService.insertTeacher(headmasterTeacherToSave);
        TeacherDto teacherDto = teacherDtoMapper.mapFromDomainToDto(insertedHeadmasterTeacher);
        model.addAttribute("teacherCreated", true);
        model.addAttribute("headmasterTeacherDto", teacherDto);

        return "headmaster/headmaster_competition_teacher";
    }

    @GetMapping(HEADMASTER_COMPETITION_INSTRUMENT_SEARCH)
    public String headmasterSearchCompetitionsByInstrumentToPutStudent(
            @RequestParam("headmasterTeacherEmail") String headmasterTeacherEmail,
            Model model
    )
    {
        teacherService.findTeacherByEmail(headmasterTeacherEmail);
        List<InstrumentDto> instrumentDTOs = instrumentApiService.findAllInstruments().stream()
                .map(instrumentDtoMapper::mapFromDomainToDto)
                .toList();

        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(competition -> competition.finished().equals(false))
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        CompetitionWithLocationDto competitionDto = CompetitionWithLocationDto.builder().build();

        model.addAttribute("cityDTOs", cityDTOs);
        model.addAttribute("headmasterTeacherEmail", headmasterTeacherEmail);
        model.addAttribute("instrumentDTOs", instrumentDTOs);
        model.addAttribute("competitionDto", competitionDto);

        return "headmaster/headmaster_competition_search_instrument";
    }

    @GetMapping(HEADMASTER_COMPETITION_ALL_SEARCH)
    public String headmasterSearchAllAvailableCompetitionsPageable(
            @RequestParam("headmasterTeacherEmail") String headmasterTeacherEmail,
            @PathVariable("currentPage") Integer currentPage,
            Model model
    )
    {
        Page<Competition> competitionsPage = competitionService.findAllCompetitionsPageable(currentPage);
        int allPages = competitionsPage.getTotalPages();
        long allCompetitions = competitionsPage.getTotalElements();
        List<Competition> competitions = competitionsPage.getContent();
        List<CompetitionWithLocationDto> competitionDTOs = competitions.stream()
                .filter(competition -> !competition.finished())
                .filter(competition -> OffsetDateTime.now().isBefore(competition.applicationDeadline()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        Teacher headmasterTeacher = teacherService.findTeacherByEmail(headmasterTeacherEmail);
        String teacherId = headmasterTeacher.teacherId();

        List<Student> studentList = studentService.findAllStudents().stream()
                .filter(student -> teacherId.equals(student.teacher().teacherId()))
                .toList();

        List<StudentDto> studentDTOs = studentList.stream()
                .map(studentDtoMapper::mapFromDomainToDto)
                .toList();

        List<ClassLevel> classLevels
                = Arrays.stream(ClassLevel.values())
                .toList();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("allPages", allPages);
        model.addAttribute("allCompetitions", allCompetitions);
        model.addAttribute("studentDTOs", studentDTOs);
        model.addAttribute("classLevels", classLevels);
        model.addAttribute("competitionDTOs", competitionDTOs);
        model.addAttribute("headmasterTeacherEmail", headmasterTeacherEmail);

        return "headmaster/headmaster_competition_search_all";
    }

    @GetMapping(HEADMASTER_COMPETITION_FILTERS_SEARCH)
    public String headmasterSearchCompetitionsByFiltersToPutStudent(
            @RequestParam("headmasterTeacherEmail") String headmasterTeacherEmail,
            Model model
    )
    {
        List<InstrumentDto> instrumentDTOs = instrumentApiService.findAllInstruments().stream()
                .map(instrumentDtoMapper::mapFromDomainToDto)
                .toList();

        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(competition -> competition.finished().equals(false))
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        CompetitionWithLocationDto competitionDto = CompetitionWithLocationDto.builder().build();

        model.addAttribute("headmasterTeacherEmail", headmasterTeacherEmail);
        model.addAttribute("instrumentDTOs", instrumentDTOs);
        model.addAttribute("competitionDto", competitionDto);
        model.addAttribute("cityDTOs", cityDTOs);

        return "headmaster/headmaster_competition_search_filters";
    }

    @GetMapping(HEADMASTER_COMPETITION_SELECT_STUDENT)
    public String headmasterSelectStudentToCompetition(
            @RequestParam("headmasterTeacherEmail") String headmasterTeacherEmail,
            @ModelAttribute("competitionDto") CompetitionWithLocationDto competitionDto,
            Model model
    )
    {
        Teacher headmasterTeacher = teacherService.findTeacherByEmail(headmasterTeacherEmail);
        String teacherId = headmasterTeacher.teacherId();

        List<Student> studentList = studentService.findAllStudents().stream()
                .filter(student ->
                        teacherId.equals(student.teacher().teacherId()))
                .toList();
        List<StudentDto> studentDTOs = studentList.stream()
                .map(studentDtoMapper::mapFromDomainToDto)
                .toList();

        List<Competition> foundCompetitions;

        if (Objects.isNull(competitionDto.competitionOnline())
                && Objects.isNull(competitionDto.competitionPrimaryDegree())
                && Objects.isNull(competitionDto.competitionSecondaryDegree())
                && Objects.isNull(competitionDto.addressCity())) {
            foundCompetitions = competitionService.findCompetitionsByInstrument(
                    competitionDto.competitionInstrument());
        } else {
            foundCompetitions = competitionService.findCompetitionsByFilters(
                    competitionDto.competitionInstrument(),
                    competitionDto.competitionOnline(),
                    competitionDto.competitionPrimaryDegree(),
                    competitionDto.competitionSecondaryDegree(),
                    competitionDto.addressCity());
        }

        List<CompetitionWithLocationDto> competitionDTOs = foundCompetitions.stream()
                .filter(competition -> !competition.finished())
                .filter(competition -> OffsetDateTime.now().isBefore(competition.applicationDeadline()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        List<ClassLevel> classLevels
                = Arrays.stream(ClassLevel.values())
                .toList();

        model.addAttribute("competitionDTOs", competitionDTOs);
        model.addAttribute("studentDTOs", studentDTOs);
        model.addAttribute("headmasterTeacherEmail", headmasterTeacherEmail);
        model.addAttribute("classLevels", classLevels);

        return "headmaster/headmaster_competition_select_student";
    }

    @PostMapping(HEADMASTER_COMPETITION_STUDENT_PUT)
    public String headmasterPutUpStudentToCompetition(
            @RequestParam("headmasterTeacherEmail") String headmasterTeacherEmail,
            @RequestParam("studentId") String studentId,
            @RequestParam("competitionId") String competitionId,
            @RequestParam("classLevel") String classLevel,
            @RequestParam("performancePieces") String performancePieces,
            Model model
    )
    {
        Competition competition = competitionService.findCompetitionById(competitionId);
        Teacher teacher = teacherService.findTeacherByEmail(headmasterTeacherEmail);
        Student student = studentService.findStudentById(studentId);

        boolean studentAlreadyInCompetitionAnyMatch = applicationFormService.findAllApplicationForms().stream()
                .anyMatch(applicationForm ->
                        studentId.equals(applicationForm.student().studentId())
                                && competitionId.equals(applicationForm.competition().competitionId()));
        if (studentAlreadyInCompetitionAnyMatch) {
            throw new RuntimeException("Student [%s] [%s] is already announced to competition [%s]!"
                    .formatted(student.name(), student.surname(), competition.name()));
        }

        ApplicationFormDto applicationFormDto
                = createStudentApplicationForm(competition, teacher, student, classLevel, performancePieces);

        model.addAttribute("applicationFormDto", applicationFormDto);

        return "headmaster/headmaster_competition_put_student";
    }

    @GetMapping(HEADMASTER_COMPETITION_STUDENT_CANCEL)
    public String headmasterCancelStudentApplicationForm(
            Model model,
            @RequestParam("headmasterTeacherEmail") @Email String headmasterTeacherEmail
    )
    {
        teacherService.findTeacherByEmail(headmasterTeacherEmail);
        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(competition -> !competition.finished())
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        model.addAttribute("headmasterTeacherEmail", headmasterTeacherEmail);
        model.addAttribute("cityDTOs", cityDTOs);

        return "headmaster/headmaster_competition_student_cancel";
    }

    @GetMapping(HEADMASTER_COMPETITION_STUDENT_CANCEL_SELECT)
    public String headmasterCancelStudentApplicationSelectCompetition(
            @RequestParam("competitionDateFrom") @DateTimeFormat LocalDate competitionDateFrom,
            @RequestParam("competitionDateTo") @DateTimeFormat LocalDate competitionDateTo,
            @RequestParam("headmasterTeacherEmail") String headmasterTeacherEmail,
            @RequestParam("competitionCity") String competitionCity,
            Model model
    )
    {
        List<CompetitionWithLocationDto> competitionDTOs = competitionService.findAllCompetitions().stream()
                .filter(competition -> !competition.finished())
                .filter(competition -> competitionDateFrom.isBefore(competition.beginning().toLocalDate())
                        && competitionDateTo.isAfter(competition.beginning().toLocalDate()))
                .filter(competition -> competitionCity.equals(competition.competitionLocation().address().city()))
                .filter(competition -> OffsetDateTime.now().plusHours(2L).isBefore(competition.beginning()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        model.addAttribute("headmasterTeacherEmail", headmasterTeacherEmail);
        model.addAttribute("competitionDTOs", competitionDTOs);

        return "headmaster/headmaster_competition_student_cancel_select";
    }

    @GetMapping(HEADMASTER_COMPETITION_STUDENT_CANCEL_CONFIRM)
    public String headmasterCancelStudentApplicationSelectStudent(
            @RequestParam("headmasterTeacherEmail") String headmasterTeacherEmail,
            @RequestParam("competitionId") String competitionId,
            Model model
    )
    {
        Teacher headmasterTeacher = teacherService.findTeacherByEmail(headmasterTeacherEmail);
        String headmasterTeacherId = headmasterTeacher.teacherId();
        Competition competition = competitionService.findCompetitionById(competitionId);
        CompetitionWithLocationDto competitionDto = competitionDtoMapper.mapFromDomainToDto(competition);

        List<StudentDto> studentDTOs = applicationFormService.findAllApplicationForms().stream()
                .filter(applicationForm ->
                        competitionId.equals(applicationForm.competition().competitionId()))
                .filter(applicationForm -> headmasterTeacherId.equals(applicationForm.teacher().teacherId()))
                .map(ApplicationForm::student)
                .map(studentDtoMapper::mapFromDomainToDto)
                .toList();

        model.addAttribute("applicationDeleted", false);
        model.addAttribute("studentDTOs", studentDTOs);
        model.addAttribute("competitionId", competitionId);
        model.addAttribute("competitionDto", competitionDto);

        return "headmaster/headmaster_competition_student_cancel_confirm";
    }

    @PostMapping(HEADMASTER_COMPETITION_STUDENT_CANCEL_CONFIRM)
    public String headmasterCancelStudentConfirm(
            Model model,
            @RequestParam("competitionId") String competitionId,
            @RequestParam("studentId") String studentId
    )
    {
        Student student = studentService.findStudentById(studentId);
        StudentDto studentDto = studentDtoMapper.mapFromDomainToDto(student);
        Competition competition = competitionService.findCompetitionById(competitionId);
        CompetitionWithLocationDto competitionDto = competitionDtoMapper.mapFromDomainToDto(competition);

        Optional<ApplicationForm> studentApplication = applicationFormService.findAllApplicationForms().stream()
                .filter(applicationForm ->
                        competitionId.equals(applicationForm.competition().competitionId()))
                .filter(applicationForm -> studentId.equals(applicationForm.student().studentId()))
                .findAny();

        if (studentApplication.isEmpty()) {
            throw new RuntimeException("Sorry, student [%s] [%s] isn't announced to competition [%s]!"
                    .formatted(student.name(), student.surname(), competition.name()));
        }

        applicationFormService.deleteApplicationForm(studentApplication.get());

        model.addAttribute("applicationDeleted", true);
        model.addAttribute("studentDto", studentDto);
        model.addAttribute("competitionDto", competitionDto);

        return "headmaster/headmaster_competition_student_cancel_confirm";
    }

    @GetMapping(HEADMASTER_COMPETITION_RESULT)
    public String headmasterAnnounceCompetitionResultsHomePage(Model model)
    {
        model.addAttribute("competitionDTOs", Collections.emptyList());
        model.addAttribute("noCompetitions", false);

        return "headmaster/headmaster_competition_result";
    }

    @PostMapping(HEADMASTER_COMPETITION_RESULT)
    public String headmasterPickCompetitionToAnnounceResults(
            @RequestParam("headmasterEmail") String headmasterEmail,
            Model model
    )
    {
        List<CompetitionWithLocationDto> competitionDTOs = competitionService.findAllCompetitions().stream()
                .filter(competition -> !competition.finished())
                .filter(competition ->
                        headmasterEmail.equalsIgnoreCase(competition.headmaster().email()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        if (competitionDTOs.isEmpty()) {
            model.addAttribute("noCompetitions", true);
            return "headmaster/headmaster_competition_result";
        }

        model.addAttribute("noCompetitions", false);
        model.addAttribute("competitionDTOs", competitionDTOs);
        return "headmaster/headmaster_competition_result";
    }

    @GetMapping(HEADMASTER_COMPETITION_ANNOUNCE_RESULT)
    public String headmasterAnnounceCompetitionMainPage(
            @RequestParam("competitionId") String competitionId,
            Model model
    )
    {
        Competition competition = competitionService.findCompetitionById(competitionId);
        CompetitionWithLocationDto competitionDto = competitionDtoMapper.mapFromDomainToDto(competition);

        List<Student> studentList = applicationFormService.findAllApplicationForms().stream()
                .filter(applicationForm -> competitionId.equals(applicationForm.competition().competitionId()))
                .map(ApplicationForm::student)
                .toList();
        List<StudentDto> studentDTOs = studentList.stream()
                .map(studentDtoMapper::mapFromDomainToDto)
                .toList();

        CompetitionResultListDto resultListDto = CompetitionResultListDto.builder()
                .competitionResultsSupport(new ArrayList<>())
                .build();
        List<CompetitionResultListDto.CompetitionResultSupport> competitionResultsSupport
                = resultListDto.getCompetitionResultsSupport();

        for (int i = 0; i < studentDTOs.size(); i++) {
            competitionResultsSupport.add(CompetitionResultListDto.CompetitionResultSupport.builder().build());
        }

        model.addAttribute("competitionDto", competitionDto);
        model.addAttribute("studentDTOs", studentDTOs);
        model.addAttribute("resultListDto", resultListDto);

        return "headmaster/headmaster_competition_result_announce";
    }

    @PostMapping(HEADMASTER_COMPETITION_ANNOUNCE_RESULT)
    public String headmasterAnnounceCompetitionResultsConfirm(
            @RequestParam("competitionId") String competitionId,
            @ModelAttribute("resultListDto") CompetitionResultListDto resultListDto,
            Model model
    )
    {
        List<CompetitionResultDto> resultDTOs = createCompetitionResults(competitionId, resultListDto);
        String competitionName = resultDTOs.stream()
                .map(result -> result.competition().competitionName())
                .findAny().orElseThrow();

        model.addAttribute("resultDTOs", resultDTOs);
        model.addAttribute("competitionName", competitionName);

        return "headmaster/headmaster_competition_result_announce_done";
    }

    @GetMapping(HEADMASTER_COMPETITION_CHECK)
    public String headmasterCheckCompetitionResultsHomePage(Model model) {
        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(Competition::finished)
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        model.addAttribute("cityDTOs", cityDTOs);

        return "headmaster/headmaster_competition_check";
    }

    @GetMapping(HEADMASTER_COMPETITION_CHECK_RESULT)
    public String headmasterCheckCompetitionResultsByFilters(
            Model model,
            @RequestParam("competitionFrom") @DateTimeFormat LocalDate competitionFrom,
            @RequestParam("competitionTo") @DateTimeFormat LocalDate competitionTo,
            @RequestParam("competitionCity") String competitionCity
    )
    {

        List<CompetitionWithLocationDto> competitionDTOs = competitionService.findAllCompetitions().stream()
                .filter(Competition::finished)
                .filter(competition -> competitionFrom.isBefore(competition.beginning().toLocalDate())
                        && competitionTo.isAfter(competition.end().toLocalDate()))
                .filter(competition -> competitionCity.equals(competition.competitionLocation().address().city()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        model.addAttribute("competitionDTOs", competitionDTOs);

        return "headmaster/headmaster_competition_check_filter";
    }

    @PostMapping(HEADMASTER_COMPETITION_CHECK_RESULT)
    public String headmasterCheckCompetitionResultsShowResults(
            Model model,
            @RequestParam("competitionId") String competitionId
    )
    {
        Competition competition = competitionService.findCompetitionById(competitionId);
        String competitionName = competition.name();

        List<CompetitionResultDto> resultDTOs = competitionResultService.findAllCompetitionResults().stream()
                .filter(competitionResult ->
                        competitionId.equals(competitionResult.competition().competitionId()))
                .map(competitionResultDtoMapper::mapFromDomainToDto)
                .toList();

        model.addAttribute("resultDTOs", resultDTOs);
        model.addAttribute("competitionName", competitionName);

        return "headmaster/headmaster_competition_check_result";
    }


    private CompetitionWithLocationDto createCompetition(CompetitionWithLocationDto competitionDto,
                                                         String organizerEmail
    )
    {
        Headmaster competitionOrganizer = headmasterService.findHeadmasterByEmail(organizerEmail);
        Competition competition = competitionDtoMapper.mapFromDtoToDomain(competitionDto);
        if (Objects.nonNull(competitionDto.competitionLocationName())) {
            Competition insertedCompetition = competitionService.insertCompetition(
                    competition.withHeadmaster(competitionOrganizer));
            return competitionDtoMapper.mapFromDomainToDto(insertedCompetition);
        }
        MusicSchool organizerMusicSchool = competitionOrganizer.musicSchool();
        CompetitionLocation competitionLocation = CompetitionLocation.builder()
                .name(organizerMusicSchool.name())
                .address(organizerMusicSchool.address().withAddressId(null))
                .build();

        Competition insertedCompetition
                = competitionService.insertCompetition(competition
                .withHeadmaster(competitionOrganizer)
                .withCompetitionLocation(competitionLocation));
        return competitionDtoMapper.mapFromDomainToDto(insertedCompetition);
    }

    private ApplicationFormDto createStudentApplicationForm(
            Competition competition, Teacher teacher, Student student,
            String classLevel, String performancePieces
    )
    {
        ApplicationForm applicationForm = ApplicationForm.builder()
                .competition(competition)
                .teacher(teacher)
                .student(student)
                .classLevel(ClassLevel.valueOf(classLevel))
                .performancePieces(performancePieces)
                .build();

        ApplicationForm insertedApplicationForm = applicationFormService.insertApplicationForm(applicationForm);
        return applicationFormDtoMapper.mapFromDomainToDto(insertedApplicationForm);
    }

    private List<CompetitionResultDto> createCompetitionResults(String competitionId, CompetitionResultListDto resultListDto) {
        Competition competition = competitionService.findCompetitionById(competitionId);
        List<CompetitionResultListDto.CompetitionResultSupport> competitionResultsSupport
                = resultListDto.getCompetitionResultsSupport();
        List<CompetitionResult> competitionResults = competitionResultsSupport.stream()
                .map(competitionResultSupport -> {
                    return CompetitionResult.builder()
                            .competition(competitionService.updateCompetitionAfterResults(competition))
                            .student(studentService.findStudentById(
                                    competitionResultSupport.getStudentId()))
                            .competitionPlace(competitionResultSupport.getCompetitionPlace())
                            .specialAward(competitionResultSupport.getSpecialAward())
                            .build();
                }).toList();

        List<CompetitionResult> insertedCompetitionResults
                = competitionResultService.insertAllCompetitionResults(competitionResults);

        return insertedCompetitionResults.stream()
                .map(competitionResultDtoMapper::mapFromDomainToDto)
                .toList();
    }

}