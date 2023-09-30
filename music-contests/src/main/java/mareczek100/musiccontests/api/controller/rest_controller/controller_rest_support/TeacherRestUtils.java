package mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.api.dto.ApplicationFormDto;
import mareczek100.musiccontests.api.dto.CompetitionResultDto;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.StudentDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.*;
import mareczek100.musiccontests.api.dto.mapper.ApplicationFormDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.CompetitionResultDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.StudentDtoMapper;
import mareczek100.musiccontests.business.*;
import mareczek100.musiccontests.domain.ApplicationForm;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TeacherRestUtils {

    private final CompetitionService competitionService;
    private final CompetitionDtoMapper competitionDtoMapper;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final StudentDtoMapper studentDtoMapper;
    private final ApplicationFormService applicationFormService;
    private final ApplicationFormDtoMapper applicationFormDtoMapper;
    private final CompetitionResultService competitionResultService;
    private final CompetitionResultDtoMapper competitionResultDtoMapper;


    public ClassLevels findAllClassLevels()
    {
        List<ClassLevel> classLevels
                = Arrays.stream(ClassLevel.values())
                .toList();

        return ClassLevels.builder().classLevelList(classLevels).build();
    }

    public ResponseEntity<StudentsDto> findAllTeacherStudents(
            String teacherEmail
    )
    {
        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        String teacherId = teacher.teacherId();

        List<StudentDto> studentDTOs = studentService.findAllStudents().stream()
                .filter(student -> teacherId.equals(student.teacher().teacherId()))
                .map(studentDtoMapper::mapFromDomainToDto)
                .toList();

        if (studentDTOs.isEmpty()) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                    "You have no students at all!")).build();
        }

        StudentsDto studentsDto = StudentsDto.builder().StudentDtoList(studentDTOs).build();

        return ResponseEntity.ok(studentsDto);
    }

    public ResponseEntity<ApplicationFormDto> announceStudentToCompetition(
            String teacherEmail,
            String studentId,
            String competitionId,
            String classLevel,
            String performancePieces
    )
    {

        Competition competition = competitionService.findCompetitionById(competitionId);
        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        Student student = studentService.findStudentById(studentId);

        if (!OffsetDateTime.now().isBefore(competition.applicationDeadline())) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                    HttpStatus.REQUEST_TIMEOUT, "Too late! It is after announce deadline, " +
                            "You can't no longer put Your student to competition [%s].".formatted(competition.name())
            )).build();
        }

        boolean studentAlreadyInCompetitionAnyMatch = applicationFormService.findAllApplicationForms().stream()
                .anyMatch(applicationForm ->
                        studentId.equals(applicationForm.student().studentId())
                                && competitionId.equals(applicationForm.competition().competitionId()));
        if (studentAlreadyInCompetitionAnyMatch) {
            throw new RuntimeException("Student [%s] [%s] is already announced to competition [%s]!"
                    .formatted(student.name(), student.surname(), competition.name()));
        }

        ApplicationFormDto studentApplicationForm
                = createStudentApplicationForm(competition, teacher, student, classLevel, performancePieces);

        return ResponseEntity.status(HttpStatus.CREATED).body(studentApplicationForm);
    }

    public ResponseEntity<?> updateStudentApplicationForm(
            String applicationFormId,
            String classLevel,
            String performancePieces
    )
    {
        ApplicationForm existingApplicationForm = applicationFormService.findAllApplicationForms().stream()
                .filter(applicationForm -> applicationFormId.equals(applicationForm.applicationFormId()))
                .findAny().orElseThrow(() ->
                        new RuntimeException("Application form [%s] doesn't exists!".formatted(applicationFormId)));

        if (!OffsetDateTime.now().isBefore(existingApplicationForm.competition().applicationDeadline())) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                    HttpStatus.REQUEST_TIMEOUT, "Too late! It is after announce deadline, " +
                            "You can't update Your student application to competition [%s]."
                                    .formatted(existingApplicationForm.competition().name())
            )).build();
        }

        applicationFormService.insertApplicationForm(
                existingApplicationForm
                        .withClassLevel(ClassLevel.valueOf(classLevel))
                        .withPerformancePieces(performancePieces)
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    public ApplicationFormsDto findTeacherApplicationsToCompetition(
            String teacherEmail,
            String competitionId
    )
    {

        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        String teacherId = teacher.teacherId();

        List<ApplicationFormDto> applicationFormDTOs = applicationFormService.findAllApplicationForms().stream()
                .filter(applicationForm ->
                        competitionId.equals(applicationForm.competition().competitionId()))
                .filter(applicationForm -> teacherId.equals(applicationForm.teacher().teacherId()))
                .map(applicationFormDtoMapper::mapFromDomainToDto)
                .toList();


        return ApplicationFormsDto.builder().applicationFormDtoList(applicationFormDTOs).build();
    }

    public CompetitionsDto findFinishedCompetitionsByFilters(
            String dateFrom,
            String dateTo,
            String competitionCity
    )
    {
        LocalDate competitionDateFrom = LocalDate.parse(dateFrom);
        LocalDate competitionDateTo = LocalDate.parse(dateTo);

        List<CompetitionWithLocationDto> competitionDTOs = competitionService.findAllCompetitions().stream()
                .filter(Competition::finished)
                .filter(competition -> competitionDateFrom.isBefore(competition.beginning().toLocalDate())
                        && competitionDateTo.isAfter(competition.beginning().toLocalDate()))
                .filter(competition -> competitionCity.equals(competition.competitionLocation().address().city()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        if (competitionDTOs.isEmpty()) {
            throw new RuntimeException("No finished competitions by these filters!");
        }


        return CompetitionsDto.builder().competitionDtoList(competitionDTOs).build();
    }
    public CompetitionsDto findAllFinishedCompetitions()
    {
        List<CompetitionWithLocationDto> competitionDTOs = competitionService.findAllCompetitions().stream()
                .filter(Competition::finished)
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        if (competitionDTOs.isEmpty()) {
            throw new RuntimeException("No finished competitions right now!");
        }

        return CompetitionsDto.builder().competitionDtoList(competitionDTOs).build();
    }

    public ResponseEntity<?> announceStudentToCompetitionCancel(
            String competitionId,
            String studentId
    )
    {
        Student student = studentService.findStudentById(studentId);
        Competition competition = competitionService.findCompetitionById(competitionId);

        Optional<ApplicationForm> studentApplication = applicationFormService.findAllApplicationForms().stream()
                .filter(applicationForm ->
                        competitionId.equals(applicationForm.competition().competitionId()))
                .filter(applicationForm ->
                        studentId.equals(applicationForm.student().studentId()))
                .findAny();

        if (studentApplication.isEmpty()) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST, "Sorry, student [%s] [%s] isn't announced to competition [%s]!"
                            .formatted(student.name(), student.surname(), competition.name()))).build();
        }

        if (OffsetDateTime.now().isAfter(studentApplication.get().competition().beginning().minusHours(2L))) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                    HttpStatus.REQUEST_TIMEOUT, "Too late! It is less than 2 hours before competition starts"
            )).build();
        }

        applicationFormService.deleteApplicationForm(studentApplication.get());

        return ResponseEntity.noContent().build();
    }

    public CompetitionResultsDto checkTeacherStudentsResults(
            String competitionId,
            String teacherEmail
    )
    {
        Teacher teacher = teacherService.findTeacherByEmail(teacherEmail);
        String teacherId = teacher.teacherId();

        List<CompetitionResultDto> resultDTOs = competitionResultService.findAllCompetitionResults().stream()
                .filter(competitionResult ->
                        competitionId.equals(competitionResult.competition().competitionId()))
                .filter(competitionResult ->
                        teacherId.equals(competitionResult.student().teacher().teacherId()))
                .map(competitionResultDtoMapper::mapFromDomainToDto)
                .toList();

        if (resultDTOs.isEmpty()) {
            throw new RuntimeException("No results!");
        }

        return CompetitionResultsDto.builder().competitionResultDtoList(resultDTOs).build();
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
