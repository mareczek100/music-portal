package mareczek100.musiccontests.test_data_storage.headmaster;

import mareczek100.musiccontests.api.dto.HeadmasterDto;
import mareczek100.musiccontests.api.dto.TeacherDto;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDtoTestData;

public class HeadmasterDtoTestData {
    public static HeadmasterDto headmasterDtoToSave1() {
        return HeadmasterDto.builder()
                .headmasterId(null)
                .name("name")
                .surname("surname")
                .email("headmaster@music-school.com")
                .pesel("11111111111")
                .musicSchool(MusicSchoolDtoTestData.musicSchoolDtoSaved1())
                .build();
    }
    public static HeadmasterDto headmasterDtoSaved1() {
        return HeadmasterDto.builder()
                .headmasterId("fac2f8da-d0d0-47ec-a890-6bbe3f4787f9")
                .name("name")
                .surname("surname")
                .email("headmaster@music-school.com")
                .pesel("$2a$12$ia8DouTyUNpjcPWUIXWG9.GVHnCMCpEe1Zgnhw8OTd3cy.QFXA/8.")
                .musicSchool(MusicSchoolDtoTestData.musicSchoolDtoSaved1())
                .build();
        //11111111111
    }

    public static TeacherDto headmasterTeacherDtoToSave1() {
        return TeacherDto.builder()
                .teacherId(null)
                .name("name")
                .surname("surname")
                .email("headmaster@music-school.com")
                .pesel("$2a$12$ia8DouTyUNpjcPWUIXWG9.GVHnCMCpEe1Zgnhw8OTd3cy.QFXA/8.")
                .musicSchool(MusicSchoolDtoTestData.musicSchoolDtoSaved1())
                .instrument("fagot")
                .build();
    }
    public static TeacherDto headmasterTeacherDtoSaved1() {
        return TeacherDto.builder()
                .teacherId("c1a9eb88-8fcc-467c-b8bc-fe17f0975c27")
                .name("name")
                .surname("surname")
                .email("headmaster@music-school.com")
                .pesel("$2a$12$ia8DouTyUNpjcPWUIXWG9.GVHnCMCpEe1Zgnhw8OTd3cy.QFXA/8.")
                .musicSchool(MusicSchoolDtoTestData.musicSchoolDtoSaved1())
                .instrument("fagot")
                .build();
        //11111111111
    }

}
