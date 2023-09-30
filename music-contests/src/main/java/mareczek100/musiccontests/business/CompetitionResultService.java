package mareczek100.musiccontests.business;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.CompetitionResultRepositoryDAO;
import mareczek100.musiccontests.domain.CompetitionResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class CompetitionResultService {

    private final CompetitionResultRepositoryDAO competitionResultRepositoryDAO;

    @Transactional
    public CompetitionResult insertCompetitionResult(CompetitionResult competitionResult)
    {
        return competitionResultRepositoryDAO.insertCompetitionResult(competitionResult);
    }
    @Transactional
    public List<CompetitionResult> insertAllCompetitionResults(List<CompetitionResult> competitionResults)
    {
        if (competitionResults.isEmpty()){
            throw new RuntimeException("There is no competition results!");
        }
        return competitionResultRepositoryDAO.insertAllCompetitionResults(competitionResults);
    }

    @Transactional
    public List<CompetitionResult> findAllCompetitionResults()
    {
        return competitionResultRepositoryDAO.findAllCompetitionResults();
    }
    @Transactional
    public void deleteCompetitionResult(CompetitionResult competitionResult)
    {
      competitionResultRepositoryDAO.deleteCompetitionResult(competitionResult);
    }
}
