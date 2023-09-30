package mareczek100.musiccontests.business.dao;

import mareczek100.musiccontests.domain.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface CompetitionRepositoryDAO {

    Competition insertCompetition(Competition competition);

    List<Competition> findAllCompetitions();

    Page<Competition> findAllCompetitionsPageable(Pageable pageable);

    List<Competition> findCompetitionsByInstrument(String instrument);

    List<Competition> findCompetitionByOnline(Boolean online);

    List<Competition> findCompetitionByPrimaryDegree(Boolean primaryDegree);

    List<Competition> findCompetitionBySecondaryDegree(Boolean secondaryDegree);

    List<Competition> findCompetitionsByFilters(
            String instrument, Boolean online, Boolean primaryDegree, Boolean secondaryDegree, String locationCity);

    Optional<Competition> findCompetitionById(String competitionId);

    void deleteCompetition(Competition competition);
}
