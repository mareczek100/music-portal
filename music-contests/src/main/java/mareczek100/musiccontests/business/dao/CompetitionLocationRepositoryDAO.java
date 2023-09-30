package mareczek100.musiccontests.business.dao;

import mareczek100.musiccontests.domain.CompetitionLocation;

import java.util.List;


public interface CompetitionLocationRepositoryDAO {
    CompetitionLocation insertCompetitionLocation(CompetitionLocation competitionLocation);

    List<CompetitionLocation> findAllCompetitionLocations();
}



