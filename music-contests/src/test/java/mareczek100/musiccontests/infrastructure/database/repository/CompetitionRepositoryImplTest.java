package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionEntity;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionLocationEntity;
import mareczek100.musiccontests.infrastructure.database.entity.HeadmasterEntity;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.AddressJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.CompetitionJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.CompetitionLocationJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.HeadmasterJpaRepository;
import mareczek100.musiccontests.test_configuration.PersistenceTestConfig;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionEntityTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceTestConfig.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CompetitionRepositoryImplTest {

    private final CompetitionJpaRepository competitionJpaRepository;
    private final CompetitionLocationJpaRepository competitionLocationJpaRepository;
    private final AddressJpaRepository addressJpaRepository;
    private final HeadmasterJpaRepository headmasterJpaRepository;
    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(competitionJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(competitionLocationJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(addressJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(headmasterJpaRepository);
    }

    @Test
    void insertCompetition() {
        //given
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.findAll().stream().findAny().orElseThrow();
        CompetitionEntity competitionEntity
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().withHeadmaster(headmasterEntitySaved);
        CompetitionLocationEntity competitionLocation = competitionEntity.getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        CompetitionEntity competitionEntitySaved = competitionJpaRepository.saveAndFlush(competitionEntity);

        //then
        Assertions.assertThat(competitionEntitySaved.getCompetitionId()).isNotNull();
        Assertions.assertThat(competitionEntitySaved.getCompetitionLocation().getCompetitionLocationId()).isNotNull();
        Assertions.assertThat (competitionEntity).usingRecursiveComparison()
                .ignoringFields("competitionId").isEqualTo( competitionEntitySaved);
    }

    @Test
    void findAllCompetitions() {
        //given
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.findAll().stream().findAny().orElseThrow();
        CompetitionEntity competitionEntity
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().withHeadmaster(headmasterEntitySaved);
        CompetitionLocationEntity competitionLocation = competitionEntity.getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        CompetitionEntity competitionEntitySaved = competitionJpaRepository.saveAndFlush(competitionEntity);
        List<CompetitionEntity> competitionEntities = competitionJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(competitionEntities).hasSizeGreaterThanOrEqualTo(1);
        Assertions.assertThatCollection(competitionEntities).contains(competitionEntitySaved);
    }

    @Test
    void findCompetitionsByInstrument() {
        //given
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.findAll().stream().findAny().orElseThrow();
        CompetitionEntity competitionEntity
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().withHeadmaster(headmasterEntitySaved);
        CompetitionLocationEntity competitionLocation = competitionEntity.getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();
        String instrument = "fortepian";

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        CompetitionEntity competitionEntitySaved
                = competitionJpaRepository.saveAndFlush(competitionEntity.withInstrument(instrument));
        List<CompetitionEntity> competitionsByInstrument = competitionJpaRepository.findCompetitionByInstrument(instrument);
        List<String> instrumentList = competitionsByInstrument.stream()
                .map(CompetitionEntity::getInstrument)
                .toList();

        //then
        Assertions.assertThatCollection(instrumentList).containsOnly(instrument);
        Assertions.assertThatCollection(competitionsByInstrument).contains(competitionEntitySaved);
    }

    @Test
    void findCompetitionsByOnline() {
        //given
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.findAll().stream().findAny().orElseThrow();
        CompetitionEntity competitionEntity
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().withHeadmaster(headmasterEntitySaved);
        CompetitionLocationEntity competitionLocation = competitionEntity.getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();
        Boolean online = true;

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        CompetitionEntity competitionEntitySaved
                = competitionJpaRepository.saveAndFlush(competitionEntity.withOnline(online));
        List<CompetitionEntity> competitionByOnline = competitionJpaRepository.findCompetitionByOnline(online);
        List<Boolean> onlineList = competitionByOnline.stream()
                .map(CompetitionEntity::getOnline)
                .toList();

        //then
        Assertions.assertThatCollection(onlineList).containsOnly(online);
        Assertions.assertThatCollection(competitionByOnline).contains(competitionEntitySaved);
    }

    @Test
    void findCompetitionsByPrimaryDegree() {
        //given
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.findAll().stream().findAny().orElseThrow();
        CompetitionEntity competitionEntity
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().withHeadmaster(headmasterEntitySaved);
        CompetitionLocationEntity competitionLocation = competitionEntity.getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();
        Boolean primaryDegree = true;

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        CompetitionEntity competitionEntitySaved
                = competitionJpaRepository.saveAndFlush(competitionEntity.withPrimaryDegree(primaryDegree));
        List<CompetitionEntity> competitionByPrimaryDegree = competitionJpaRepository.findCompetitionByPrimaryDegree(primaryDegree);
        List<Boolean> primaryDegreeList = competitionByPrimaryDegree.stream()
                .map(CompetitionEntity::getPrimaryDegree)
                .toList();

        //then
        Assertions.assertThatCollection(primaryDegreeList).containsOnly(primaryDegree);
        Assertions.assertThatCollection(competitionByPrimaryDegree).contains(competitionEntitySaved);
    }

    @Test
    void findCompetitionsBySecondaryDegree() {
        //given
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.findAll().stream().findAny().orElseThrow();
        CompetitionEntity competitionEntity
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().withHeadmaster(headmasterEntitySaved);
        CompetitionLocationEntity competitionLocation = competitionEntity.getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();
        Boolean secondaryDegree = true;

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        CompetitionEntity competitionEntitySaved
                = competitionJpaRepository.saveAndFlush(competitionEntity.withSecondaryDegree(secondaryDegree));
        List<CompetitionEntity> competitionBySecondaryDegree = competitionJpaRepository.findCompetitionBySecondaryDegree(secondaryDegree);
        List<Boolean> secondaryDegreeList = competitionBySecondaryDegree.stream()
                .map(CompetitionEntity::getSecondaryDegree)
                .toList();

        //then
        Assertions.assertThatCollection(secondaryDegreeList).containsOnly(secondaryDegree);
        Assertions.assertThatCollection(competitionBySecondaryDegree).contains(competitionEntitySaved);
    }

    @Test
    void findCompetitionsByFilters() {
        //given
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.findAll().stream().findAny().orElseThrow();
        CompetitionEntity competitionEntity
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().withHeadmaster(headmasterEntitySaved);
        CompetitionLocationEntity competitionLocation = competitionEntity.getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();
        String instrument = "fortepian";
        Boolean online = true;
        Boolean primaryDegree = true;
        Boolean secondaryDegree = true;

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        CompetitionEntity competitionEntitySaved
                = competitionJpaRepository.saveAndFlush(competitionEntity
                .withInstrument(instrument)
                .withOnline(online)
                .withPrimaryDegree(primaryDegree)
                .withSecondaryDegree(secondaryDegree));

        List<CompetitionEntity> competitionsByFilters = competitionJpaRepository.findCompetitionsByFilters(
                instrument, online, primaryDegree, secondaryDegree);

        //then
        Assertions.assertThatCollection(competitionsByFilters).hasSizeGreaterThanOrEqualTo(1);
        Assertions.assertThatCollection(competitionsByFilters).contains(competitionEntitySaved);
    }

    @Test
    void findCompetitionsById() {
        //given
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.findAll().stream().findAny().orElseThrow();
        CompetitionEntity competitionEntity
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().withHeadmaster(headmasterEntitySaved);
        CompetitionLocationEntity competitionLocation = competitionEntity.getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        CompetitionEntity competitionEntitySaved
                = competitionJpaRepository.saveAndFlush(competitionEntity);
        String competitionId = competitionEntitySaved.getCompetitionId();
        CompetitionEntity competitionById
                = competitionJpaRepository.findById(competitionId).orElse(null);

        //then
        Assertions.assertThat(competitionById).isEqualTo(competitionEntitySaved);
    }
}