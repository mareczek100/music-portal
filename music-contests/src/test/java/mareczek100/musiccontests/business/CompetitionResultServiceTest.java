package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.CompetitionResultRepositoryDAO;
import mareczek100.musiccontests.domain.CompetitionResult;
import mareczek100.musiccontests.test_data_storage.competition_results.CompetitionResultDomainTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CompetitionResultServiceTest {

    @Mock
    private CompetitionResultRepositoryDAO competitionResultRepositoryDAO;
    @InjectMocks
    private CompetitionResultService competitionResultService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(competitionResultRepositoryDAO);
        Assertions.assertNotNull(competitionResultService);
    }

    @Test
    void insertCompetitionResult() {
        //given
        CompetitionResult competitionResultToSave
                = CompetitionResultDomainTestData.competitionResultToSave1();
        CompetitionResult competitionResultSaved
                = CompetitionResultDomainTestData.competitionResultSaved1();

        //when
        Mockito.when(competitionResultRepositoryDAO.insertCompetitionResult(
                competitionResultToSave)).thenReturn(competitionResultSaved);
        CompetitionResult insertedCompetitionResult
                = competitionResultService.insertCompetitionResult(competitionResultToSave);

        //then
        Assertions.assertEquals(insertedCompetitionResult, competitionResultSaved);
    }

    @Test
    void insertAllCompetitionResultsWorksCorrectly() {
        //given
        CompetitionResult competitionResultToSave1
                = CompetitionResultDomainTestData.competitionResultToSave1();
        CompetitionResult competitionResultToSave2
                = CompetitionResultDomainTestData.competitionResultToSave2();
        CompetitionResult competitionResultToSave3
                = CompetitionResultDomainTestData.competitionResultToSave3();
        List<CompetitionResult> competitionResultsToSave
                = List.of(competitionResultToSave1, competitionResultToSave2, competitionResultToSave3);

        CompetitionResult competitionResultSaved1
                = CompetitionResultDomainTestData.competitionResultSaved1();
        CompetitionResult competitionResultSaved2
                = CompetitionResultDomainTestData.competitionResultSaved2();
        CompetitionResult competitionResultSaved3
                = CompetitionResultDomainTestData.competitionResultSaved3();
        List<CompetitionResult> competitionResultsSaved
                = List.of(competitionResultSaved1, competitionResultSaved2, competitionResultSaved3);

        //when
        Mockito.when(competitionResultRepositoryDAO.insertAllCompetitionResults(
                competitionResultsToSave)).thenReturn(competitionResultsSaved);
        List<CompetitionResult> insertedCompetitionResult
                = competitionResultService.insertAllCompetitionResults(competitionResultsToSave);

        //then
        Assertions.assertEquals(insertedCompetitionResult, competitionResultsSaved);
    }

    @Test
    void insertAllCompetitionResultsThrowExceptionIfThereAreNoResultsToInsert() {
        //given
        List<CompetitionResult> competitionResultsToInsert = Collections.emptyList();
        String exceptionMessage = "There is no competition results!";

        //when
        Executable exception = () -> competitionResultService.insertAllCompetitionResults(competitionResultsToInsert);

        //then
        Assertions.assertThrowsExactly(RuntimeException.class, exception, exceptionMessage);
    }

    @Test
    void findAllCompetitionResultsWorksCorrectly() {
        //given
        CompetitionResult competitionResultSaved1
                = CompetitionResultDomainTestData.competitionResultSaved1();
        CompetitionResult competitionResultSaved2
                = CompetitionResultDomainTestData.competitionResultSaved2();
        CompetitionResult competitionResultSaved3
                = CompetitionResultDomainTestData.competitionResultSaved3();
        List<CompetitionResult> competitionResultsSaved
                = List.of(competitionResultSaved1, competitionResultSaved2, competitionResultSaved3);

        //when
        Mockito.when(competitionResultRepositoryDAO.findAllCompetitionResults())
                .thenReturn(competitionResultsSaved);
        List<CompetitionResult> competitionResultList = competitionResultService.findAllCompetitionResults();

        //then
        Assertions.assertEquals(competitionResultList, competitionResultsSaved);
    }

    @Test
    void deleteCompetitionResult() {
        //given
        CompetitionResult competitionResultSaved1
                = CompetitionResultDomainTestData.competitionResultSaved1();

        //when
        competitionResultService.deleteCompetitionResult(competitionResultSaved1);

        //then
        Mockito.verify(competitionResultRepositoryDAO).deleteCompetitionResult(competitionResultSaved1);
    }
}