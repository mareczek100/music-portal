package mareczek100.musiccontests.test_data_storage.application_form;



import mareczek100.musiccontests.domain.ApplicationForm;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDomainTestData;

import java.util.List;

public class ApplicationFormDomainTestData {
    public static ApplicationForm applicationFormToSave1() {
        return ApplicationForm.builder()
                .applicationFormId(null)
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved1())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormSaved1() {
        return ApplicationForm.builder()
                .applicationFormId("b03d04c5-2602-4edc-9204-6b5f15d4710c")
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved1())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormToSave2() {
        return ApplicationForm.builder()
                .applicationFormId(null)
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved2())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormSaved2() {
        return ApplicationForm.builder()
                .applicationFormId("1e790ff5-f9d9-4d0c-be5c-6918b208c3fb")
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved2())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormToSave3() {
        return ApplicationForm.builder()
                .applicationFormId(null)
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved3())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormSaved3() {
        return ApplicationForm.builder()
                .applicationFormId("f1cf93a5-9da9-41a1-abee-cb3f575adc56")
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved3())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormToSave4() {
        return ApplicationForm.builder()
                .applicationFormId(null)
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved4())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormSaved4() {
        return ApplicationForm.builder()
                .applicationFormId("6236c024-f764-4a3c-afa7-9c1afb100d3a")
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved4())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormToSave5() {
        return ApplicationForm.builder()
                .applicationFormId(null)
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved5())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationForm applicationFormSaved5() {
        return ApplicationForm.builder()
                .applicationFormId("2f36171d-b84d-44d2-9673-02aea4c3cf99")
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .teacher(TeacherDomainTestData.teacherSaved1())
                .student(StudentDomainTestData.studentSaved5())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static List<ApplicationForm> applicationFormList() {
        return List.of(
                applicationFormSaved1(),
                applicationFormSaved2(),
                applicationFormSaved3(),
                applicationFormSaved4(),
                applicationFormSaved5()
        );
    }

}