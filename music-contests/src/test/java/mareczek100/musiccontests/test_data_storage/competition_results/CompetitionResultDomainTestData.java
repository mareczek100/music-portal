package mareczek100.musiccontests.test_data_storage.competition_results;


import mareczek100.musiccontests.domain.CompetitionResult;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDomainTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDomainTestData;

import java.util.List;

public class CompetitionResultDomainTestData {
    public static CompetitionResult competitionResultToSave1() {
        return CompetitionResult.builder()
                .competitionResultId(null)
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .student(StudentDomainTestData.studentSaved1())
                .competitionPlace("competitionPlace1")
                .specialAward("specialAward1")
                .build();
    }

    public static CompetitionResult competitionResultSaved1() {
        return CompetitionResult.builder()
                .competitionResultId("09c79493-b897-4d79-952e-6824b0561c24")
                .competition(CompetitionDomainTestData
                        .competitionAtOrganizerSchoolSaved1().withFinished(true))
                .student(StudentDomainTestData.studentSaved1())
                .competitionPlace("competitionPlace1")
                .specialAward("specialAward1")
                .build();
    }

    public static CompetitionResult competitionResultToSave2() {
        return CompetitionResult.builder()
                .competitionResultId(null)
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .student(StudentDomainTestData.studentSaved2())
                .competitionPlace("competitionPlace2")
                .specialAward("specialAward2")
                .build();
    }

    public static CompetitionResult competitionResultSaved2() {
        return CompetitionResult.builder()
                .competitionResultId("1319cbb6-8a7f-47ee-9997-ce1733899458")
                .competition(CompetitionDomainTestData
                        .competitionAtOrganizerSchoolSaved1().withFinished(true))
                .student(StudentDomainTestData.studentSaved2())
                .competitionPlace("competitionPlace2")
                .specialAward("specialAward2")
                .build();
    }

    public static CompetitionResult competitionResultToSave3() {
        return CompetitionResult.builder()
                .competitionResultId(null)
                .competition(CompetitionDomainTestData.competitionAtOrganizerSchoolSaved1())
                .student(StudentDomainTestData.studentSaved3())
                .competitionPlace("competitionPlace3")
                .specialAward("specialAward3")
                .build();
    }

    public static CompetitionResult competitionResultSaved3() {
        return CompetitionResult.builder()
                .competitionResultId("4ec4c5fc-731f-4c35-9f3a-68db90959cc0")
                .competition(CompetitionDomainTestData
                        .competitionAtOrganizerSchoolSaved1().withFinished(true))
                .student(StudentDomainTestData.studentSaved3())
                .competitionPlace("competitionPlace3")
                .specialAward("specialAward3")
                .build();
    }


    public static List<CompetitionResult> competitionResultListToSave() {
        return List.of(
                competitionResultToSave1(),
                competitionResultToSave2(),
                competitionResultToSave3()
        );
    }
    public static List<CompetitionResult> competitionResultListSaved() {
        return List.of(
                competitionResultSaved1(),
                competitionResultSaved2(),
                competitionResultSaved3()
        );
    }

}
