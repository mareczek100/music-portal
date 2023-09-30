package mareczek100.musiccontests.test_data_storage.competition_results;

import mareczek100.musiccontests.api.dto.CompetitionResultDto;
import mareczek100.musiccontests.api.dto.dto_class_support.CompetitionResultListDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CompetitionResultsDto;
import mareczek100.musiccontests.test_data_storage.competition.CompetitionDtoTestData;
import mareczek100.musiccontests.test_data_storage.student.StudentDtoTestData;

import java.util.List;

public class CompetitionResultDtoTestData {
    public static CompetitionResultDto competitionResultToSave1() {
        return CompetitionResultDto.builder()
                .competitionResultId(null)
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .student(StudentDtoTestData.studentDtoSaved1())
                .competitionPlace("competitionPlace1")
                .specialAward("specialAward1")
                .build();
    }

    public static CompetitionResultDto competitionResultSavedDto1() {
        return CompetitionResultDto.builder()
                .competitionResultId("09c79493-b897-4d79-952e-6824b0561c24")
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .student(StudentDtoTestData.studentDtoSaved1())
                .competitionPlace("competitionPlace1")
                .specialAward("specialAward1")
                .build();
    }

    public static CompetitionResultDto competitionResultToSave2() {
        return CompetitionResultDto.builder()
                .competitionResultId(null)
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .student(StudentDtoTestData.studentDtoSaved2())
                .competitionPlace("competitionPlace2")
                .specialAward("specialAward2")
                .build();
    }

    public static CompetitionResultDto competitionResultSavedDto2() {
        return CompetitionResultDto.builder()
                .competitionResultId("1319cbb6-8a7f-47ee-9997-ce1733899458")
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .student(StudentDtoTestData.studentDtoSaved2())
                .competitionPlace("competitionPlace2")
                .specialAward("specialAward2")
                .build();
    }

    public static CompetitionResultDto competitionResultToSave3() {
        return CompetitionResultDto.builder()
                .competitionResultId(null)
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .student(StudentDtoTestData.studentDtoSaved3())
                .competitionPlace("competitionPlace3")
                .specialAward("specialAward3")
                .build();
    }

    public static CompetitionResultDto competitionResultSavedDto3() {
        return CompetitionResultDto.builder()
                .competitionResultId("4ec4c5fc-731f-4c35-9f3a-68db90959cc0")
                .competition(CompetitionDtoTestData.competitionAtOrganizerSchoolSavedDto1())
                .student(StudentDtoTestData.studentDtoSaved3())
                .competitionPlace("competitionPlace3")
                .specialAward("specialAward3")
                .build();
    }

    public static List<CompetitionResultDto> competitionResultDtoListSaved() {
        return List.of(
                competitionResultSavedDto1(),
                competitionResultSavedDto2(),
                competitionResultSavedDto3()
        );
    }

    public static CompetitionResultsDto competitionResultsDtoSaved() {
        return CompetitionResultsDto.builder().competitionResultDtoList(competitionResultDtoListSaved()).build();
    }


    public static CompetitionResultListDto competitionResultsDtoToSave() {
        return CompetitionResultListDto.builder()
                .competitionResultsSupport(competitionResultSupportList()).build();
    }

    public static List<CompetitionResultListDto.CompetitionResultSupport> competitionResultSupportList() {
        return List.of(
                new CompetitionResultListDto.CompetitionResultSupport(
                        competitionResultToSave1().student().studentId(),
                        competitionResultToSave1().competitionPlace(),
                        competitionResultToSave1().specialAward()),
        new CompetitionResultListDto.CompetitionResultSupport(
                competitionResultToSave2().student().studentId(),
                competitionResultToSave2().competitionPlace(),
                competitionResultToSave2().specialAward()),
        new CompetitionResultListDto.CompetitionResultSupport(
                competitionResultToSave3().student().studentId(),
                competitionResultToSave3().competitionPlace(),
                competitionResultToSave3().specialAward())
        );
    }
    public static CompetitionResultListDto competitionResultsDtoEmpty() {
        return CompetitionResultListDto.builder()
                .competitionResultsSupport(competitionResultSupportEmptyList()).build();
    }
    public static List<CompetitionResultListDto.CompetitionResultSupport> competitionResultSupportEmptyList() {
        return List.of(
                new CompetitionResultListDto.CompetitionResultSupport(
                        null,
                        null,
                        null),
        new CompetitionResultListDto.CompetitionResultSupport(
                null,
                null,
                null),
        new CompetitionResultListDto.CompetitionResultSupport(
                null,
                null,
                null)
        );
    }

}
