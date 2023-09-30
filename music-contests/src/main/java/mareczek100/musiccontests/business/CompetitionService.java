package mareczek100.musiccontests.business;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.CompetitionRepositoryDAO;
import mareczek100.musiccontests.business.instrument_storage_service.dao.InstrumentDAO;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.CompetitionLocation;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompetitionService {

    private final CompetitionRepositoryDAO competitionRepositoryDAO;
    private final CompetitionLocationService competitionLocationService;
    private final InstrumentDAO instrumentDAO;

    @Transactional
    public Competition insertCompetition(Competition competition)
    {
        CompetitionLocation competitionLocation = competition.competitionLocation();
        CompetitionLocation insertedCompetitionLocations
                = competitionLocationService.insertCompetitionLocation(competitionLocation);
        return competitionRepositoryDAO.insertCompetition(competition
                .withCompetitionLocation(insertedCompetitionLocations)
                .withFinished(false));
    }
    @Transactional
    public Competition updateCompetitionAfterResults(Competition competition)
    {
        return competitionRepositoryDAO.insertCompetition(competition.withFinished(true));
    }

    @Transactional
    public List<Competition> findAllCompetitions()
    {
        return competitionRepositoryDAO.findAllCompetitions();
    }
    @Transactional
    public Page<Competition> findAllCompetitionsPageable(Integer pageNumber)
    {
        Sort sort = Sort.by("instrument").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1,5, sort);
        return competitionRepositoryDAO.findAllCompetitionsPageable(pageable);
    }

    @Transactional
    public Competition findCompetitionById(String competitionId)
    {
        return competitionRepositoryDAO.findCompetitionById(competitionId).orElseThrow(
                () -> new RuntimeException("Competition with id [%s] doesn't exist"
                        .formatted(competitionId))
        );
    }

    @Transactional
    public List<Competition> findCompetitionsByInstrument(String instrument)
    {
        return competitionRepositoryDAO.findCompetitionsByInstrument(instrument);
    }

    @Transactional
    public List<Competition> findCompetitionsByInstrumentCategory(String instrumentCategory)
    {
        List<Competition> allCompetitions = findAllCompetitions();
        Set<String> instrumentNamesSet = instrumentDAO.findInstrumentsByCategoryName(instrumentCategory).stream()
                .map(Instrument::name)
                .collect(Collectors.toSet());

        return allCompetitions.stream()
                .filter(competition -> instrumentNamesSet.contains(competition.instrument()))
                .toList();
    }

    @Transactional
    public List<Competition> findCompetitionByOnline(Boolean online)
    {
        return competitionRepositoryDAO.findCompetitionByOnline(online);
    }

    @Transactional
    public List<Competition> findCompetitionByPrimaryDegree(Boolean primaryDegree)
    {
        return competitionRepositoryDAO.findCompetitionByPrimaryDegree(primaryDegree);
    }

    @Transactional
    public List<Competition> findCompetitionBySecondaryDegree(Boolean secondaryDegree)
    {
        return competitionRepositoryDAO.findCompetitionBySecondaryDegree(secondaryDegree);
    }

    @Transactional
    public List<Competition> findCompetitionsByFilters(
            String instrument, Boolean online, Boolean primaryDegree, Boolean secondaryDegree, String locationCity)
    {
        return competitionRepositoryDAO.findCompetitionsByFilters(
                instrument, online, primaryDegree, secondaryDegree, locationCity);
    }
    @Transactional
    public void deleteCompetition(Competition competition)
    {
        competitionRepositoryDAO.deleteCompetition(competition);
    }

}