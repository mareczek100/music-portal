package mareczek100.musiccontests.api.controller;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static mareczek100.musiccontests.api.controller.TeacherController.TEACHER_MAIN_PAGE;

@Validated
@Controller
@RequestMapping(TEACHER_MAIN_PAGE)
@AllArgsConstructor
public class TeacherController {

    public static final String TEACHER_MAIN_PAGE = "/teacher";
    public static final String TEACHER_STUDENT_COMPETITIONS_SEARCH = "/student/competition/search";
    public static final String TEACHER_STUDENT_COMPETITIONS_FILTERS = "/student/competition/filters";
    public static final String TEACHER_STUDENT_COMPETITIONS_INSTRUMENT = "/student/competition/instrument";
    public static final String TEACHER_STUDENT_COMPETITIONS_ALL = "/student/competition/all/{currentPage}";
    public static final String TEACHER_STUDENT_SELECT = "/student/competition/select";
    public static final String TEACHER_STUDENT_ANNOUNCE = "/student/competition/announce";
    public static final String TEACHER_STUDENT_CANCEL = "/student/cancel";
    public static final String TEACHER_STUDENT_CANCEL_SELECT = "/student/cancel/select";
    public static final String TEACHER_STUDENT_CANCEL_CONFIRM = "/student/cancel/confirm";
    public static final String TEACHER_RESULT = "/result/competition";
    public static final String TEACHER_RESULT_SEARCH = "/result/competition/search";
    public static final String TEACHER_RESULT_SHOW = "/result/competition/show";

    private final CompetitionService competitionService;
    private final CompetitionDtoMapper competitionDtoMapper;
    private final InstrumentApiService instrumentApiService;
    private final InstrumentDtoMapper instrumentDtoMapper;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final StudentDtoMapper studentDtoMapper;
    private final ApplicationFormService applicationFormService;
    private final ApplicationFormDtoMapper applicationFormDtoMapper;
    private final CompetitionResultService competitionResultService;
    private final CompetitionResultDtoMapper competitionResultDtoMapper;

    @GetMapping
    public String teacherHomePage() {

        return "teacher/teacher";
    }
    @GetMapping(TEACHER_STUDENT_COMPETITIONS_SEARCH)
    public String teacherSearchCompetitionsHomePage(Model model)
    {
        List<Competition> competitions = competitionService.findAllCompetitions().stream()
                .filter(competition -> !competition.finished())
                .toList();

        model.addAttribute("competitions", competitions);

        return "teacher/teacher_student_competition_search";
    }

    @GetMapping(TEACHER_STUDENT_COMPETITIONS_ALL)
    public String teacherSearchAllCompetitionsWithPaginationAndSortedByInstrument(
            @RequestParam("teacherEmail") String teacherEmail,
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

        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        String teacherId = teacher.teacherId();

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
        model.addAttribute("teacherEmail", teacherEmail);

        return "teacher/teacher_student_competition_all";
    }

    @GetMapping(TEACHER_STUDENT_COMPETITIONS_INSTRUMENT)
    public String teacherSearchCompetitionsByInstrument(
            @RequestParam("teacherEmail") String teacherEmail,
            Model model
    ) {

        List<InstrumentDto> instrumentDTOs = instrumentApiService.findAllInstruments().stream()
                .map(instrumentDtoMapper::mapFromDomainToDto)
                .toList();

        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(competition -> !competition.finished())
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        CompetitionWithLocationDto competitionDto = CompetitionWithLocationDto.builder().build();

        model.addAttribute("instrumentDTOs", instrumentDTOs);
        model.addAttribute("competitionDto", competitionDto);
        model.addAttribute("cityDTOs", cityDTOs);
        model.addAttribute("teacherEmail", teacherEmail);

        return "teacher/teacher_student_competition_instrument";
    }

    @GetMapping(TEACHER_STUDENT_COMPETITIONS_FILTERS)
    public String teacherSearchCompetitionsByFilters(
            @RequestParam("teacherEmail") String teacherEmail,
            Model model
    ) {

        List<InstrumentDto> instrumentDTOs = instrumentApiService.findAllInstruments().stream()
                .map(instrumentDtoMapper::mapFromDomainToDto)
                .toList();

        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(competition -> !competition.finished())
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        CompetitionWithLocationDto competitionDto = CompetitionWithLocationDto.builder().build();

        model.addAttribute("instrumentDTOs", instrumentDTOs);
        model.addAttribute("competitionDto", competitionDto);
        model.addAttribute("cityDTOs", cityDTOs);
        model.addAttribute("teacherEmail", teacherEmail);

        return "teacher/teacher_student_competition_filters";
    }

    @GetMapping(TEACHER_STUDENT_SELECT)
    public String teacherSelectStudentToCompetition(
            Model model,
            @RequestParam("teacherEmail") String teacherEmail,
            @ModelAttribute ("competitionDto") CompetitionWithLocationDto competitionDto
    )
    {
        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        String teacherId = teacher.teacherId();
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
        model.addAttribute("teacherEmail", teacherEmail);
        model.addAttribute("classLevels", classLevels);

        return "teacher/teacher_student_select";
    }

    @PostMapping(TEACHER_STUDENT_ANNOUNCE)
    public String teacherStudentAnnounceToCompetition(
            @RequestParam("teacherEmail") String teacherEmail,
            @RequestParam("studentId") String studentId,
            @RequestParam("competitionId") String competitionId,
            @RequestParam("classLevel") String classLevel,
            @RequestParam("performancePieces") String performancePieces,
            Model model
    )
    {
        Competition competition = competitionService.findCompetitionById(competitionId);
        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
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

        return "teacher/teacher_student_announce";
    }

    @GetMapping(TEACHER_STUDENT_CANCEL)
    public String teacherCancelStudentApplicationForm(Model model) {

        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(competition -> !competition.finished())
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        model.addAttribute("cityDTOs", cityDTOs);

        return "teacher/teacher_student_cancel";
    }

    @GetMapping(TEACHER_STUDENT_CANCEL_SELECT)
    public String teacherCancelStudentApplicationFormSelectCompetition(
            @RequestParam("competitionDateFrom") @DateTimeFormat LocalDate competitionDateFrom,
            @RequestParam("competitionDateTo") @DateTimeFormat LocalDate competitionDateTo,
            @RequestParam("teacherEmail") String teacherEmail,
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

        model.addAttribute("teacherEmail", teacherEmail);
        model.addAttribute("competitionDTOs", competitionDTOs);

        return "teacher/teacher_student_cancel_select";
    }

    @GetMapping(TEACHER_STUDENT_CANCEL_CONFIRM)
    public String teacherCancelStudentApplicationFormSelectStudent(
            @RequestParam("teacherEmail") String teacherEmail,
            @RequestParam("competitionId") String competitionId,
            Model model
    )
    {

        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        String teacherId = teacher.teacherId();
        Competition competition = competitionService.findCompetitionById(competitionId);
        CompetitionWithLocationDto competitionDto = competitionDtoMapper.mapFromDomainToDto(competition);

        List<StudentDto> studentDTOs = applicationFormService.findAllApplicationForms().stream()
                .filter(applicationForm ->
                        competitionId.equals(applicationForm.competition().competitionId()))
                .filter(applicationForm -> teacherId.equals(applicationForm.teacher().teacherId()))
                .map(ApplicationForm::student)
                .map(studentDtoMapper::mapFromDomainToDto)
                .toList();

        model.addAttribute("applicationDeleted", false);
        model.addAttribute("studentDTOs", studentDTOs);
        model.addAttribute("competitionId", competitionId);
        model.addAttribute("competitionDto", competitionDto);

        return "teacher/teacher_student_cancel_confirm";
    }

    @PostMapping(TEACHER_STUDENT_CANCEL_CONFIRM)
    public String teacherCancelStudentConfirm(
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

        return "teacher/teacher_student_cancel_confirm";
    }

    @GetMapping(TEACHER_RESULT)
    public String teacherCheckCompetitionResultsHomePage(Model model) {
        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(Competition::finished)
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        model.addAttribute("cityDTOs", cityDTOs);

        return "teacher/teacher_result";
    }

    @GetMapping(TEACHER_RESULT_SEARCH)
    public String teacherCheckCompetitionResultsByFilters(Model model,
                                                         @RequestParam("competitionFrom") @DateTimeFormat LocalDate competitionFrom,
                                                         @RequestParam("competitionTo") @DateTimeFormat LocalDate competitionTo,
                                                         @RequestParam("competitionCity") String competitionCity
    )
    {

        List<CompetitionWithLocationDto> competitionDTOs = competitionService.findAllCompetitions().stream()
                .filter(Competition::finished)
                .filter(competition -> competitionFrom.isBefore(competition.beginning().toLocalDate())
                        && competitionTo.isAfter(competition.beginning().toLocalDate()))
                .filter(competition -> competitionCity.equals(competition.competitionLocation().address().city()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        model.addAttribute("competitionDTOs", competitionDTOs);

        return "teacher/teacher_result_search";
    }

    @PostMapping(TEACHER_RESULT_SHOW)
    public String teacherCheckCompetitionResultsShowResults(Model model,
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

        return "teacher/teacher_result_show";
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

}
