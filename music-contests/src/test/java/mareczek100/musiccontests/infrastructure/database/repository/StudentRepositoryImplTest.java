package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;
import mareczek100.musiccontests.infrastructure.database.entity.StudentEntity;
import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.MusicSchoolJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.StudentJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.TeacherJpaRepository;
import mareczek100.musiccontests.test_configuration.PersistenceTestConfig;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolEntityTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentEntityTestData;
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
class StudentRepositoryImplTest {

    private final StudentJpaRepository studentJpaRepository;
    private final MusicSchoolJpaRepository musicSchoolJpaRepository;
    private final TeacherJpaRepository teacherJpaRepository;
    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(studentJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(musicSchoolJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(teacherJpaRepository);
    }

    @Test
    void insertStudent() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        StudentEntity studentEntity = StudentEntityTestData.studentEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withTeacher(teacherEntitySaved);

        //when
        StudentEntity savedAndFlushedStudentEntity = studentJpaRepository.saveAndFlush(studentEntity);

        //then
        Assertions.assertThat(savedAndFlushedStudentEntity.getStudentId()).isNotNull();
        Assertions.assertThat(studentEntity).usingRecursiveComparison()
                .ignoringFields("addressId").isEqualTo( savedAndFlushedStudentEntity);
    }

    @Test
    void findAllStudents() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        StudentEntity studentEntity = StudentEntityTestData.studentEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withTeacher(teacherEntitySaved);

        //when
        StudentEntity studentEntitiesSaved = studentJpaRepository.saveAndFlush(studentEntity);
        List<StudentEntity> studentEntityList = studentJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(studentEntityList).contains(studentEntitiesSaved);
    }

    @Test
    void findStudentByPesel() {
        //given
        String studentPesel = "12345678901";
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        StudentEntity studentEntity = StudentEntityTestData.studentEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withTeacher(teacherEntitySaved)
                .withPesel(studentPesel);

        //when
        StudentEntity studentEntitySaved = studentJpaRepository.saveAndFlush(studentEntity);
        Optional<StudentEntity> studentByPesel = studentJpaRepository.findStudentByPesel(studentPesel);

        //then
        Assertions.assertThat(studentByPesel).isNotEmpty();
        Assertions.assertThat(studentByPesel.get().getPesel()).isEqualTo(studentPesel);
        Assertions.assertThat(studentEntitySaved).isEqualTo(studentByPesel.get());
    }

    @Test
    void findStudentByEmail() {
        //given
        String studentEmail = "student-email@student-email.com";
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        StudentEntity studentEntity = StudentEntityTestData.studentEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withTeacher(teacherEntitySaved)
                .withEmail(studentEmail);

        //when
        StudentEntity studentEntitySaved = studentJpaRepository.saveAndFlush(studentEntity);
        Optional<StudentEntity> studentByEmail = studentJpaRepository.findStudentByEmail(studentEmail);

        //then
        Assertions.assertThat(studentByEmail).isNotEmpty();
        Assertions.assertThat(studentByEmail.get().getEmail()).isEqualTo(studentEmail);
        Assertions.assertThat(studentEntitySaved).isEqualTo(studentByEmail.get());
    }

    @Test
    void findStudentById() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        StudentEntity studentEntity = StudentEntityTestData.studentEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withTeacher(teacherEntitySaved);

        //when
        StudentEntity studentEntitySaved
                = studentJpaRepository.saveAndFlush(studentEntity);
        String studentId = studentEntitySaved.getStudentId();
        Optional<StudentEntity> studentById = studentJpaRepository.findById(studentId);

        //then
        Assertions.assertThat(studentById).isNotEmpty();
        Assertions.assertThat(studentById.get().getStudentId()).isEqualTo(studentId);
        Assertions.assertThat(studentEntitySaved).isEqualTo(studentById.get());
    }

    @Test
    void deleteStudent() {
        //given
        MusicSchoolEntity musicSchoolEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1();
        MusicSchoolEntity musicSchoolEntitySaved1 = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntityToSave1);
        TeacherEntity teacherEntity = TeacherEntityTestData.teacherEntityToSave1().withMusicSchool(musicSchoolEntitySaved1);
        TeacherEntity teacherEntitySaved = teacherJpaRepository.saveAndFlush(teacherEntity);
        StudentEntity studentEntity = StudentEntityTestData.studentEntityToSave1()
                .withMusicSchool(musicSchoolEntitySaved1)
                .withTeacher(teacherEntitySaved);
        StudentEntity studentEntitySaved = studentJpaRepository.saveAndFlush(studentEntity);

        //when
        List<StudentEntity> studentEntityListBefore = studentJpaRepository.findAll();
        studentJpaRepository.delete(studentEntitySaved);
        List<StudentEntity> studentEntityListAfter = studentJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(studentEntityListBefore).isNotEqualTo(studentEntityListAfter);
        Assertions.assertThatCollection(studentEntityListBefore).contains(studentEntitySaved);
        Assertions.assertThatCollection(studentEntityListAfter).doesNotContain(studentEntitySaved);
    }
}