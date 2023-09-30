package mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.InstrumentDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CitiesDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CompetitionsDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.InstrumentsDto;
import mareczek100.musiccontests.api.dto.mapper.CompetitionDtoMapper;
import mareczek100.musiccontests.api.dto.mapper.InstrumentDtoMapper;
import mareczek100.musiccontests.business.CompetitionService;
import mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService;
import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.CompetitionLocation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
@Component
@RequiredArgsConstructor
public class AllUsersRestUtils {

    private final CompetitionService competitionService;
    private final CompetitionDtoMapper competitionDtoMapper;
    private final InstrumentApiService instrumentApiService;
    private final InstrumentDtoMapper instrumentDtoMapper;

    public InstrumentsDto findAllAvailableInstruments()
    {
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(0, 5, sort);
        List<InstrumentDto> instrumentDTOs = instrumentApiService.findAllInstruments().stream()
                .map(instrumentDtoMapper::mapFromDomainToDto)
                .toList();

        return InstrumentsDto.builder().instrumentDtoList(instrumentDTOs).build();
    }

    public CitiesDto findAllAvailableCompetitionCities()
    {
        List<String> cityDTOs = competitionService.findAllCompetitions().stream()
                .map(Competition::competitionLocation)
                .map(CompetitionLocation::address)
                .map(Address::city)
                .distinct()
                .toList();

        return CitiesDto.builder().competitionCitiesDtoList(cityDTOs).build();
    }
    public ResponseEntity<CompetitionsDto> findAllAvailableCompetitions()
    {
        List<CompetitionWithLocationDto> competitionDTOs
                = competitionService.findAllCompetitions().stream()
                .filter(competition -> !competition.finished())
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        if (competitionDTOs.isEmpty()){
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_FOUND, "No competitions, at all!")).build();
        }

        CompetitionsDto competitionsDto = CompetitionsDto.builder().competitionDtoList(competitionDTOs).build();

        return ResponseEntity.ok(competitionsDto);
    }
    public ResponseEntity<CompetitionsDto> findAllAvailableCompetitionsPageable(Integer pageNumber)
    {
        List<CompetitionWithLocationDto> competitionDTOs
                = competitionService.findAllCompetitionsPageable(pageNumber).getContent().stream()
                .filter(competition -> !competition.finished())
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        if (competitionDTOs.isEmpty()){
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_FOUND, "No more competitions, page doesn't exist!")).build();
        }

        CompetitionsDto competitionsDto = CompetitionsDto.builder().competitionDtoList(competitionDTOs).build();

        return ResponseEntity.ok(competitionsDto);
    }

    public CompetitionsDto findAvailableCompetitionsByFilters(
            String competitionInstrument,
            Boolean competitionOnline,
            Boolean competitionPrimaryDegree,
            Boolean competitionSecondaryDegree,
            String competitionCity
    )
    {

        List<Competition> competitionsByFilters = competitionService.findCompetitionsByFilters
                (
                        competitionInstrument,
                        competitionOnline,
                        competitionPrimaryDegree,
                        competitionSecondaryDegree,
                        competitionCity
                );

        List<CompetitionWithLocationDto> competitionDTOs = competitionsByFilters.stream()
                .filter(competition -> !competition.finished())
                .filter(competition -> OffsetDateTime.now().isBefore(competition.applicationDeadline()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        return CompetitionsDto.builder().competitionDtoList(competitionDTOs).build();
    }
    public CompetitionsDto findAvailableCompetitionsByInstrument(String competitionInstrument)
    {
        List<Competition> competitionsByInstrument
                = competitionService.findCompetitionsByInstrument(competitionInstrument);

        List<CompetitionWithLocationDto> competitionDTOs = competitionsByInstrument.stream()
                .filter(competition -> !competition.finished())
                .filter(competition -> OffsetDateTime.now().isBefore(competition.applicationDeadline()))
                .map(competitionDtoMapper::mapFromDomainToDto)
                .toList();

        return CompetitionsDto.builder().competitionDtoList(competitionDTOs).build();
    }

}
