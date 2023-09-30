package mareczek100.musiccontests.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import mareczek100.musiccontests.api.dto.CompetitionResultDto;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.InstrumentDto;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.CompetitionResultDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.InstrumentDtoMapper;
import mareczek100.musiccontests.business.CompetitionResultService;
import mareczek100.musiccontests.business.CompetitionService;
import mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService;
import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.CompetitionLocation;
import mareczek100.musiccontests.domain.CompetitionResult;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import static mareczek100.musiccontests.api.controller.StudentController.STUDENT_MAIN_PAGE;

@Validated
@Controller
@RequestMapping(STUDENT_MAIN_PAGE)
@AllArgsConstructor
public class StudentController {

    public static final String STUDENT_MAIN_PAGE = "/student";
    public static final String STUDENT_COMPETITION_FILTERS = "/competition/filters";
    public static final String STUDENT_COMPETITION_INSTRUMENT = "/competition/instrument";
    public static final String STUDENT_COMPETITION_SHOW = "/competition/show";
    public static final String STUDENT_COMPETITION_SHOW_PAGES = "/competition/show/{currentPage}";
    public static final String STUDENT_RESULT = "/result";
    public static final String STUDENT_RESULT_SEARCH = "/result/search";
    public static final String STUDENT_RESULT_SHOW = "/result/show";

    private final CompetitionService competitionService;
    private final CompetitionDtoMapper competitionDtoMapper;
    private final CompetitionResultService competitionResultService;
    private final CompetitionResultDtoMapper competitionResultDtoMapper;
    private final InstrumentApiService instrumentApiService;
    private final InstrumentDtoMapper instrumentDtoMapper;

    @GetMapping
    public String studentHomePage(Model model) {


        return "student/student";
    }

    @GetMapping(STUDENT_COMPETITION_INSTRUMENT)
    public String studentSearchCompetitionsByInstrument(Model model) {

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

        return "student/student_competition_instrument";
    }
    @GetMapping(STUDENT_COMPETITION_FILTERS)
    public String studentSearchCompetitionsByFilters(Model model) {

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

        return "student/student_competition_filters";
    }

    @GetMapping(STUDENT_COMPETITION_SHOW)
    public String studentShowAvailableCompetitions(
            Model model,
            @ModelAttribute ("competitionDto") CompetitionWithLocationDto competitionDto
    )
    {

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

        model.addAttribute("competitionDTOs", competitionDTOs);

        return "student/student_competition_show";
    }
    @GetMapping(STUDENT_COMPETITION_SHOW_PAGES)
    public String studentShowAvailableCompetitionsWithPaginationSortedByInstrument(
            Model model,
            @PathVariable("currentPage") Integer currentPage
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

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("allPages", allPages);
        model.addAttribute("allCompetitions", allCompetitions);
        model.addAttribute("competitionDTOs", competitionDTOs);

        return "student/student_competition_show_pages";
    }

    @GetMapping(STUDENT_RESULT)
    public String studentCheckCompetitionResultHomePage(Model model) {
        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .filter(Competition::finished)
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        model.addAttribute("cityDTOs", cityDTOs);

        return "student/student_result";
    }

    @GetMapping(STUDENT_RESULT_SEARCH)
    public String studentCheckCompetitionResultByFilters(
            Model model,
            @RequestParam("competitionFrom") @DateTimeFormat LocalDate competitionFrom,
            @RequestParam("competitionTo") @DateTimeFormat LocalDate competitionTo,
            @RequestParam("competitionCity") String competitionCity,
            @RequestParam("studentPesel") @Valid @Pattern(regexp = "^\\d{11}$") String studentPesel
    )
    {

        List<CompetitionWithLocationDto> competitionDTOs = competitionResultService.findAllCompetitionResults().stream()
                .filter(competitionResult ->
                        BCrypt.checkpw(studentPesel, competitionResult.student().pesel()))
                .map(CompetitionResult::competition)
                .filter(Competition::finished)
                .filter(competition -> competitionFrom.isBefore(competition.beginning().toLocalDate())
                        && competitionTo.isAfter(competition.beginning().toLocalDate()))
                .filter(competition -> competitionCity.equals(competition.competitionLocation().address().city()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        model.addAttribute("studentPesel", studentPesel);
        model.addAttribute("competitionDTOs", competitionDTOs);

        return "student/student_result_search";
    }

    @PostMapping(STUDENT_RESULT_SHOW)
    public String studentCheckCompetitionResultShowStudentResult(
            Model model,
            @RequestParam("competitionId") String competitionId,
            @RequestParam("studentPesel") String studentPesel
    )
    {
        Competition competition = competitionService.findCompetitionById(competitionId);
        String competitionName = competition.name();

        CompetitionResultDto resultDto = competitionResultService.findAllCompetitionResults().stream()
                .filter(competitionResult ->
                        competitionId.equals(competitionResult.competition().competitionId()))
                .filter(competitionResult ->
                        BCrypt.checkpw(studentPesel, competitionResult.student().pesel()))
                .map(competitionResultDtoMapper::mapFromDomainToDto)
                .toList().get(0);

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("competitionName", competitionName);

        return "student/student_result_show";
    }

}