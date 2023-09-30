package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.infrastructure.database.entity.ApplicationFormEntity;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionEntity;
import mareczek100.musiccontests.infrastructure.database.entity.StudentEntity;
import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.ApplicationFormJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.CompetitionJpaRepository;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.StudentJpaRepository;
import mareczek100.musiccontests.test_configuration.PersistenceTestConfig;
import mareczek100.musiccontests.test_data_storage.application_form.ApplicationFormEntityTestData;
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
class ApplicationFormRepositoryImplTest {

    private final ApplicationFormJpaRepository applicationFormJpaRepository;
    private final CompetitionJpaRepository competitionJpaRepository;
    private final StudentJpaRepository studentJpaRepository;
    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(applicationFormJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(competitionJpaRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(studentJpaRepository);
    }

    @Test
    void insertApplicationForm() {
        //given
        CompetitionEntity competitionEntitySaved = competitionJpaRepository.findAll().stream().findAny().orElseThrow();
        StudentEntity studentEntitySaved = studentJpaRepository.findAll().stream().findAny().orElseThrow();
        TeacherEntity teacherEntitySaved = studentEntitySaved.getTeacher();
        ApplicationFormEntity applicationFormEntityToSave1
                = ApplicationFormEntityTestData.applicationFormEntityToSave1()
                .withCompetition(competitionEntitySaved)
                .withTeacher(teacherEntitySaved)
                .withStudent(studentEntitySaved);
        ApplicationFormEntity applicationFormEntitySaved1
                = ApplicationFormEntityTestData.applicationFormEntitySaved1()
                .withCompetition(competitionEntitySaved)
                .withTeacher(teacherEntitySaved)
                .withStudent(studentEntitySaved);

        //when
        ApplicationFormEntity savedAndFlushApplicationFormEntity
                = applicationFormJpaRepository.saveAndFlush(applicationFormEntityToSave1);

        //then
        Assertions.assertThat(savedAndFlushApplicationFormEntity.getApplicationFormId()).isNotNull();
        Assertions.assertThat(applicationFormEntitySaved1).usingRecursiveComparison()
                .ignoringFields("applicationFormId").isEqualTo( savedAndFlushApplicationFormEntity);
    }

    @Test
    void findAllApplicationForms() {
        //given
        CompetitionEntity competitionEntitySaved = competitionJpaRepository.findAll().stream().findAny().orElseThrow();
        StudentEntity studentEntitySaved = studentJpaRepository.findAll().stream().findAny().orElseThrow();
        TeacherEntity teacherEntitySaved = studentEntitySaved.getTeacher();
        ApplicationFormEntity applicationFormEntityToSave1
                = ApplicationFormEntityTestData.applicationFormEntityToSave1()
                .withCompetition(competitionEntitySaved)
                .withTeacher(teacherEntitySaved)
                .withStudent(studentEntitySaved);
        ApplicationFormEntity applicationFormEntity = applicationFormJpaRepository.saveAndFlush(applicationFormEntityToSave1);

        //when
        List<ApplicationFormEntity> applicationFormEntities = applicationFormJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(applicationFormEntities).hasSizeGreaterThanOrEqualTo(1);
        Assertions.assertThatCollection(applicationFormEntities).contains(applicationFormEntity);
    }

    @Test
    void deleteApplicationForm() {
        //given
        CompetitionEntity competitionEntitySaved = competitionJpaRepository.findAll().stream().findAny().orElseThrow();
        StudentEntity studentEntitySaved = studentJpaRepository.findAll().stream().findAny().orElseThrow();
        TeacherEntity teacherEntitySaved = studentEntitySaved.getTeacher();
        ApplicationFormEntity applicationFormEntityToSave1
                = ApplicationFormEntityTestData.applicationFormEntityToSave1()
                .withCompetition(competitionEntitySaved)
                .withTeacher(teacherEntitySaved)
                .withStudent(studentEntitySaved);
        ApplicationFormEntity applicationFormEntity = applicationFormJpaRepository.saveAndFlush(applicationFormEntityToSave1);

        //when
        List<ApplicationFormEntity> applicationFormEntitiesBefore = applicationFormJpaRepository.findAll();
        applicationFormJpaRepository.delete(applicationFormEntity);
        List<ApplicationFormEntity> applicationFormEntitiesAfter = applicationFormJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(applicationFormEntitiesBefore).isNotEqualTo(applicationFormEntitiesAfter);
        Assertions.assertThatCollection(applicationFormEntitiesAfter).doesNotContain(applicationFormEntity);
    }
}