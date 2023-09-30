package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;
import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.MusicSchoolJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.TeacherJpaRepository;
import mareczek100.musiccontests.test_configuration.PersistenceTestConfig;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolEntityTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherEntityTestData;
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
class TeacherRepositoryImplTest {


    private final TeacherJpaRepository teacherJpaRepository;
    private final MusicSchoolJpaRepository musicSchoolJpaRepository;
    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(teacherJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(musicSchoolJpaRepository);
    }

    @Test
    void insertTeacher() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity
                = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);

        //when
        TeacherEntity savedAndFlushedTeacherEntity = teacherJpaRepository.saveAndFlush(teacherEntity);

        //then
        Assertions.assertThat(savedAndFlushedTeacherEntity.getTeacherId()).isNotNull();
        Assertions.assertThat(teacherEntity).usingRecursiveComparison()
                .ignoringFields("addressId").isEqualTo( savedAndFlushedTeacherEntity);
    }

    @Test
    void findAllTeachers() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity
                = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);

        //when
        TeacherEntity teacherEntitiesSaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        List<TeacherEntity> teacherEntityList = teacherJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(teacherEntityList).contains(teacherEntitiesSaved);
    }

    @Test
    void findTeacherByPesel() {
        //given
        String teacherPesel = "12345678901";
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity
                = TeacherEntityTestData.teacherEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withPesel(teacherPesel);

        //when
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        Optional<TeacherEntity> teacherByPesel = teacherJpaRepository.findTeacherByPesel(teacherPesel);

        //then
        Assertions.assertThat(teacherByPesel).isNotEmpty();
        Assertions.assertThat(teacherByPesel.get().getPesel()).isEqualTo(teacherPesel);
        Assertions.assertThat(teacherEntitySaved).isEqualTo(teacherByPesel.get());
    }

    @Test
    void findTeacherByEmail() {
        //given
        String teacherEmail = "teacher-email@teacher-email.com";
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity
                = TeacherEntityTestData.teacherEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withEmail(teacherEmail);

        //when
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        Optional<TeacherEntity> teacherByEmail = teacherJpaRepository.findTeacherByEmail(teacherEmail);

        //then
        Assertions.assertThat(teacherByEmail).isNotEmpty();
        Assertions.assertThat(teacherByEmail.get().getEmail()).isEqualTo(teacherEmail);
        Assertions.assertThat(teacherEntitySaved).isEqualTo(teacherByEmail.get());
    }

    @Test
    void deleteTeacher() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity
                = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);

        //when
        List<TeacherEntity> teacherEntityListBefore = teacherJpaRepository.findAll();
        teacherJpaRepository.delete(teacherEntitySaved);
        List<TeacherEntity> teacherEntityListAfter = teacherJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(teacherEntityListBefore).isNotEqualTo(teacherEntityListAfter);
        Assertions.assertThatCollection(teacherEntityListBefore).contains(teacherEntitySaved);
        Assertions.assertThatCollection(teacherEntityListAfter).doesNotContain(teacherEntitySaved);
    }
}