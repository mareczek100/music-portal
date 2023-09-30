package mareczek100.musiccontests.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.*;
import mareczek100.musiccontests.api.dto.dto_class_support.MusicContestsPortalUserDto;
import mareczek100.musiccontests.api.dto.mapper.*;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService;
import mareczek100.musiccontests.domain.*;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.domain.enums.Degree;
import mareczek100.musiccontests.domain.enums.EducationProgram;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static mareczek100.musiccontests.api.controller.MainPageController.MUSIC_CONTESTS_MAIN_PAGE;

@Validated
@Controller
@AllArgsConstructor
@RequestMapping(MUSIC_CONTESTS_MAIN_PAGE)
public class MainPageController {

    public static final String MUSIC_CONTESTS_MAIN_PAGE = "/";
    public static final String MUSIC_CONTESTS_AUTHENTICATION = "/authentication";
    public static final String MUSIC_CONTESTS_LOGIN = "/login";
    public static final String MUSIC_CONTESTS_LOGOUT = "/logout";
    public static final String MUSIC_CONTESTS_LOGOUT_SUCCESS = "/logout/success";
    public static final String MUSIC_CONTESTS_FAILURE = "/failure";
    public static final String MUSIC_CONTESTS_ERROR = "/error";
    public static final String MUSIC_CONTESTS_CREATE_ACCOUNT = "/account";
    public static final String MUSIC_CONTESTS_DELETE_ACCOUNT = "/account/delete";
    public static final String MUSIC_CONTESTS_ACCOUNT_STUDENT = "/account/student";
    public static final String MUSIC_CONTESTS_ACCOUNT_TEACHER = "/account/teacher";
    private final static Boolean ACCOUNT_CREATED_SUCCESS = true;
    private final static Boolean ACCOUNT_CREATED_FAILURE = false;
    private final static Boolean ACCOUNT_DELETED_SUCCESS = true;
    private final static Boolean ACCOUNT_DELETED_FAILURE = false;
    private final static String NONE_MUSIC_SCHOOL = "NONE";
    private final static String NONE_SECOND_INSTRUMENT = "NONE";

    private final HeadmasterService headmasterService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final MusicSchoolService musicSchoolService;
    private final InstrumentApiService instrumentApiService;
    private final InstrumentDtoMapper instrumentDtoMapper;
    private final MusicSchoolDtoMapper musicSchoolDtoMapper;
    private final HeadmasterDtoMapper headmasterDtoMapper;
    private final TeacherDtoMapper teacherDtoMapper;
    private final StudentDtoMapper studentDtoMapper;
    private final ApplicationFormService applicationFormService;
    private final CompetitionResultService competitionResultService;
    private final CompetitionService competitionService;

    @GetMapping(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_LOGIN)
    public String loginHomePage() {

        return "login/login_main_page";
    }

    @GetMapping(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_CREATE_ACCOUNT)
    public String loginCreateAccountHomePage(Model model) {
        List<String> roleList = Arrays.stream(RoleEntity.RoleName.values())
                .filter(roleName -> !roleName.equals(RoleEntity.RoleName.ADMIN))
                .map(Enum::name).toList();

        List<MusicSchoolWithAddressDto> musicSchoolDTOs = musicSchoolService.findAllMusicSchools().stream()
                .filter(musicSchool ->
                        !RoleEntity.RoleName.ADMIN.name().equalsIgnoreCase(musicSchool.name()))
                .map(musicSchoolDtoMapper::mapFromDomainToDto)
                .toList();

        model.addAttribute("roleList", roleList);
        model.addAttribute("musicSchoolDTOs", musicSchoolDTOs);

        return "login/login_create_account";
    }

    @PostMapping(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_CREATE_ACCOUNT)
    public ModelAndView loginCreateAccountProcess(
            @ModelAttribute("musicSchoolDto") MusicSchoolWithAddressDto musicSchoolDto,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") @Email String email,
            @RequestParam("pesel") @Valid @Pattern(regexp = "^\\d{11}$") String pesel,
            @RequestParam("password") @Valid @Pattern(regexp = "^[A-Z](.{7,})$") String password,
            @RequestParam("role") String role
    )
    {

        RoleEntity.RoleName roleName = RoleEntity.RoleName.valueOf(role);
        MusicContestsPortalUserDto portalUser = MusicContestsPortalUserDto.builder()
                .name(name)
                .surname(surname)
                .email(email.strip())
                .pesel(pesel)
                .password(password)
                .role(roleName)
                .build();

        List<InstrumentDto> instrumentDTOs = instrumentApiService.findAllInstruments().stream()
                .map(instrumentDtoMapper::mapFromDomainToDto)
                .toList();

        String musicSchoolId = Objects.requireNonNullElse(musicSchoolDto.musicSchoolId(), "");

        if (musicSchoolId.equals(NONE_MUSIC_SCHOOL)) {
            MusicSchoolWithAddressDto musicSchoolToCreateDto = MusicSchoolWithAddressDto.builder().build();
            ModelAndView musicSchoolModelView = new ModelAndView("login/login_create_music_school");
            musicSchoolModelView.addObject("portalUser", portalUser);
            musicSchoolModelView.addObject("musicSchoolDto", musicSchoolToCreateDto);
            return musicSchoolModelView;
        }

        return switch (roleName) {
            case HEADMASTER -> createHeadmaster(portalUser, musicSchoolDto.withMusicSchoolId(musicSchoolId));
            case TEACHER -> prepareTeacher(portalUser, musicSchoolDto, instrumentDTOs);
            case STUDENT -> prepareStudent(portalUser, musicSchoolDto, instrumentDTOs);
            default -> {
                ModelAndView failureModelView = new ModelAndView("login/login_failure");
                failureModelView.addObject("accountCreated", ACCOUNT_CREATED_FAILURE);
                yield failureModelView;
            }
        };
    }
    @PostMapping(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_ACCOUNT_TEACHER)
    public String loginCreateTeacherAccountProcess(
            @Valid @ModelAttribute TeacherDto teacherDto,
            @Valid @RequestParam ("teacherPassword") String teacherPassword,
            @Valid @ModelAttribute("musicSchoolDto") MusicSchoolWithAddressDto musicSchoolDto,
            Model model
    )
    {
        TeacherDto insertedTeacherDto = createTeacher(teacherDto, teacherPassword, musicSchoolDto);
        model.addAttribute("accountCreated", ACCOUNT_CREATED_SUCCESS);
        model.addAttribute("portalUser", insertedTeacherDto);

        return "login/login_main_page";
    }

    @PostMapping(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_ACCOUNT_STUDENT)
    public String loginCreateStudentAccountProcess(
            @Valid @ModelAttribute StudentDto studentDto,
            @ModelAttribute("musicSchoolDto") MusicSchoolWithAddressDto musicSchoolDto,
            @Valid @RequestParam ("studentPassword") String studentPassword,
            @RequestParam("teacherEmail") @Email String teacherEmail,
            Model model
    )
    {
        String secondInstrument = studentDto.secondInstrument();

        if (secondInstrument.equals(NONE_SECOND_INSTRUMENT)) {
           studentDto = studentDto.withSecondInstrument("");
        }

        StudentDto insertedStudentDto = createStudent(studentDto, teacherEmail, studentPassword, musicSchoolDto);
        model.addAttribute("accountCreated", ACCOUNT_CREATED_SUCCESS);
        model.addAttribute("portalUser", insertedStudentDto);

        return "login/login_main_page";
    }

    @PostMapping(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_DELETE_ACCOUNT)
    public String deleteMusicContestsUserAccount(
            @RequestParam("userEmail") @Email String userEmail,
            Model model
    )
    {
        Optional<Headmaster> foundHeadmaster = headmasterService.findAllHeadmasters().stream()
                .filter(headmaster -> userEmail.equalsIgnoreCase(headmaster.email()))
                .findAny();

        Optional<Teacher> foundTeacher = teacherService.findAllTeachers().stream()
                .filter(teacher -> userEmail.equalsIgnoreCase(teacher.email()))
                .findAny();

        Optional<Student> foundStudent = studentService.findAllStudents().stream()
                .filter(student -> userEmail.equalsIgnoreCase(student.email()))
                .findAny();

        if (foundHeadmaster.isEmpty() && foundTeacher.isEmpty() && foundStudent.isEmpty())
        {
            model.addAttribute("accountDeleted", ACCOUNT_DELETED_FAILURE);
            model.addAttribute("userEmail", userEmail);
            return "login/login_main_page";
        }

        if (foundHeadmaster.isPresent()) {
            Headmaster headmaster = foundHeadmaster.get();

            List<Competition> competitions = competitionService.findAllCompetitions().stream()
                    .filter(competition ->
                            headmaster.headmasterId().equals(competition.headmaster().headmasterId()))
                    .toList();

            List<String> competitionIdList = competitions.stream().map(Competition::competitionId).toList();

            List<ApplicationForm> applicationForms = applicationFormService.findAllApplicationForms().stream()
                    .filter(applicationForm ->
                            competitionIdList.contains(applicationForm.competition().competitionId()))
                    .toList();

            List<CompetitionResult> competitionResults = competitionResultService.findAllCompetitionResults().stream()
                    .filter(competitionResult ->
                            competitionIdList.contains(competitionResult.competition().competitionId()))
                    .toList();

            if (!competitions.isEmpty()){
                competitions.forEach(competitionService::deleteCompetition);
            }

            if (!applicationForms.isEmpty()){
                applicationForms.forEach(applicationFormService::deleteApplicationForm);
            }

            if (!competitionResults.isEmpty()){
                competitionResults.forEach(competitionResultService::deleteCompetitionResult);
            }

            headmasterService.deleteHeadmaster(headmaster);
            model.addAttribute("portalUser", headmaster);
        }

        if (foundTeacher.isPresent()) {
            Teacher teacher = foundTeacher.get();

            List<ApplicationForm> applicationForms = applicationFormService.findAllApplicationForms().stream()
                    .filter(applicationForm -> teacher.teacherId().equals(applicationForm.teacher().teacherId()))
                    .toList();

            List<Student> students = studentService.findAllStudents().stream()
                    .filter(student -> teacher.teacherId().equals(student.teacher().teacherId()))
                    .toList();

            if (!applicationForms.isEmpty()){
                applicationForms.forEach(applicationFormService::deleteApplicationForm);
            }

            if (!students.isEmpty()){
                students.forEach(studentService::deleteStudent);
            }
            teacherService.deleteTeacher(teacher);
            model.addAttribute("portalUser", teacher);
        }

        if (foundStudent.isPresent()) {
            Student student = foundStudent.get();
            List<CompetitionResult> competitionResults = competitionResultService.findAllCompetitionResults().stream()
                    .filter(competitionResult ->
                            student.studentId().equals(competitionResult.student().studentId()))
                    .toList();

            if (!competitionResults.isEmpty()){
            competitionResults.forEach(competitionResultService::deleteCompetitionResult);
            }

            studentService.deleteStudent(student);
            model.addAttribute("portalUser", student);
        }

        model.addAttribute("accountDeleted", ACCOUNT_DELETED_SUCCESS);
        return "login/login_main_page";
    }

    @GetMapping(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_FAILURE)
    public String loginFailureHomePage() {

        return "login/login_failure";
    }

    @GetMapping(MUSIC_CONTESTS_ERROR)
    public String errorHomePage() {
        return "error";
    }

    @GetMapping(MUSIC_CONTESTS_AUTHENTICATION + MUSIC_CONTESTS_LOGOUT_SUCCESS)
    public String loginLogoutHomePage() {
        return "login/login_logout";
    }

    private ModelAndView createHeadmaster(
            MusicContestsPortalUserDto portalUser,
            MusicSchoolWithAddressDto musicSchoolDto
    )
    {
        ModelAndView headmasterModelView = new ModelAndView("login/login_main_page");

        Headmaster headmaster = Headmaster.builder()
                .name(portalUser.getName())
                .surname(portalUser.getSurname())
                .email(portalUser.getEmail())
                .pesel(portalUser.getPesel())
                .password(portalUser.getPassword())
                .build();

        HeadmasterDto headmasterDto;
        MusicSchool musicSchool;

        if (!musicSchoolDto.musicSchoolId().isEmpty()) {
            musicSchool = musicSchoolService.findMusicSchoolById(musicSchoolDto.musicSchoolId());
        } else {
            musicSchool = musicSchoolDtoMapper.mapFromDtoToDomain(musicSchoolDto);
        }
        Headmaster insertedHeadmaster = headmasterService.insertHeadmaster(
                headmaster.withMusicSchool(musicSchool));
        headmasterDto = headmasterDtoMapper.mapFromDomainToDto(insertedHeadmaster);

        headmasterModelView.addObject("portalUser", headmasterDto);
        headmasterModelView.addObject("accountCreated", ACCOUNT_CREATED_SUCCESS);
        headmasterModelView.setStatus(HttpStatus.CREATED);

        return headmasterModelView;
    }

    private ModelAndView prepareTeacher(MusicContestsPortalUserDto portalUser,
                                        MusicSchoolWithAddressDto musicSchoolDto,
                                        List<InstrumentDto> instrumentDTOs
    )
    {
        TeacherDto teacherDto = TeacherDto.builder().build();
        ModelAndView teacherModelView = new ModelAndView("login/login_create_teacher");
        teacherModelView.addObject("portalUser", portalUser);
        teacherModelView.addObject("teacherDto", teacherDto);
        teacherModelView.addObject("instrumentDTOs", instrumentDTOs);
        teacherModelView.addObject("musicSchoolDto", musicSchoolDto);
        return teacherModelView;
    }

    private TeacherDto createTeacher(TeacherDto teacherDto,
                                     String teacherPassword,
                                     MusicSchoolWithAddressDto musicSchoolDto
    )
    {
        Teacher teacher;

        if (!musicSchoolDto.musicSchoolId().isEmpty()) {
            MusicSchool musicSchool = musicSchoolService.findMusicSchoolById(musicSchoolDto.musicSchoolId());
            MusicSchoolWithAddressDto insertedMusicSchoolDto = musicSchoolDtoMapper.mapFromDomainToDto(musicSchool);
            teacher = teacherDtoMapper.mapFromDtoToDomain(teacherDto.withMusicSchool(insertedMusicSchoolDto));
        } else {
            teacher = teacherDtoMapper.mapFromDtoToDomain(teacherDto.withMusicSchool(musicSchoolDto));
        }

        Teacher insertedTeacher = teacherService.insertTeacher(teacher.withPassword(teacherPassword));
        return teacherDtoMapper.mapFromDomainToDto(insertedTeacher);
    }

    private ModelAndView prepareStudent(MusicContestsPortalUserDto portalUser,
                                        MusicSchoolWithAddressDto musicSchoolDto,
                                        List<InstrumentDto> instrumentDTOs
    )
    {
        StudentDto studentDto = StudentDto.builder().build();
        ModelAndView studentModelView = new ModelAndView("login/login_create_student");

        List<ClassLevel> classLevels = Arrays.stream(ClassLevel.values()).toList();
        List<EducationProgram> educationPrograms = Arrays.stream(EducationProgram.values()).toList();
        List<Degree> degrees = Arrays.stream(Degree.values()).toList();
        List<TeacherDto> teacherDTOs = teacherService.findAllTeachers().stream()
                .filter(teacher -> musicSchoolDto.musicSchoolId()
                        .equals(teacher.musicSchool().musicSchoolId()))
                .map(teacherDtoMapper::mapFromDomainToDto)
                .toList();

        studentModelView.addObject("classLevels", classLevels);
        studentModelView.addObject("educationPrograms", educationPrograms);
        studentModelView.addObject("degrees", degrees);
        studentModelView.addObject("teacherDTOs", teacherDTOs);
        studentModelView.addObject("portalUser", portalUser);
        studentModelView.addObject("studentDto", studentDto);
        studentModelView.addObject("instrumentDTOs", instrumentDTOs);
        studentModelView.addObject("musicSchoolDto", musicSchoolDto);

        return studentModelView;
    }

    private StudentDto createStudent(StudentDto studentDto,
                                     String teacherEmail,
                                     String studentPassword,
                                     MusicSchoolWithAddressDto musicSchoolDto
    )
    {
        Teacher teacherByEmail = teacherService.findTeacherByEmail(teacherEmail);
        Student student;

        if (!musicSchoolDto.musicSchoolId().isEmpty()) {
            MusicSchool musicSchool = musicSchoolService.findMusicSchoolById(musicSchoolDto.musicSchoolId());
            MusicSchoolWithAddressDto insertedMusicSchoolDto = musicSchoolDtoMapper.mapFromDomainToDto(musicSchool);
            student = studentDtoMapper.mapFromDtoToDomain(studentDto.withMusicSchool(insertedMusicSchoolDto));
        } else {
            student = studentDtoMapper.mapFromDtoToDomain(studentDto.withMusicSchool(musicSchoolDto));
        }

        Student insertedStudent = studentService.insertStudent(student
                .withTeacher(teacherByEmail)
                .withPassword(studentPassword)
        );
        return studentDtoMapper.mapFromDomainToDto(insertedStudent);
    }
}