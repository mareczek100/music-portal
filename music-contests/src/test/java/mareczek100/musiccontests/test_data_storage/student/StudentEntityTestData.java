package mareczek100.musiccontests.test_data_storage.student;



import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.domain.enums.Degree;
import mareczek100.musiccontests.domain.enums.EducationProgram;
import mareczek100.musiccontests.infrastructure.database.entity.StudentEntity;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolEntityTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherEntityTestData;

import java.util.List;

public class StudentEntityTestData {
    public static StudentEntity studentEntityToSave1() {
        return StudentEntity.builder()
                .studentId(null)
                .name("name1")
                .surname("surname1")
                .email("student1@music-school.com")
                .pesel("13333333333")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
    }

    public static StudentEntity studentEntitySaved1() {
        return StudentEntity.builder()
                .studentId("90903284-5fab-4113-bf93-94e040685eb9")
                .name("name1")
                .surname("surname1")
                .email("student1@music-school.com")
                .pesel("$2a$12$/iK7.k0Weo3rpCdRlvaDouun/6nHI75KTPVMptxeg8KY3ov.vqTvC")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
        //pesel 13333333333
    }

    public static StudentEntity studentEntityToSave2() {
        return StudentEntity.builder()
                .studentId(null)
                .name("name2")
                .surname("surname2")
                .email("student2@music-school.com")
                .pesel("33333333332")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
    }

    public static StudentEntity studentEntitySaved2() {
        return StudentEntity.builder()
                .studentId("d93452da-fad8-4d86-a476-a1c33c6efc8a")
                .name("name2")
                .surname("surname2")
                .email("student2@music-school.com")
                .pesel("$2a$12$b/IcsSSIyzsDNR93EhzElutbCoV7b7fZScZTVEfs6lss7NhiHdjIC")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
        //33333333332
    }

    public static StudentEntity studentEntityToSave3() {
        return StudentEntity.builder()
                .studentId(null)
                .name("name3")
                .surname("surname3")
                .email("student3@music-school.com")
                .pesel("33333333333")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
    }

    public static StudentEntity studentEntitySaved3() {
        return StudentEntity.builder()
                .studentId("1cec4ffd-18f5-437a-b5ad-3b4ec347e999")
                .name("name3")
                .surname("surname3")
                .email("student3@music-school.com")
                .pesel("$2a$12$X8YYtELB8O0CKBMhWgT4cOiYsA4KMy3TUQOwdy8hIDd077Bq6mHJS")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
        //33333333333
    }

    public static StudentEntity studentEntityToSave4() {
        return StudentEntity.builder()
                .studentId(null)
                .name("name4")
                .surname("surname4")
                .email("student4@music-school.com")
                .pesel("43333333333")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
    }

    public static StudentEntity studentEntitySaved4() {
        return StudentEntity.builder()
                .studentId("efa88725-4ccb-44c7-aecb-b4028cd09e94")
                .name("name4")
                .surname("surname4")
                .email("student4@music-school.com")
                .pesel("$2a$12$JOvKXxmRzgI5EvUsbGhCs.1y2HJIwTPpxSO9QdbPaYiAsha.sMyba")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
        //43333333333
    }

    public static StudentEntity studentEntityToSave5() {
        return StudentEntity.builder()
                .studentId(null)
                .name("name5")
                .surname("surname5")
                .email("student5@music-school.com")
                .pesel("53333333333")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
    }

    public static StudentEntity studentEntitySaved5() {
        return StudentEntity.builder()
                .studentId("d902d866-53ba-46c9-9030-0b6319bfc4bb")
                .name("name5")
                .surname("surname5")
                .email("student5@music-school.com")
                .pesel("$2a$12$4Dh/bAeEus0eJyv9/SAZ/OOCMVLstIvZdLjD.emdh8J/oCIGfrXu.")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolEntityTestData.musicSchoolEntitySaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .build();
        //53333333333
    }

    public static List<StudentEntity> studentEntityList() {
        return List.of(
                studentEntitySaved1(),
                studentEntitySaved2(),
                studentEntitySaved3(),
                studentEntitySaved4(),
                studentEntitySaved5()
        );
    }

}