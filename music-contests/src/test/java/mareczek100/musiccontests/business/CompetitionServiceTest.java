package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.CompetitionRepositoryDAO;
import mareczek100.musiccontests.business.instrument_storage_service.dao.InstrumentDAO;
import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.CompetitionLocation;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDomainTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class CompetitionServiceTest {

    @Mock
    private CompetitionRepositoryDAO competitionRepositoryDAO;
    @Mock
    private InstrumentDAO instrumentDAO;
    @Mock
    private CompetitionLocationService competitionLocationService;
    @InjectMocks
    private CompetitionService competitionService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(competitionRepositoryDAO);
        Assertions.assertNotNull(instrumentDAO);
        Assertions.assertNotNull(competitionLocationService);
        Assertions.assertNotNull(competitionService);
    }

    @Test
    void insertCompetition() {
        //given
        Competition competitionToSave = CompetitionDomainTestData.competitionAtOrganizerSchoolToSave1();
        Competition competitionSaved = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();

        //when
        Mockito.when(competitionLocationService.insertCompetitionLocation(
                competitionToSave.competitionLocation())).thenReturn(competitionSaved.competitionLocation());
        Mockito.when(competitionRepositoryDAO.insertCompetition(competitionToSave))
                .thenReturn(competitionSaved);
        Competition insertedCompetition = competitionService.insertCompetition(competitionToSave);

        //then
        Assertions.assertEquals(insertedCompetition, competitionSaved);
    }

    @Test
    void updateCompetitionAfterResults() {
        //given
        Competition competitionToUpdate = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        Competition competitionSaved
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().withFinished(true);

        //when
        Mockito.when(competitionRepositoryDAO.insertCompetition(
                competitionToUpdate.withFinished(true))).thenReturn(competitionSaved);
        Competition insertedCompetition = competitionService.updateCompetitionAfterResults(competitionToUpdate);

        //then
        Assertions.assertEquals(insertedCompetition, competitionSaved);
    }

    @Test
    void findAllCompetitions() {
        //given
        Competition competitionSaved1 = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        Competition competitionSaved2 = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved2();
        Competition competitionSaved3 = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved3();
        List<Competition> competitionsSaved
                = List.of(competitionSaved1, competitionSaved2, competitionSaved3);

        //when
        Mockito.when(competitionRepositoryDAO.findAllCompetitions()).thenReturn(competitionsSaved);
        List<Competition> competitionList = competitionService.findAllCompetitions();

        //then
        Assertions.assertEquals(competitionList, competitionsSaved);
    }

    @Test
    void findCompetitionById() {
        //given
        Competition competitionSaved1 = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1();
        String competitionId = competitionSaved1.competitionId();

        //when
        Mockito.when(competitionRepositoryDAO.findCompetitionById(competitionId))
                .thenReturn(Optional.of(competitionSaved1));
        Competition competitionById = competitionService.findCompetitionById(competitionId);

        //then
        Assertions.assertEquals(competitionById, competitionSaved1);
    }

    @Test
    void findCompetitionsByInstrument() {
        //given
        String instrument = "testInstrument";
        Competition competitionSaved1
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().withInstrument(instrument);
        Competition competitionSaved2
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved2().withInstrument(instrument);
        Competition competitionSaved3
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved3().withInstrument(instrument);
        List<Competition> competitionsSaved
                = List.of(competitionSaved1, competitionSaved2, competitionSaved3);

        //when
        Mockito.when(competitionRepositoryDAO.findCompetitionsByInstrument(instrument))
                .thenReturn(competitionsSaved);
        List<Competition> competitionsByInstrument = competitionService.findCompetitionsByInstrument(instrument);

        //then
        Assertions.assertEquals(competitionsByInstrument, competitionsSaved);
    }

    @Test
    void findCompetitionByInstrumentCategory() {
        //given
        Instrument instrument1 = InstrumentDomainTestData.instrumentSaved1();
        Instrument instrument2 = InstrumentDomainTestData.instrumentSaved2();
        Instrument instrument3 = InstrumentDomainTestData.instrumentSaved3();
        String instrumentCategory = instrument1.category().instrumentCategory();
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList().stream()
                .filter(instrument -> instrumentCategory.equals(instrument.category().instrumentCategory()))
                .toList();
        Set<String> instrumentNameList = instrumentList.stream()
                .map(Instrument::name).collect(Collectors.toSet());

        Competition competitionSaved1
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1()
                .withInstrument(instrument1.name());
        Competition competitionSaved2
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved2()
                .withInstrument(instrument2.name());
        Competition competitionSaved3
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved3()
                .withInstrument(instrument3.name());
        List<Competition> competitionsSaved
                = List.of(competitionSaved1, competitionSaved2, competitionSaved3);

        //when
        Mockito.when(instrumentDAO.findInstrumentsByCategoryName(instrumentCategory))
                .thenReturn(instrumentList);
        Mockito.when(competitionRepositoryDAO.findAllCompetitions()).thenReturn(competitionsSaved);
        List<Competition> competitionsByInstrumentCategory
                = competitionService.findCompetitionsByInstrumentCategory(instrumentCategory);

        //then
        org.assertj.core.api.Assertions.assertThatCollection(competitionsByInstrumentCategory)
                .isNotEqualTo(competitionsSaved);
        org.assertj.core.api.Assertions.assertThatCollection(competitionsByInstrumentCategory)
                .contains(competitionSaved1);
        org.assertj.core.api.Assertions.assertThatCollection(competitionsByInstrumentCategory)
                .contains(competitionSaved2);
        org.assertj.core.api.Assertions.assertThatCollection(competitionsByInstrumentCategory)
                .doesNotContain(competitionSaved3);
        org.assertj.core.api.Assertions.assertThatCollection(instrumentNameList)
                .containsOnly(competitionsByInstrumentCategory.get(0).instrument(),
                        competitionsByInstrumentCategory.get(1).instrument());
    }

    @Test
    void findCompetitionByOnline() {
        //given
        Boolean online = true;
        Competition competitionSaved1
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().withOnline(online);
        Competition competitionSaved2
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved2().withOnline(online);
        Competition competitionSaved3
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved3().withOnline(online);
        List<Competition> competitionsSaved
                = List.of(competitionSaved1, competitionSaved2, competitionSaved3);

        //when
        Mockito.when(competitionRepositoryDAO.findCompetitionByOnline(online))
                .thenReturn(competitionsSaved);
        List<Competition> competitionByOnline = competitionService.findCompetitionByOnline(online);

        //then
        Assertions.assertEquals(competitionByOnline, competitionsSaved);
    }

    @Test
    void findCompetitionByPrimaryDegree() {
        //given
        Boolean primaryDegree = true;
        Competition competitionSaved1
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().withPrimaryDegree(primaryDegree);
        Competition competitionSaved2
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved2().withPrimaryDegree(primaryDegree);
        Competition competitionSaved3
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved3().withPrimaryDegree(primaryDegree);
        List<Competition> competitionsSaved
                = List.of(competitionSaved1, competitionSaved2, competitionSaved3);

        //when
        Mockito.when(competitionRepositoryDAO.findCompetitionByPrimaryDegree(primaryDegree))
                .thenReturn(competitionsSaved);
        List<Competition> competitionByPrimaryDegree = competitionService.findCompetitionByPrimaryDegree(primaryDegree);

        //then
        Assertions.assertEquals(competitionByPrimaryDegree, competitionsSaved);
    }

    @Test
    void findCompetitionBySecondaryDegree() {
        //given
        Boolean secondaryDegree = true;
        Competition competitionSaved1
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().withSecondaryDegree(secondaryDegree);
        Competition competitionSaved2
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved2().withSecondaryDegree(secondaryDegree);
        Competition competitionSaved3
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved3().withSecondaryDegree(secondaryDegree);
        List<Competition> competitionsSaved
                = List.of(competitionSaved1, competitionSaved2, competitionSaved3);

        //when
        Mockito.when(competitionRepositoryDAO.findCompetitionBySecondaryDegree(secondaryDegree))
                .thenReturn(competitionsSaved);
        List<Competition> competitionBySecondaryDegree = competitionService.findCompetitionBySecondaryDegree(secondaryDegree);

        //then
        Assertions.assertEquals(competitionBySecondaryDegree, competitionsSaved);
    }

    @Test
    void findCompetitionsByFilters() {
        //given
        CompetitionLocation competitionLocation
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1().competitionLocation();
        Address address = competitionLocation.address();
        String instrument = "testInstrument";
        Boolean online = true;
        Boolean primaryDegree = true;
        Boolean secondaryDegree = true;
        String competitionCity = "testCity";

        Competition competitionSaved1
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1()
                .withInstrument(instrument)
                .withOnline(online)
                .withPrimaryDegree(primaryDegree)
                .withSecondaryDegree(secondaryDegree)
                .withCompetitionLocation(competitionLocation
                        .withAddress(address.withCity(competitionCity)));
        Competition competitionSaved2
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved2()
                .withInstrument(instrument)
                .withOnline(online)
                .withPrimaryDegree(primaryDegree)
                .withSecondaryDegree(secondaryDegree)
                .withCompetitionLocation(competitionLocation
                        .withAddress(address.withCity(competitionCity)));
        Competition competitionSaved3
                = CompetitionDomainTestData.competitionAtOrganizerSchoolSaved3()
                .withInstrument(instrument)
                .withOnline(online)
                .withPrimaryDegree(primaryDegree)
                .withSecondaryDegree(secondaryDegree)
                .withCompetitionLocation(competitionLocation
                        .withAddress(address.withCity(competitionCity)));
        List<Competition> competitionsSaved
                = List.of(competitionSaved1, competitionSaved2, competitionSaved3);

        //when
        Mockito.when(competitionRepositoryDAO.findCompetitionsByFilters(
                instrument, online, primaryDegree, secondaryDegree, competitionCity))
                .thenReturn(competitionsSaved);
        List<Competition> competitionsByFilters = competitionService.findCompetitionsByFilters(
                instrument, online, primaryDegree, secondaryDegree, competitionCity);

        //then
        Assertions.assertEquals(competitionsByFilters, competitionsSaved);
    }

    @Test
    void deleteCompetitionResult() {
        //given
        Competition competitionSaved1
                = CompetitionDomainTestData.competitionAtOtherLocationSaved1();

        //when
        competitionService.deleteCompetition(competitionSaved1);

        //then
        Mockito.verify(competitionRepositoryDAO).deleteCompetition(competitionSaved1);
    }
}