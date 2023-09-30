package mareczek100.musiccontests.test_data_storage.application_form;


import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.infrastructure.database.entity.ApplicationFormEntity;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionEntityTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentEntityTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherEntityTestData;

import java.util.List;

public class ApplicationFormEntityTestData {
    public static ApplicationFormEntity applicationFormEntityToSave1() {
        return ApplicationFormEntity.builder()
                .applicationFormId(null)
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved1())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntitySaved1() {
        return ApplicationFormEntity.builder()
                .applicationFormId("b03d04c5-2602-4edc-9204-6b5f15d4710c")
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved1())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntityToSave2() {
        return ApplicationFormEntity.builder()
                .applicationFormId(null)
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved2())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntitySaved2() {
        return ApplicationFormEntity.builder()
                .applicationFormId("1e790ff5-f9d9-4d0c-be5c-6918b208c3fb")
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved2())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntityToSave3() {
        return ApplicationFormEntity.builder()
                .applicationFormId(null)
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved3())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntitySaved3() {
        return ApplicationFormEntity.builder()
                .applicationFormId("f1cf93a5-9da9-41a1-abee-cb3f575adc56")
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved3())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntityToSave4() {
        return ApplicationFormEntity.builder()
                .applicationFormId(null)
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved4())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntitySaved4() {
        return ApplicationFormEntity.builder()
                .applicationFormId("6236c024-f764-4a3c-afa7-9c1afb100d3a")
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved4())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntityToSave5() {
        return ApplicationFormEntity.builder()
                .applicationFormId(null)
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved5())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormEntity applicationFormEntitySaved5() {
        return ApplicationFormEntity.builder()
                .applicationFormId("2f36171d-b84d-44d2-9673-02aea4c3cf99")
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .teacher(TeacherEntityTestData.teacherEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved5())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static List<ApplicationFormEntity> applicationFormList() {
        return List.of(
                applicationFormEntitySaved1(),
                applicationFormEntitySaved2(),
                applicationFormEntitySaved3(),
                applicationFormEntitySaved4(),
                applicationFormEntitySaved5()
        );
    }

}