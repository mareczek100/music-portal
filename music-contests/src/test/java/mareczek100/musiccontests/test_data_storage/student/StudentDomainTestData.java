package mareczek100.musiccontests.test_data_storage.student;


import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.domain.enums.Degree;
import mareczek100.musiccontests.domain.enums.EducationProgram;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDomainTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDomainTestData;

import java.util.List;

public class StudentDomainTestData {
    public static Student studentToSave1() {
        return Student.builder()
                .studentId(null)
                .name("name1")
                .surname("surname1")
                .email("student1@music-school.com")
                .pesel("13333333333")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
    }

    public static Student studentSaved1() {
        return Student.builder()
                .studentId("90903284-5fab-4113-bf93-94e040685eb9")
                .name("name1")
                .surname("surname1")
                .email("student1@music-school.com")
                .pesel("$2a$12$/iK7.k0Weo3rpCdRlvaDouun/6nHI75KTPVMptxeg8KY3ov.vqTvC")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
        //13333333333
    }

    public static Student studentToSave2() {
        return Student.builder()
                .studentId(null)
                .name("name2")
                .surname("surname2")
                .email("student2@music-school.com")
                .pesel("33333333332")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
    }

    public static Student studentSaved2() {
        return Student.builder()
                .studentId("d93452da-fad8-4d86-a476-a1c33c6efc8a")
                .name("name2")
                .surname("surname2")
                .email("student2@music-school.com")
                .pesel("$2a$12$b/IcsSSIyzsDNR93EhzElutbCoV7b7fZScZTVEfs6lss7NhiHdjIC")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
        //33333333332
    }

    public static Student studentToSave3() {
        return Student.builder()
                .studentId(null)
                .name("name3")
                .surname("surname3")
                .email("student3@music-school.com")
                .pesel("33333333333")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
    }

    public static Student studentSaved3() {
        return Student.builder()
                .studentId("1cec4ffd-18f5-437a-b5ad-3b4ec347e999")
                .name("name3")
                .surname("surname3")
                .email("student3@music-school.com")
                .pesel("$2a$12$X8YYtELB8O0CKBMhWgT4cOiYsA4KMy3TUQOwdy8hIDd077Bq6mHJS")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
        //33333333333
    }

    public static Student studentToSave4() {
        return Student.builder()
                .studentId(null)
                .name("name4")
                .surname("surname4")
                .email("student4@music-school.com")
                .pesel("43333333333")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
    }

    public static Student studentSaved4() {
        return Student.builder()
                .studentId("efa88725-4ccb-44c7-aecb-b4028cd09e94")
                .name("name4")
                .surname("surname4")
                .email("student4@music-school.com")
                .pesel("$2a$12$JOvKXxmRzgI5EvUsbGhCs.1y2HJIwTPpxSO9QdbPaYiAsha.sMyba")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
        //43333333333
    }

    public static Student studentToSave5() {
        return Student.builder()
                .studentId(null)
                .name("name5")
                .surname("surname5")
                .email("student5@music-school.com")
                .pesel("53333333333")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
    }

    public static Student studentSaved5() {
        return Student.builder()
                .studentId("d902d866-53ba-46c9-9030-0b6319bfc4bb")
                .name("name5")
                .surname("surname5")
                .email("student5@music-school.com")
                .pesel("$2a$12$4Dh/bAeEus0eJyv9/SAZ/OOCMVLstIvZdLjD.emdh8J/oCIGfrXu.")
                .password("StudentPassword")
                .classYear(ClassLevel.FIRST)
                .educationDuration(EducationProgram.FOUR_YEAR)
                .musicSchoolDegree(Degree.FIRST)
                .musicSchool(MusicSchoolDomainTestData.musicSchoolSaved1())
                .mainInstrument("fortepian")
                .secondInstrument("obój")
                .teacher(TeacherDomainTestData.teacherSaved1())
                .build();
        //53333333333
    }

    public static List<Student> studentList() {
        return List.of(
                studentSaved1(),
                studentSaved2(),
                studentSaved3(),
                studentSaved4(),
                studentSaved5()
        );
    }

}