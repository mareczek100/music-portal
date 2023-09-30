package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.CompetitionLocationRepositoryDAO;
import mareczek100.musiccontests.domain.CompetitionLocation;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CompetitionLocationServiceTest {

    @Mock
    private CompetitionLocationRepositoryDAO competitionLocationRepositoryDAO;
    @Mock
    private AddressService addressService;
    @InjectMocks
    private CompetitionLocationService competitionLocationService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(competitionLocationRepositoryDAO);
        Assertions.assertNotNull(competitionLocationService);
        Assertions.assertNotNull(addressService);
    }

    @Test
    void insertCompetitionLocations() {
        //given
        CompetitionLocation competitionLocationToSave
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().competitionLocation();
        CompetitionLocation competitionLocationSaved
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().competitionLocation();

        //when
        Mockito.when(addressService.findAllAddresses())
                .thenReturn(List.of(competitionLocationSaved.address()));
        Mockito.when(competitionLocationRepositoryDAO.insertCompetitionLocation(
                competitionLocationToSave)).thenReturn(competitionLocationSaved);
        CompetitionLocation insertedCompetitionLocation
                = competitionLocationService.insertCompetitionLocation(competitionLocationToSave);

        //then
        Assertions.assertEquals(insertedCompetitionLocation, competitionLocationSaved);
    }

    @Test
    void findAllCompetitionLocation() {
        //given
        CompetitionLocation competitionLocationSaved1
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().competitionLocation();
        CompetitionLocation competitionLocationSaved2
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved2().competitionLocation();
        CompetitionLocation competitionLocationSaved3
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved3().competitionLocation();
        List<CompetitionLocation> competitionLocationsSaved
                = List.of(competitionLocationSaved1, competitionLocationSaved2, competitionLocationSaved3);

        //when
        Mockito.when(competitionLocationRepositoryDAO.findAllCompetitionLocations())
                .thenReturn(competitionLocationsSaved);
        List<CompetitionLocation> competitionLocationList = competitionLocationService.findAllCompetitionLocations();

        //then
        Assertions.assertEquals(competitionLocationList, competitionLocationsSaved);
    }
}