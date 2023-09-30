package mareczek100.musiccontests.business.dao;

import mareczek100.musiccontests.domain.CompetitionResult;

import java.util.List;


public interface CompetitionResultRepositoryDAO {


    CompetitionResult insertCompetitionResult(CompetitionResult competitionResult);

    List<CompetitionResult> findAllCompetitionResults();

    List<CompetitionResult> insertAllCompetitionResults(List<CompetitionResult> competitionResults);

    void deleteCompetitionResult(CompetitionResult competitionResult);
}
