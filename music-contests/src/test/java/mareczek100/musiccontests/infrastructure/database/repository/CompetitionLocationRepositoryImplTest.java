package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionLocationEntity;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.AddressJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.CompetitionLocationJpaRepository;
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
class CompetitionLocationRepositoryImplTest {

    private final CompetitionLocationJpaRepository competitionLocationJpaRepository;
    private final AddressJpaRepository addressJpaRepository;
    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(competitionLocationJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(addressJpaRepository);
    }

    @Test
    void insertCompetitionLocation() {
        //given
        CompetitionLocationEntity competitionLocation
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        CompetitionLocationEntity competitionLocationEntity
                = competitionLocationJpaRepository.saveAndFlush(competitionLocation);

        //then
        Assertions.assertThat(competitionLocationEntity.getCompetitionLocationId()).isNotNull();
        Assertions.assertThat (competitionLocation).usingRecursiveComparison()
                .ignoringFields("competitionLocationId").isEqualTo( competitionLocationEntity);
    }

    @Test
    void findAllCompetitionLocations() {
        //given
        CompetitionLocationEntity competitionLocation
                = CompetitionEntityTestData.competitionAtOtherLocationEntityToSave1().getCompetitionLocation();
        AddressEntity addressEntity = competitionLocation.getAddress();

        //when
        addressJpaRepository.saveAndFlush(addressEntity);
        CompetitionLocationEntity competitionLocationEntity
                = competitionLocationJpaRepository.saveAndFlush(competitionLocation);
        List<CompetitionLocationEntity> locationEntities = competitionLocationJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(locationEntities).hasSizeGreaterThanOrEqualTo(1);
        Assertions.assertThatCollection(locationEntities).contains(competitionLocationEntity);

    }
}