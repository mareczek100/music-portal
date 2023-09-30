package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.MusicSchoolJpaRepository;
import mareczek100.musiccontests.test_configuration.PersistenceTestConfig;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolEntityTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceTestConfig.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MusicSchoolRepositoryImplTest {

    private final MusicSchoolJpaRepository musicSchoolJpaRepository;
    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(musicSchoolJpaRepository);
    }

    @Test
    void insertMusicSchool() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = MusicSchoolEntityTestData.musicSchoolEntitySaved1();

        //when
        MusicSchoolEntity musicSchoolEntity = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);

        //then
        Assertions.assertThat(musicSchoolEntity.getMusicSchoolId()).isNotNull();
        Assertions.assertThat(musicSchoolEntitySaved1).usingRecursiveComparison()
                .ignoringFields("musicSchoolId", "address.addressId")
                .isEqualTo( musicSchoolEntity);
    }

    @Test
    void findAllMusicSchools() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntityToSave2 = MusicSchoolEntityTestData.musicSchoolEntityToSave2();
        MusicSchoolEntity musicSchoolEntityToSave3 = MusicSchoolEntityTestData.musicSchoolEntityToSave3();
        List<MusicSchoolEntity> musicSchoolEntitiesToSave
                = List.of(musicSchoolEntityToSave1, musicSchoolEntityToSave2, musicSchoolEntityToSave3);

        //when
        List<MusicSchoolEntity> musicSchoolEntitiesSaved = musicSchoolJpaRepository.saveAllAndFlush(musicSchoolEntitiesToSave);
        List<MusicSchoolEntity> musicSchoolEntityList = musicSchoolJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(musicSchoolEntityList).containsAll(musicSchoolEntitiesSaved);
    }

    @Test
    void findMusicSchoolByPatron() {
        //given
        String patron = "Patron Szkoły";
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();

        //when
        MusicSchoolEntity musicSchoolEntity
                = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1.withPatron(patron));
        Optional<MusicSchoolEntity> musicSchoolByPatron = musicSchoolJpaRepository.findMusicSchoolByPatron(patron);

        //then
        Assertions.assertThat(musicSchoolByPatron).isNotEmpty();
        Assertions.assertThat(musicSchoolByPatron.get().getPatron()).isEqualTo(patron);
        Assertions.assertThat(musicSchoolEntity).isEqualTo(musicSchoolByPatron.get());
    }

    @Test
    void findMusicSchoolById() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();

        //when
        MusicSchoolEntity musicSchoolEntity
                = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        String musicSchoolId = musicSchoolEntity.getMusicSchoolId();
        Optional<MusicSchoolEntity> musicSchoolById = musicSchoolJpaRepository.findById(musicSchoolId);

        //then
        Assertions.assertThat(musicSchoolById).isNotEmpty();
        Assertions.assertThat(musicSchoolById.get().getMusicSchoolId()).isEqualTo(musicSchoolId);
        Assertions.assertThat(musicSchoolEntity).isEqualTo(musicSchoolById.get());
    }
}