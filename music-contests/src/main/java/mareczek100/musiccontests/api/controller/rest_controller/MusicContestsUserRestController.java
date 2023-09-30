package mareczek100.musiccontests.api.controller.rest_controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.HeadmasterDto;
import mareczek100.musiccontests.api.dto.MusicSchoolWithAddressDto;
import mareczek100.musiccontests.api.dto.StudentDto;
import mareczek100.musiccontests.api.dto.TeacherDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.MusicSchoolsDto;
import mareczek100.musiccontests.api.dto.mapper.HeadmasterDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.MusicSchoolDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.StudentDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.TeacherDtoMapper;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.domain.*;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static mareczek100.musiccontests.api.controller.rest_controller.MusicContestsUserRestController.MUSIC_CONTESTS_USER_REST_MAIN_PAGE;

@Validated
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = MUSIC_CONTESTS_USER_REST_MAIN_PAGE)
@AllArgsConstructor
public class MusicContestsUserRestController  {
    public static final String MUSIC_CONTESTS_USER_REST_MAIN_PAGE = "/api/user";
    public static final String MUSIC_CONTESTS_USER_DELETE_ACCOUNT = "/delete";
    public static final String CREATE_HEADMASTER = "/create/headmaster";
    public static final String CREATE_TEACHER = "/create/teacher";
    public static final String CREATE_STUDENT = "/create/student";
    public static final String FIND_MUSIC_SCHOOL_BY_ID = "/school/id";
    public static final String FIND_MUSIC_SCHOOL_BY_PATRON = "/school/patron";
    public static final String FIND_ALL_MUSIC_SCHOOLS = "/school/all";
    public static final String CREATE_MUSIC_SCHOOL = "/create/school";

    private final HeadmasterService headmasterService;
    private final HeadmasterDtoMapper headmasterDtoMapper;
    private final TeacherService teacherService;
    private final TeacherDtoMapper teacherDtoMapper;
    private final StudentService studentService;
    private final StudentDtoMapper studentDtoMapper;
    private final MusicSchoolService musicSchoolService;
    private final MusicSchoolDtoMapper musicSchoolDtoMapper;
    private final ApplicationFormService applicationFormService;
    private final CompetitionService competitionService;
    private final CompetitionResultService competitionResultService;


    @PostMapping(CREATE_HEADMASTER)
    @Operation(summary = "Create headmaster account.")
    public ResponseEntity<HeadmasterDto> createHeadmaster(
            @RequestBody @Valid HeadmasterDto headmasterDto,
            @RequestParam("password") @Valid @Pattern(regexp = "^[A-Z](.{7,})$") String password
    )
    {
        Headmaster headmaster = headmasterDtoMapper.mapFromDtoToDomain(headmasterDto);
        Headmaster insertedHeadmaster = headmasterService.insertHeadmaster(headmaster.withPassword(password));
        HeadmasterDto insertedHeadmasterDto = headmasterDtoMapper.mapFromDomainToDto(insertedHeadmaster);
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedHeadmasterDto);
    }
    @PostMapping(CREATE_TEACHER)
    @Operation(summary = "Create teacher account.")
    public ResponseEntity<TeacherDto> createTeacher(
            @RequestBody @Valid TeacherDto teacherDto,
            @RequestParam("password") @Valid @Pattern(regexp = "^[A-Z](.{7,})$") String password
    )
    {
        Teacher teacher = teacherDtoMapper.mapFromDtoToDomain(teacherDto);
        Teacher insertedTeacher = teacherService.insertTeacher(teacher.withPassword(password));
        TeacherDto insertedTeacherDto = teacherDtoMapper.mapFromDomainToDto(insertedTeacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedTeacherDto);
    }
    @PostMapping(CREATE_STUDENT)
    @Operation(summary = "Create student account.")
    public ResponseEntity<StudentDto> createStudent(
            @RequestBody @Valid StudentDto studentDto,
            @RequestParam("password") @Valid @Pattern(regexp = "^[A-Z](.{7,})$") String password
    )
    {
        Student student = studentDtoMapper.mapFromDtoToDomain(studentDto);
        Student insertedStudent = studentService.insertStudent(student.withPassword(password));
        StudentDto insertedStudentDto = studentDtoMapper.mapFromDomainToDto(insertedStudent);
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedStudentDto);
    }
    @GetMapping(FIND_MUSIC_SCHOOL_BY_ID)
    @Operation(summary = "Find music school by id number.")
    public MusicSchoolWithAddressDto findMusicSchoolById(@RequestParam String musicSchoolId)
    {
        MusicSchool musicSchool = musicSchoolService.findMusicSchoolById(musicSchoolId);
        return musicSchoolDtoMapper.mapFromDomainToDto(musicSchool);
    }
    @GetMapping(FIND_MUSIC_SCHOOL_BY_PATRON)
    @Operation(summary = "Find music school by patron. Enter patron's name and surname.")
    public ResponseEntity<MusicSchoolWithAddressDto> findMusicSchoolByPatron(
            @RequestParam String patron
    )
    {
        Optional<MusicSchool> musicSchoolByPatron = musicSchoolService.findMusicSchoolByPatron(patron);

        if (musicSchoolByPatron.isEmpty()){
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                    "Music school with patron [%s] doesn't exist!".formatted(patron))).build();
        }

        MusicSchoolWithAddressDto musicSchoolWithAddressDto
                = musicSchoolDtoMapper.mapFromDomainToDto(musicSchoolByPatron.get());

        return ResponseEntity.ok(musicSchoolWithAddressDto);

    }
    @GetMapping(FIND_ALL_MUSIC_SCHOOLS)
    @Operation(summary = "Find list of available music schools.")
    public MusicSchoolsDto findAllMusicSchools()
    {
        List<MusicSchoolWithAddressDto> musicSchoolDTOs = musicSchoolService.findAllMusicSchools().stream()
                .filter(musicSchool ->
                        !RoleEntity.RoleName.ADMIN.name().equalsIgnoreCase(musicSchool.name()))
                .map(musicSchoolDtoMapper::mapFromDomainToDto)
                .toList();

        return MusicSchoolsDto.builder().musicSchoolDtoList(musicSchoolDTOs).build();
    }
    @PostMapping(CREATE_MUSIC_SCHOOL)
    @Operation(summary = "Create new music school with school address.")
    public ResponseEntity<MusicSchoolWithAddressDto> createMusicSchoolWithAddress(
            @RequestBody @Valid MusicSchoolWithAddressDto musicSchoolWithAddressDto
    )
    {
        MusicSchool musicSchool = musicSchoolDtoMapper.mapFromDtoToDomain(musicSchoolWithAddressDto);
        MusicSchool insertedMusicSchool = musicSchoolService.insertMusicSchool(musicSchool);
        MusicSchoolWithAddressDto insertedMusicSchoolWithAddressDto
                = musicSchoolDtoMapper.mapFromDomainToDto(insertedMusicSchool);
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedMusicSchoolWithAddressDto);
    }
    @DeleteMapping(MUSIC_CONTESTS_USER_DELETE_ACCOUNT)
    @Operation(summary = "Delete music contests user account by email.")
    public ResponseEntity<?> deleteMusicContestsUserAccount(@RequestParam("userEmail") @Email String userEmail)
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
            throw new RuntimeException("Music Contests Portal user [%s] doesn't exist!".formatted(userEmail));
        }

        foundHeadmaster.ifPresent(headmaster -> {
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
        });

        foundTeacher.ifPresent(teacher -> {
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
        });

        foundStudent.ifPresent(student -> {
            List<CompetitionResult> competitionResults = competitionResultService.findAllCompetitionResults().stream()
                    .filter(competitionResult ->
                            student.studentId().equals(competitionResult.student().studentId()))
                    .toList();

            if (!competitionResults.isEmpty()){
                competitionResults.forEach(competitionResultService::deleteCompetitionResult);
            }
            studentService.deleteStudent(student);
        });

        return ResponseEntity.noContent().build();
    }

}