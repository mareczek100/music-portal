package mareczek100.musiccontests.api.controller.rest_controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.AllUsersRestUtils;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.ControllerRestSupport;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.TeacherRestUtils;
import mareczek100.musiccontests.api.dto.ApplicationFormDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static mareczek100.musiccontests.api.controller.rest_controller.TeacherRestController.TEACHER_REST_MAIN_PAGE;

@Validated
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = TEACHER_REST_MAIN_PAGE)
@AllArgsConstructor
public class TeacherRestController implements ControllerRestSupport {
    public static final String TEACHER_REST_MAIN_PAGE = "/api/teacher";

    private final AllUsersRestUtils allUsersRestUtils;
    private final TeacherRestUtils teacherRestUtils;


    @GetMapping(FIND_ALL_INSTRUMENTS)
    @Operation(summary = "Find list of available instruments.")
    public InstrumentsDto findAllAvailableInstruments()
    {
        return allUsersRestUtils.findAllAvailableInstruments();
    }

    @GetMapping(FIND_ALL_CITIES)
    @Operation(summary = "Find list of available competition's cities.")
    public CitiesDto findAllAvailableCompetitionCities()
    {
        return allUsersRestUtils.findAllAvailableCompetitionCities();
    }

    @GetMapping(FIND_AVAILABLE_CLASS_LEVELS)
    @Operation(summary = "Find list of available class levels.")
    public ClassLevels findAllClassLevels()
    {
        return teacherRestUtils.findAllClassLevels();
    }

    @GetMapping(FIND_ALL_COMPETITIONS)
    @Operation(summary = "Find list of all available music competitions. Sorted by instrument, without paging.")
    public ResponseEntity<CompetitionsDto> findAllAvailableCompetitions()
    {
        return allUsersRestUtils.findAllAvailableCompetitions();
    }

    @GetMapping(FIND_ALL_COMPETITIONS_PAGEABLE)
    @Operation(summary = "Find list of all available music competitions - 5 results per page, sorted by instrument.")
    public ResponseEntity<CompetitionsDto> findAllAvailableCompetitionsWithPagingAndSorting(
            @PathVariable("currentPage") Integer currentPage
    )
    {
        return allUsersRestUtils.findAllAvailableCompetitionsPageable(currentPage);
    }

    @GetMapping(FIND_AVAILABLE_COMPETITIONS_BY_FILTERS)
    @Operation(summary = "Find list of available music competitions by filters.")
    public CompetitionsDto findAvailableCompetitionsByFilters(
            @RequestParam("competitionInstrument") String competitionInstrument,
            @RequestParam("competitionOnline") Boolean competitionOnline,
            @RequestParam("competitionPrimaryDegree") Boolean competitionPrimaryDegree,
            @RequestParam("competitionSecondaryDegree") Boolean competitionSecondaryDegree,
            @RequestParam("competitionCity") String competitionCity
    )
    {
        return allUsersRestUtils.findAvailableCompetitionsByFilters(
                competitionInstrument, competitionOnline, competitionPrimaryDegree,
                competitionSecondaryDegree, competitionCity
        );
    }

    @GetMapping(FIND_AVAILABLE_COMPETITIONS_BY_INSTRUMENT)
    @Operation(summary = "Find list of all available music competitions for chosen instrument.")
    public CompetitionsDto findAllAvailableCompetitionsByInstrument(
            @RequestParam("competitionInstrument") String competitionInstrument
    )
    {
        return allUsersRestUtils.findAvailableCompetitionsByInstrument(competitionInstrument);
    }

    @GetMapping(FIND_FINISHED_COMPETITIONS_BY_FILTERS)
    @Operation(summary = "Find list of finished music competitions by filters. Date format: yyyy-MM-dd.")
    public CompetitionsDto findFinishedCompetitionsByFilters(
            @RequestParam("competitionDateFrom") @DateTimeFormat String competitionDateFrom,
            @RequestParam("competitionDateTo") @DateTimeFormat String competitionDateTo,
            @RequestParam("competitionCity") String competitionCity
    )
    {
        return teacherRestUtils.findFinishedCompetitionsByFilters(
                competitionDateFrom, competitionDateTo, competitionCity);
    }

    @GetMapping(FIND_FINISHED_COMPETITIONS)
    @Operation(summary = "Find list of all finished music competitions.")
    public CompetitionsDto findFinishedCompetitionsByFilters()
    {
        return teacherRestUtils.findAllFinishedCompetitions();
    }

    @GetMapping(FIND_ALL_TEACHER_STUDENTS)
    @Operation(summary = "Find list of teacher's students.")
    public ResponseEntity<StudentsDto> findAllTeacherStudents(
            @RequestParam("teacherEmail") @Email String teacherEmail
    )
    {
        return teacherRestUtils.findAllTeacherStudents(teacherEmail);
    }

    @PostMapping(ANNOUNCE_STUDENT_TO_COMPETITION)
    @Operation(summary = "Fill in application form to announce student to competition.")
    public ResponseEntity<ApplicationFormDto> announceStudentToCompetition(
            @RequestParam("teacherEmail") @Email String teacherEmail,
            @RequestParam("studentId") String studentId,
            @RequestParam("competitionId") String competitionId,
            @RequestParam("classLevel") String classLevel,
            @RequestParam("performancePieces") String performancePieces
    )
    {
        return teacherRestUtils.announceStudentToCompetition(
                teacherEmail, studentId, competitionId, classLevel, performancePieces);
    }

    @PatchMapping(ANNOUNCE_STUDENT_UPDATE)
    @Operation(summary = "Update student's application form, change class level and pieces to perform.")
    public ResponseEntity<?> updateStudentApplicationFormPerformancePieces(
            @PathVariable("applicationFormId") String applicationFormId,
            @RequestParam("classLevel") String classLevel,
            @RequestParam("performancePieces") String performancePieces
    )
    {
        return teacherRestUtils.updateStudentApplicationForm(
                applicationFormId, classLevel, performancePieces);
    }

    @GetMapping(FIND_TEACHER_APPLICATIONS)
    @Operation(summary = "Find list of teacher's applications to competition.")
    public ApplicationFormsDto findTeacherApplicationsToCompetition(
            @RequestParam("teacherEmail") @Email String teacherEmail,
            @RequestParam("competitionId") String competitionId
    )
    {
        return teacherRestUtils.findTeacherApplicationsToCompetition(teacherEmail, competitionId);
    }

    @DeleteMapping(ANNOUNCE_STUDENT_CANCEL)
    @Operation(summary = "Cancel student's application to competition.")
    public ResponseEntity<?> announceStudentToCompetitionCancel(
            @RequestParam("competitionId") String competitionId,
            @RequestParam("studentId") String studentId
    )
    {
        return teacherRestUtils.announceStudentToCompetitionCancel(competitionId, studentId);
    }


    @GetMapping(CHECK_RESULT)
    @Operation(summary = "Check teacher's students competition results.")
    public CompetitionResultsDto checkTeacherStudentsResults(
            @RequestParam("competitionId") String competitionId,
            @RequestParam("teacherEmail") @Email String teacherEmail
    )
    {
        return teacherRestUtils.checkTeacherStudentsResults(competitionId, teacherEmail);
    }
}