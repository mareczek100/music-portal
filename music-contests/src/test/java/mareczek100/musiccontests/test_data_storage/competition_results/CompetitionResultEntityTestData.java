package mareczek100.musiccontests.test_data_storage.competition_results;


import mareczek100.musiccontests.infrastructure.database.entity.CompetitionResultEntity;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionEntityTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentEntityTestData;

import java.util.List;

public class CompetitionResultEntityTestData {
    public static CompetitionResultEntity competitionResultEntityToSave1() {
        return CompetitionResultEntity.builder()
                .competitionResultId(null)
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved1())
                .competitionPlace("competitionPlace1")
                .specialAward("specialAward1")
                .build();
    }

    public static CompetitionResultEntity competitionResultEntitySaved1() {
        return CompetitionResultEntity.builder()
                .competitionResultId("09c79493-b897-4d79-952e-6824b0561c24")
                .competition(CompetitionEntityTestData
                        .competitionAtOrganizerSchoolEntitySaved1().withFinished(true))
                .student(StudentEntityTestData.studentEntitySaved1())
                .competitionPlace("competitionPlace1")
                .specialAward("specialAward1")
                .build();
    }

    public static CompetitionResultEntity competitionResultEntityToSave2() {
        return CompetitionResultEntity.builder()
                .competitionResultId(null)
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved2())
                .competitionPlace("competitionPlace2")
                .specialAward("specialAward2")
                .build();
    }

    public static CompetitionResultEntity competitionResultEntitySaved2() {
        return CompetitionResultEntity.builder()
                .competitionResultId("1319cbb6-8a7f-47ee-9997-ce1733899458")
                .competition(CompetitionEntityTestData
                        .competitionAtOrganizerSchoolEntitySaved1().withFinished(true))
                .student(StudentEntityTestData.studentEntitySaved2())
                .competitionPlace("competitionPlace2")
                .specialAward("specialAward2")
                .build();
    }

    public static CompetitionResultEntity competitionResultEntityToSave3() {
        return CompetitionResultEntity.builder()
                .competitionResultId(null)
                .competition(CompetitionEntityTestData.competitionAtOrganizerSchoolEntitySaved1())
                .student(StudentEntityTestData.studentEntitySaved3())
                .competitionPlace("competitionPlace3")
                .specialAward("specialAward3")
                .build();
    }

    public static CompetitionResultEntity competitionResultEntitySaved3() {
        return CompetitionResultEntity.builder()
                .competitionResultId("4ec4c5fc-731f-4c35-9f3a-68db90959cc0")
                .competition(CompetitionEntityTestData
                        .competitionAtOrganizerSchoolEntitySaved1().withFinished(true))
                .student(StudentEntityTestData.studentEntitySaved3())
                .competitionPlace("competitionPlace3")
                .specialAward("specialAward3")
                .build();
    }


    public static List<CompetitionResultEntity> competitionResultListEntitiesToSave() {
        return List.of(
                competitionResultEntityToSave1(),
                competitionResultEntityToSave2(),
                competitionResultEntityToSave3()
        );
    }
    public static List<CompetitionResultEntity> competitionResultListEntitiesSaved() {
        return List.of(
                competitionResultEntitySaved1(),
                competitionResultEntitySaved2(),
                competitionResultEntitySaved3()
        );
    }

}
