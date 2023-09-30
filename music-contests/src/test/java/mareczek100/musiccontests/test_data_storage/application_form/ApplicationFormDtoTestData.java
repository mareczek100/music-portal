package mareczek100.musiccontests.test_data_storage.application_form;

import mareczek100.musiccontests.api.dto.ApplicationFormDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.ApplicationFormsDto;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDtoTestData;
import mareczek100.musiccontests.test_data_storage.teacher.TeacherDtoTestData;

import java.util.List;

public class ApplicationFormDtoTestData {
    public static ApplicationFormDto applicationFormDtoToSave1() {
        return ApplicationFormDto.builder()
                .applicationFormId(null)
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved1())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoSaved1() {
        return ApplicationFormDto.builder()
                .applicationFormId("b03d04c5-2602-4edc-9204-6b5f15d4710c")
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved1())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoToSave2() {
        return ApplicationFormDto.builder()
                .applicationFormId(null)
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved2())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoSaved2() {
        return ApplicationFormDto.builder()
                .applicationFormId("1e790ff5-f9d9-4d0c-be5c-6918b208c3fb")
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved2())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoToSave3() {
        return ApplicationFormDto.builder()
                .applicationFormId(null)
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved3())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoSaved3() {
        return ApplicationFormDto.builder()
                .applicationFormId("f1cf93a5-9da9-41a1-abee-cb3f575adc56")
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved3())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoToSave4() {
        return ApplicationFormDto.builder()
                .applicationFormId(null)
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved4())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoSaved4() {
        return ApplicationFormDto.builder()
                .applicationFormId("6236c024-f764-4a3c-afa7-9c1afb100d3a")
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved4())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoToSave5() {
        return ApplicationFormDto.builder()
                .applicationFormId(null)
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved5())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static ApplicationFormDto applicationFormDtoSaved5() {
        return ApplicationFormDto.builder()
                .applicationFormId("2f36171d-b84d-44d2-9673-02aea4c3cf99")
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .teacher(TeacherDtoTestData.teacherDtoSaved1())
                .student(StudentDtoTestData.studentDtoSaved5())
                .classLevel(ClassLevel.FOURTH)
                .performancePieces("performancePieces")
                .build();
    }

    public static List<ApplicationFormDto> applicationFormDtoList() {
        return List.of(
                applicationFormDtoSaved1(),
                applicationFormDtoSaved2(),
                applicationFormDtoSaved3(),
                applicationFormDtoSaved4(),
                applicationFormDtoSaved5()
        );
    }

    public static ApplicationFormsDto applicationFormsDtoList() {
        return ApplicationFormsDto.builder().applicationFormDtoList(applicationFormDtoList()).build();
    }
}