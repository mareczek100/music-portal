package mareczek100.musiccontests.test_data_storage.competition;


import mareczek100.musiccontests.api.dto.dto_rest_support.CitiesDto;
import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.domain.CompetitionLocation;
import mareczek100.musiccontests.domain.Headmaster;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDomainTestData;

import java.time.*;
import java.util.List;

public class CompetitionDomainTestData {

    private static final ZoneOffset ZONE_OFFSET
            = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now());
    public static Competition competitionAtOrganizerSchoolToSave1(){
        return Competition.builder()
                .competitionId(null)
                .name("competitionAtOrganizerSchoolName1")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizer())
                .competitionLocation(getLocationAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static Competition  competitionAtOrganizerSchoolSaved1(){
        return Competition.builder()
                .competitionId("c7972d7f-c099-4801-b74c-4a20a0a0c70b")
                .name("competitionAtOrganizerSchoolName1")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizer())
                .competitionLocation(getLocationAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static Competition  competitionAtOrganizerSchoolToSave2(){
        return Competition.builder()
                .competitionId(null)
                .name("competitionAtOrganizerSchoolName2")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizer())
                .competitionLocation(getLocationAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static Competition  competitionAtOrganizerSchoolSaved2(){
        return Competition.builder()
                .competitionId("c246bef2-3ea0-42dc-920d-51a119e2a6f3")
                .name("competitionAtOrganizerSchoolName2")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizer())
                .competitionLocation(getLocationAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static Competition  competitionAtOrganizerSchoolToSave3(){
        return Competition.builder()
                .competitionId(null)
                .name("competitionAtOrganizerSchoolName3")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizer())
                .competitionLocation(getLocationAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static Competition  competitionAtOrganizerSchoolSaved3(){
        return Competition.builder()
                .competitionId("aa24b3e9-8368-44d2-9135-d175c52565d2")
                .name("competitionAtOrganizerSchoolName3")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizer())
                .competitionLocation(getLocationAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static Competition competitionAtOtherLocationToSave1() {
        return Competition.builder()
                .competitionId(null)
                .name("competitionAtOtherLocationName1")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description")
                .headmaster(competitionOrganizer())
                .competitionLocation(getLocationAtOtherPlaceToSave())
                .finished(false)
                .build();
    }
    public static Competition competitionAtOtherLocationSaved1() {
        return Competition.builder()
                .competitionId("5e457d48-eae7-45ce-b3a6-a0706a8deb83")
                .name("competitionAtOtherLocationName1")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description")
                .headmaster(competitionOrganizer())
                .competitionLocation(getLocationAtOtherPlaceSaved())
                .finished(false)
                .build();
    }

    public static List<Competition> competitionList() {
        return List.of(
                competitionAtOrganizerSchoolSaved1(),
                competitionAtOrganizerSchoolSaved2(),
                competitionAtOrganizerSchoolSaved3()
        );
    }
    public static List<String> competitionCityList() {
        return List.of(
                competitionAtOrganizerSchoolSaved1().competitionLocation().address().city(),
                competitionAtOrganizerSchoolSaved2().competitionLocation().address().city(),
                competitionAtOrganizerSchoolSaved3().competitionLocation().address().city()
        );
    }

    public static CitiesDto competitionCities() {
        return CitiesDto.builder().competitionCitiesDtoList(competitionCityList()).build();
    }

    private static CompetitionLocation getLocationAtSchoolToSave() {
        return CompetitionLocation.builder()
                .competitionLocationId(null)
                .name(competitionOrganizer().musicSchool().name())
                .address(competitionOrganizer().musicSchool().address())
                .build();

    }
    private static CompetitionLocation getLocationAtSchoolSaved() {
        return CompetitionLocation.builder()
                .competitionLocationId("07b4d7e8-3385-4129-8e02-1cf57c0c56cc")
                .name(competitionOrganizer().musicSchool().name())
                .address(competitionOrganizer().musicSchool().address())
                .build();

    }

    private static CompetitionLocation getLocationAtOtherPlaceToSave() {
        return CompetitionLocation.builder()
                .competitionLocationId(null)
                .name("Ratusz")
                .address(addressToSave())
                .build();

    }
    private static CompetitionLocation getLocationAtOtherPlaceSaved() {
        return CompetitionLocation.builder()
                .competitionLocationId(null)
                .name("Ratusz")
                .address(addressSaved())
                .build();

    }

    private static Address addressToSave(){
        return Address.builder()
                .addressId(null)
                .country("Polska")
                .city("Warszawa")
                .postalCode("00-000")
                .street("addressStreet")
                .buildingNumber("20/20")
                .additionalInfo("Wejście od tyłu budynku")
                .build();
    }
    private static Address addressSaved(){
        return Address.builder()
                .addressId("c023743e-0870-452c-a862-864b7605762a")
                .country("Polska")
                .city("Warszawa")
                .postalCode("00-000")
                .street("addressStreet")
                .buildingNumber("20/20")
                .additionalInfo("Wejście od tyłu budynku")
                .build();
    }

    private static OffsetDateTime beginDateTime(){
        LocalDate localDate = LocalDate.of(2030, 2, 22);
        LocalTime localTime = LocalTime.of(20, 0);
        return OffsetDateTime.of(localDate, localTime, ZONE_OFFSET);
    }
    private static OffsetDateTime endDateTime(){
        LocalDate localDate = LocalDate.of(2030, 2, 24);
        LocalTime localTime = LocalTime.of(10, 0);
        return OffsetDateTime.of(localDate, localTime, ZONE_OFFSET);
    }
    private static OffsetDateTime resultsDateTime(){
        LocalDate localDate = LocalDate.of(2030, 3, 2);
        LocalTime localTime = LocalTime.of(20, 0);
        return OffsetDateTime.of(localDate, localTime, ZONE_OFFSET);
    }
    private static OffsetDateTime deadlineDateTime(){
        LocalDate localDate = LocalDate.of(3030, 2, 21);
        LocalTime localTime = LocalTime.of(23, 59);
        return OffsetDateTime.of(localDate, localTime, ZONE_OFFSET);
    }
    private static Headmaster competitionOrganizer() {
        return HeadmasterDomainTestData.headmasterSaved1();
    }
}
