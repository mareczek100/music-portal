package mareczek100.musiccontests.test_data_storage.teacher;


import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDomainTestData;

import java.util.List;

public class TeacherDomainTestData {
    public static Teacher teacherToSave1() {
        return Teacher.builder()
                .teacherId(null)
                .name("name1")
                .surname("surname1")
                .email("teacher1@music-school.com")
                .pesel("12222222222")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("fortepian")
                .build();
    }

    public static Teacher teacherSaved1() {
        return Teacher.builder()
                .teacherId("fac2f8da-d0d0-47ec-a890-6bbe3f4787f9")
                .name("name1")
                .surname("surname1")
                .email("teacher1@music-school.com")
                .pesel("$2a$12$vLrVnyg4X6iKXsH19Ah6qOpJWu5VP3UQzFOhhV/9c5Cd7Dz948ANO")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("fortepian")
                .build();
        //12222222222
    }

    public static Teacher teacherToSave2() {
        return Teacher.builder()
                .teacherId(null)
                .name("name2")
                .surname("surname2")
                .email("teacher2@music-school.com")
                .pesel("22222222222")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("perkusja")
                .build();
    }

    public static Teacher teacherSaved2() {
        return Teacher.builder()
                .teacherId("4fcf4bd2-cc8e-4363-b3ea-393fb203c496")
                .name("name2")
                .surname("surname2")
                .email("teacher2@music-school.com")
                .pesel("$2a$12$yy4SBwg.qgW5grrIN9dibun3PyLgL/ArNuIyk4mcLbkD/eMTOujqS")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("perkusja")
                .build();
        //22222222222
    }

    public static Teacher teacherToSave3() {
        return Teacher.builder()
                .teacherId(null)
                .name("name3")
                .surname("surname3")
                .email("teacher3@music-school.com")
                .pesel("32222222222")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("ksylofon")
                .build();
    }

    public static Teacher teacherSaved3() {
        return Teacher.builder()
                .teacherId("b5df280d-7a3f-4a8e-b7ed-7cc556f3481d")
                .name("name3")
                .surname("surname3")
                .email("teacher3@music-school.com")
                .pesel("$2a$12$kAcFf4iaEJF/8fFRwzW/8e18cgWOFLjerTKd8zfQtIbla/NYJrm5y")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("ksylofon")
                .build();
        //32222222222
    }

    public static Teacher teacherToSave4() {
        return Teacher.builder()
                .teacherId(null)
                .name("name4")
                .surname("surname4")
                .email("teacher4@music-school.com")
                .pesel("42222222222")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("cymbały")
                .build();
    }

    public static Teacher teacherSaved4() {
        return Teacher.builder()
                .teacherId("750cdfbb-5bb2-413d-8ed0-920de8a5429b")
                .name("name4")
                .surname("surname4")
                .email("teacher4@music-school.com")
                .pesel("$2a$12$xlhA1mKsr6WCD3URQzrpJO.uikc85sUqZbEQw4tGbyqKBrUTJvqJa")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("cymbały")
                .build();
        //42222222222
    }

    public static Teacher teacherToSave5() {
        return Teacher.builder()
                .teacherId(null)
                .name("name5")
                .surname("surname5")
                .email("teacher5@music-school.com")
                .pesel("52222222222")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("gitara")
                .build();
    }

    public static Teacher teacherSaved5() {
        return Teacher.builder()
                .teacherId("48ab5141-5b17-4c57-aaa1-8ccc94d2c0cf")
                .name("name5")
                .surname("surname5")
                .email("teacher5@music-school.com")
                .pesel("$2a$12$RGu/loqWswRuaQaVy6PZkeAKeRDupatBjVZtVj4cMiI3rcaDijRQK")
                .password("TeacherPassword")
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .instrument("gitara")
                .build();
        //52222222222
    }

    public static List<Teacher> teacherList() {
        return List.of(
                teacherSaved1(),
                teacherSaved2(),
                teacherSaved3(),
                teacherSaved4(),
                teacherSaved5()
        );
    }

}
