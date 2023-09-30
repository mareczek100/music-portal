package mareczek100.musiccontests.test_data_storage.competition;

import mareczek100.musiccontests.api.dto.CompetitionWithLocationDto;
import mareczek100.musiccontests.api.dto.HeadmasterDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CompetitionWithLocationRestDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.CompetitionsDto;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDtoTestData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CompetitionDtoTestData {
    public static CompetitionWithLocationDto  competitionAtOrganizerSchoolToSaveDto1(){
        return CompetitionWithLocationDto.builder()
                .competitionId(null)
                .competitionName("competitionAtOrganizerSchoolName1")
                .competitionInstrument("competitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Some competition requirements description.")
                .competitionOrganizer(competitionOrganizer())
                .competitionLocationName(competitionOrganizer().musicSchool().musicSchoolName())
                .addressCountry(competitionOrganizer().musicSchool().addressCountry())
                .addressCity(competitionOrganizer().musicSchool().addressCity())
                .addressPostalCode(competitionOrganizer().musicSchool().addressPostalCode())
                .addressStreet(competitionOrganizer().musicSchool().addressStreet())
                .addressBuildingNumber(competitionOrganizer().musicSchool().addressBuildingNumber())
                .addressAdditionalInfo(competitionOrganizer().musicSchool().addressAdditionalInfo())
                .competitionFinished(false)
                .build();
    }
    public static CompetitionWithLocationDto  competitionAtOrganizerSchoolSavedDto1(){
        return CompetitionWithLocationDto.builder()
                .competitionId("c7972d7f-c099-4801-b74c-4a20a0a0c70b")
                .competitionName("competitionAtOrganizerSchoolName1")
                .competitionInstrument("competitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Some competition requirements description.")
                .competitionOrganizer(competitionOrganizer())
                .competitionLocationName(competitionOrganizer().musicSchool().musicSchoolName())
                .addressCountry(competitionOrganizer().musicSchool().addressCountry())
                .addressCity(competitionOrganizer().musicSchool().addressCity())
                .addressPostalCode(competitionOrganizer().musicSchool().addressPostalCode())
                .addressStreet(competitionOrganizer().musicSchool().addressStreet())
                .addressBuildingNumber(competitionOrganizer().musicSchool().addressBuildingNumber())
                .addressAdditionalInfo(competitionOrganizer().musicSchool().addressAdditionalInfo())
                .competitionFinished(false)
                .build();
    }
    public static CompetitionWithLocationDto  competitionAtOrganizerSchoolToSaveDto2(){
        return CompetitionWithLocationDto.builder()
                .competitionId(null)
                .competitionName("competitionAtOrganizerSchoolName2")
                .competitionInstrument("competitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Some competition requirements description.")
                .competitionOrganizer(competitionOrganizer())
                .competitionLocationName(competitionOrganizer().musicSchool().musicSchoolName())
                .addressCountry(competitionOrganizer().musicSchool().addressCountry())
                .addressCity(competitionOrganizer().musicSchool().addressCity())
                .addressPostalCode(competitionOrganizer().musicSchool().addressPostalCode())
                .addressStreet(competitionOrganizer().musicSchool().addressStreet())
                .addressBuildingNumber(competitionOrganizer().musicSchool().addressBuildingNumber())
                .addressAdditionalInfo(competitionOrganizer().musicSchool().addressAdditionalInfo())
                .competitionFinished(false)
                .build();
    }
    public static CompetitionWithLocationDto  competitionAtOrganizerSchoolSavedDto2(){
        return CompetitionWithLocationDto.builder()
                .competitionId("c246bef2-3ea0-42dc-920d-51a119e2a6f3")
                .competitionName("competitionAtOrganizerSchoolName2")
                .competitionInstrument("competitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Some competition requirements description.")
                .competitionOrganizer(competitionOrganizer())
                .competitionLocationName(competitionOrganizer().musicSchool().musicSchoolName())
                .addressCountry(competitionOrganizer().musicSchool().addressCountry())
                .addressCity(competitionOrganizer().musicSchool().addressCity())
                .addressPostalCode(competitionOrganizer().musicSchool().addressPostalCode())
                .addressStreet(competitionOrganizer().musicSchool().addressStreet())
                .addressBuildingNumber(competitionOrganizer().musicSchool().addressBuildingNumber())
                .addressAdditionalInfo(competitionOrganizer().musicSchool().addressAdditionalInfo())
                .competitionFinished(false)
                .build();
    }
    public static CompetitionWithLocationDto  competitionAtOrganizerSchoolToSaveDto3(){
        return CompetitionWithLocationDto.builder()
                .competitionId(null)
                .competitionName("competitionAtOrganizerSchoolName3")
                .competitionInstrument("competitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Some competition requirements description.")
                .competitionOrganizer(competitionOrganizer())
                .competitionLocationName(competitionOrganizer().musicSchool().musicSchoolName())
                .addressCountry(competitionOrganizer().musicSchool().addressCountry())
                .addressCity(competitionOrganizer().musicSchool().addressCity())
                .addressPostalCode(competitionOrganizer().musicSchool().addressPostalCode())
                .addressStreet(competitionOrganizer().musicSchool().addressStreet())
                .addressBuildingNumber(competitionOrganizer().musicSchool().addressBuildingNumber())
                .addressAdditionalInfo(competitionOrganizer().musicSchool().addressAdditionalInfo())
                .competitionFinished(false)
                .build();
    }
    public static CompetitionWithLocationDto  competitionAtOrganizerSchoolSavedDto3(){
        return CompetitionWithLocationDto.builder()
                .competitionId("aa24b3e9-8368-44d2-9135-d175c52565d2")
                .competitionName("competitionAtOrganizerSchoolName3")
                .competitionInstrument("competitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Some competition requirements description.")
                .competitionOrganizer(competitionOrganizer())
                .competitionLocationName(competitionOrganizer().musicSchool().musicSchoolName())
                .addressCountry(competitionOrganizer().musicSchool().addressCountry())
                .addressCity(competitionOrganizer().musicSchool().addressCity())
                .addressPostalCode(competitionOrganizer().musicSchool().addressPostalCode())
                .addressStreet(competitionOrganizer().musicSchool().addressStreet())
                .addressBuildingNumber(competitionOrganizer().musicSchool().addressBuildingNumber())
                .addressAdditionalInfo(competitionOrganizer().musicSchool().addressAdditionalInfo())
                .competitionFinished(false)
                .build();
    }
    public static CompetitionWithLocationDto competitionAtOtherLocationToSaveDto1() {
        return CompetitionWithLocationDto.builder()
                .competitionId(null)
                .competitionName("competitionAtOtherLocationName1")
                .competitionInstrument("competitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Some competition requirements description.")
                .competitionOrganizer(competitionOrganizer())
                .competitionFinished(false)
                .competitionLocationName("Ratusz")
                .addressCountry("Polska")
                .addressCity("Warszawa")
                .addressPostalCode("00-000")
                .addressStreet("Krakowska")
                .addressBuildingNumber("20/20")
                .addressAdditionalInfo("Wejście od tyłu budynku")
                .build();
    }
    public static CompetitionWithLocationDto competitionAtOtherLocationSavedDto1() {
        return CompetitionWithLocationDto.builder()
                .competitionId("5e457d48-eae7-45ce-b3a6-a0706a8deb83")
                .competitionName("competitionAtOtherLocationName1")
                .competitionInstrument("competitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Some competition requirements description.")
                .competitionOrganizer(competitionOrganizer())
                .competitionFinished(false)
                .competitionLocationName("Ratusz")
                .addressCountry("Polska")
                .addressCity("Warszawa")
                .addressPostalCode("00-000")
                .addressStreet("Krakowska")
                .addressBuildingNumber("20/20")
                .addressAdditionalInfo("Wejście od tyłu budynku")
                .build();
    }
    public static CompetitionWithLocationRestDto competitionToUpdateDto() {
        return CompetitionWithLocationRestDto.builder()
                .competitionId(null)
                .competitionName("updatedName1")
                .competitionInstrument("updatedCompetitionInstrument")
                .competitionOnline(false)
                .competitionPrimaryDegree(true)
                .competitionSecondaryDegree(false)
                .competitionBeginning(beginDateTime())
                .competitionEnd(endDateTime())
                .competitionResultAnnouncement(resultsDateTime())
                .competitionApplicationDeadline(deadlineDateTime())
                .competitionRequirementsDescription("Updated competition requirements description.")
                .competitionFinished(false)
                .competitionLocationName("Updated location name")
                .addressCountry("Polska")
                .addressCity("Warszawa")
                .addressPostalCode("00-000")
                .addressStreet("Updated street")
                .addressBuildingNumber("20/20")
                .addressAdditionalInfo("")
                .build();
    }

    public static List<CompetitionWithLocationDto> competitionDtoList() {
        return List.of(
                competitionAtOrganizerSchoolSavedDto1(),
                competitionAtOrganizerSchoolSavedDto2(),
                competitionAtOrganizerSchoolSavedDto3()
        );
    }

    public static CompetitionsDto competitionsDtoList() {
        return CompetitionsDto.builder().competitionDtoList(competitionDtoList()).build();
    }

    private static LocalDateTime beginDateTime(){
        LocalDate localDate = LocalDate.of(2030, 2, 22);
        LocalTime localTime = LocalTime.of(20, 0);
        return LocalDateTime.of(localDate, localTime);
    }
    private static LocalDateTime endDateTime(){
        LocalDate localDate = LocalDate.of(2030, 2, 24);
        LocalTime localTime = LocalTime.of(10, 0);
        return LocalDateTime.of(localDate, localTime);
    }
    private static LocalDateTime resultsDateTime(){
        LocalDate localDate = LocalDate.of(2030, 3, 2);
        LocalTime localTime = LocalTime.of(20, 0);
        return LocalDateTime.of(localDate, localTime);
    }
    private static LocalDateTime deadlineDateTime(){
        LocalDate localDate = LocalDate.of(3030, 2, 21);
        LocalTime localTime = LocalTime.of(23, 59);
        return LocalDateTime.of(localDate, localTime);
    }
    private static HeadmasterDto competitionOrganizer() {
        return HeadmasterDtoTestData.headmasterDtoSaved1();
    }
}
