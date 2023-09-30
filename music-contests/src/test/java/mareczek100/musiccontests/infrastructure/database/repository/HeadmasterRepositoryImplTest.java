package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.infrastructure.database.entity.HeadmasterEntity;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.HeadmasterJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.MusicSchoolJpaRepository;
import mareczek100.musiccontests.test_configuration.PersistenceTestConfig;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterEntityTestData;
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
class HeadmasterRepositoryImplTest {

    private final HeadmasterJpaRepository headmasterJpaRepository;
    private final MusicSchoolJpaRepository musicSchoolJpaRepository;
    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(headmasterJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(musicSchoolJpaRepository);
    }

    @Test
    void insertHeadmaster() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        HeadmasterEntity headmasterEntity
                = HeadmasterEntityTestData.headmasterEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);

        //when
        HeadmasterEntity savedAndFlushedHeadmasterEntity = headmasterJpaRepository.saveAndFlush(headmasterEntity);

        //then
        Assertions.assertThat(savedAndFlushedHeadmasterEntity.getHeadmasterId()).isNotNull();
        Assertions.assertThat(headmasterEntity).usingRecursiveComparison()
                .ignoringFields("addressId").isEqualTo( savedAndFlushedHeadmasterEntity);
    }

    @Test
    void findAllHeadmasters() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        HeadmasterEntity headmasterEntity
                = HeadmasterEntityTestData.headmasterEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);

        //when
        HeadmasterEntity headmasterEntitiesSaved = headmasterJpaRepository.saveAndFlush(headmasterEntity);
        List<HeadmasterEntity> headmasterEntityList = headmasterJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(headmasterEntityList).contains(headmasterEntitiesSaved);
    }

    @Test
    void findHeadmasterByEmail() {
        //given
        String headmasterEmail = "headmaster-email@headmaster-email.com";
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        HeadmasterEntity headmasterEntity
                = HeadmasterEntityTestData.headmasterEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withEmail(headmasterEmail);

        //when
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.saveAndFlush(headmasterEntity);
        Optional<HeadmasterEntity> headmasterByEmail = headmasterJpaRepository.findHeadmasterByEmail(headmasterEmail);

        //then
        Assertions.assertThat(headmasterByEmail).isNotEmpty();
        Assertions.assertThat(headmasterByEmail.get().getEmail()).isEqualTo(headmasterEmail);
        Assertions.assertThat(headmasterEntitySaved).isEqualTo(headmasterByEmail.get());
    }

    @Test
    void findHeadmasterByPesel() {
        //given
        String headmasterPesel = "12345678901";
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        HeadmasterEntity headmasterEntity
                = HeadmasterEntityTestData.headmasterEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withPesel(headmasterPesel);

        //when
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.saveAndFlush(headmasterEntity);
        Optional<HeadmasterEntity> headmasterByPesel = headmasterJpaRepository.findHeadmasterByPesel(headmasterPesel);

        //then
        Assertions.assertThat(headmasterByPesel).isNotEmpty();
        Assertions.assertThat(headmasterByPesel.get().getPesel()).isEqualTo(headmasterPesel);
        Assertions.assertThat(headmasterEntitySaved).isEqualTo(headmasterByPesel.get());
    }

    @Test
    void deleteHeadmaster() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        HeadmasterEntity headmasterEntity
                = HeadmasterEntityTestData.headmasterEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);
        HeadmasterEntity headmasterEntitySaved = headmasterJpaRepository.saveAndFlush(headmasterEntity);

        //when
        List<HeadmasterEntity> headmasterEntityListBefore = headmasterJpaRepository.findAll();
        headmasterJpaRepository.delete(headmasterEntitySaved);
        List<HeadmasterEntity> headmasterEntityListAfter = headmasterJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(headmasterEntityListBefore).isNotEqualTo(headmasterEntityListAfter);
        Assertions.assertThatCollection(headmasterEntityListBefore).contains(headmasterEntitySaved);
        Assertions.assertThatCollection(headmasterEntityListAfter).doesNotContain(headmasterEntitySaved);
    }
}