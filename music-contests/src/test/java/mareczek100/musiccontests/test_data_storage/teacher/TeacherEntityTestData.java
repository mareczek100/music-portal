package mareczek100.musiccontests.test_data_storage.teacher;


import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolEntityTestData;

import java.util.List;

public class TeacherEntityTestData {
    public static TeacherEntity teacherEntityToSave1() {
        return TeacherEntity.builder()
                .teacherId(null)
                .name("name1")
                .surname("surname1")
                .email("teacher1@music-school.com")
                .pesel("12222222222")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("fortepian")
                .build();
    }

    public static TeacherEntity teacherEntitySaved1() {
        return TeacherEntity.builder()
                .teacherId("fac2f8da-d0d0-47ec-a890-6bbe3f4787f9")
                .name("name1")
                .surname("surname1")
                .email("teacher1@music-school.com")
                .pesel("$2a$12$vLrVnyg4X6iKXsH19Ah6qOpJWu5VP3UQzFOhhV/9c5Cd7Dz948ANO")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("fortepian")
                .build();
        //12222222222
    }

    public static TeacherEntity teacherEntityToSave2() {
        return TeacherEntity.builder()
                .teacherId(null)
                .name("name2")
                .surname("surname2")
                .email("teacher2@music-school.com")
                .pesel("22222222222")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("perkusja")
                .build();
    }

    public static TeacherEntity teacherEntitySaved2() {
        return TeacherEntity.builder()
                .teacherId("4fcf4bd2-cc8e-4363-b3ea-393fb203c496")
                .name("name2")
                .surname("surname2")
                .email("teacher2@music-school.com")
                .pesel("$2a$12$yy4SBwg.qgW5grrIN9dibun3PyLgL/ArNuIyk4mcLbkD/eMTOujqS")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("perkusja")
                .build();
        //22222222222
    }

    public static TeacherEntity teacherEntityToSave3() {
        return TeacherEntity.builder()
                .teacherId(null)
                .name("name3")
                .surname("surname3")
                .email("teacher3@music-school.com")
                .pesel("32222222222")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("ksylofon")
                .build();
    }

    public static TeacherEntity teacherEntitySaved3() {
        return TeacherEntity.builder()
                .teacherId("b5df280d-7a3f-4a8e-b7ed-7cc556f3481d")
                .name("name3")
                .surname("surname3")
                .email("teacher3@music-school.com")
                .pesel("$2a$12$kAcFf4iaEJF/8fFRwzW/8e18cgWOFLjerTKd8zfQtIbla/NYJrm5y")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("ksylofon")
                .build();
        //32222222222
    }

    public static TeacherEntity teacherEntityToSave4() {
        return TeacherEntity.builder()
                .teacherId(null)
                .name("name4")
                .surname("surname4")
                .email("teacher4@music-school.com")
                .pesel("42222222222")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("cymbały")
                .build();
    }

    public static TeacherEntity teacherEntitySaved4() {
        return TeacherEntity.builder()
                .teacherId("750cdfbb-5bb2-413d-8ed0-920de8a5429b")
                .name("name4")
                .surname("surname4")
                .email("teacher4@music-school.com")
                .pesel("$2a$12$xlhA1mKsr6WCD3URQzrpJO.uikc85sUqZbEQw4tGbyqKBrUTJvqJa")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("cymbały")
                .build();
        //42222222222
    }

    public static TeacherEntity teacherEntityToSave5() {
        return TeacherEntity.builder()
                .teacherId(null)
                .name("name5")
                .surname("surname5")
                .email("teacher5@music-school.com")
                .pesel("52222222222")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("gitara")
                .build();
    }

    public static TeacherEntity teacherEntitySaved5() {
        return TeacherEntity.builder()
                .teacherId("48ab5141-5b17-4c57-aaa1-8ccc94d2c0cf")
                .name("name5")
                .surname("surname5")
                .email("teacher5@music-school.com")
                .pesel("$2a$12$RGu/loqWswRuaQaVy6PZkeAKeRDupatBjVZtVj4cMiI3rcaDijRQK")
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .instrument("gitara")
                .build();
        //52222222222
    }

    public static List<TeacherEntity> teacherEntityList() {
        return List.of(
                teacherEntitySaved1(),
                teacherEntitySaved2(),
                teacherEntitySaved3(),
                teacherEntitySaved4(),
                teacherEntitySaved5()
        );
    }

}
