package mareczek100.musiccontests.test_data_storage.headmaster;


import mareczek100.musiccontests.infrastructure.database.entity.HeadmasterEntity;
import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolEntityTestData;

public class HeadmasterEntityTestData {
    public static HeadmasterEntity headmasterEntityToSave1() {
        return HeadmasterEntity.builder()
                .headmasterId(null)
                .name("name")
                .surname("surname")
                .email("headmaster@music-school.com")
                .pesel("11111111111")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .build();
    }
    public static HeadmasterEntity headmasterEntitySaved1() {
        return HeadmasterEntity.builder()
                .headmasterId("fac2f8da-d0d0-47ec-a890-6bbe3f4787f9")
                .name("name")
                .surname("surname")
                .email("headmaster@music-school.com")
                .pesel("$2a$12$ia8DouTyUNpjcPWUIXWG9.GVHnCMCpEe1Zgnhw8OTd3cy.QFXA/8.")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .build();
        //11111111111
    }

    public static TeacherEntity headmasterTeacherEntityToSave1() {
        return TeacherEntity.builder()
                .teacherId(null)
                .name("name")
                .surname("surname")
                .email("headmaster@music-school.com")
                .pesel("$2a$12$ia8DouTyUNpjcPWUIXWG9.GVHnCMCpEe1Zgnhw8OTd3cy.QFXA/8.")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("fagot")
                .build();
    }
    public static TeacherEntity headmasterTeacherEntitySaved1() {
        return TeacherEntity.builder()
                .teacherId("c1a9eb88-8fcc-467c-b8bc-fe17f0975c27")
                .name("name")
                .surname("surname")
                .email("headmaster@music-school.com")
                .pesel("$2a$12$ia8DouTyUNpjcPWUIXWG9.GVHnCMCpEe1Zgnhw8OTd3cy.QFXA/8.")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("fagot")
                .build();
        //11111111111
    }

}
