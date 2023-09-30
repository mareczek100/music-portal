package mareczek100.musiccontests.test_data_storage.competition;

import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionEntity;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionLocationEntity;
import mareczek100.musiccontests.infrastructure.database.entity.HeadmasterEntity;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterEntityTestData;

import java.time.*;
import java.util.List;


public class CompetitionEntityTestData {

    private static final ZoneOffset ZONE_OFFSET
            = ZoneId.systemDefault().getRules().getOffset(LocalDateTime.now());
    public static CompetitionEntity competitionAtOrganizerSchoolEntityToSave1(){
        return CompetitionEntity.builder()
                .competitionId(null)
                .name("competitionAtOrganizerSchoolEntityName1")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizerEntity())
                .competitionLocation(getLocationEntityAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static CompetitionEntity  competitionAtOrganizerSchoolEntitySaved1(){
        return CompetitionEntity.builder()
                .competitionId("c7972d7f-c099-4801-b74c-4a20a0a0c70b")
                .name("competitionAtOrganizerSchoolEntityName1")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizerEntity())
                .competitionLocation(getLocationEntityAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static CompetitionEntity  competitionAtOrganizerSchoolEntityToSave2(){
        return CompetitionEntity.builder()
                .competitionId(null)
                .name("competitionAtOrganizerSchoolEntityName2")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizerEntity())
                .competitionLocation(getLocationEntityAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static CompetitionEntity  competitionAtOrganizerSchoolEntitySaved2(){
        return CompetitionEntity.builder()
                .competitionId("c246bef2-3ea0-42dc-920d-51a119e2a6f3")
                .name("competitionAtOrganizerSchoolEntityName2")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizerEntity())
                .competitionLocation(getLocationEntityAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static CompetitionEntity  competitionAtOrganizerSchoolEntityToSave3(){
        return CompetitionEntity.builder()
                .competitionId(null)
                .name("competitionAtOrganizerSchoolEntityName3")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizerEntity())
                .competitionLocation(getLocationEntityAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static CompetitionEntity  competitionAtOrganizerSchoolEntitySaved3(){
        return CompetitionEntity.builder()
                .competitionId("aa24b3e9-8368-44d2-9135-d175c52565d2")
                .name("competitionAtOrganizerSchoolEntityName3")
                .instrument("competitionInstrument")
                .online(false)
                .primaryDegree(true)
                .secondaryDegree(false)
                .beginning(beginDateTime())
                .end(endDateTime())
                .resultAnnouncement(resultsDateTime())
                .applicationDeadline(deadlineDateTime())
                .requirementsDescription("Some competition requirements description.")
                .headmaster(competitionOrganizerEntity())
                .competitionLocation(getLocationEntityAtSchoolSaved())
                .finished(false)
                .build();
    }
    public static CompetitionEntity competitionAtOtherLocationEntityToSave1() {
        return CompetitionEntity.builder()
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
                .headmaster(competitionOrganizerEntity())
                .competitionLocation(getLocationEntityAtOtherPlaceToSave())
                .finished(false)
                .build();
    }
    public static CompetitionEntity competitionAtOtherLocationEntitySaved1() {
        return CompetitionEntity.builder()
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
                .headmaster(competitionOrganizerEntity())
                .competitionLocation(getLocationEntityAtOtherPlaceSaved())
                .finished(false)
                .build();
    }

    public static List<CompetitionEntity> competitionList() {
        return List.of(
                competitionAtOrganizerSchoolEntitySaved1(),
                competitionAtOrganizerSchoolEntitySaved2(),
                competitionAtOrganizerSchoolEntitySaved3()
        );
    }

    private static CompetitionLocationEntity getLocationEntityAtSchoolToSave() {
        return CompetitionLocationEntity.builder()
                .competitionLocationId(null)
                .name(competitionOrganizerEntity().getMusicSchool().getName())
                .address(competitionOrganizerEntity().getMusicSchool().getAddress())
                .build();

    }
    private static CompetitionLocationEntity getLocationEntityAtSchoolSaved() {
        return CompetitionLocationEntity.builder()
                .competitionLocationId("07b4d7e8-3385-4129-8e02-1cf57c0c56cc")
                .name(competitionOrganizerEntity().getMusicSchool().getName())
                .address(competitionOrganizerEntity().getMusicSchool().getAddress())
                .build();

    }

    private static CompetitionLocationEntity getLocationEntityAtOtherPlaceToSave() {
        return CompetitionLocationEntity.builder()
                .competitionLocationId(null)
                .name("Ratusz")
                .address(addressEntityToSave())
                .build();

    }
    private static CompetitionLocationEntity getLocationEntityAtOtherPlaceSaved() {
        return CompetitionLocationEntity.builder()
                .competitionLocationId(null)
                .name("Ratusz")
                .address(addressEntitySaved())
                .build();

    }

    private static AddressEntity addressEntityToSave(){
        return AddressEntity.builder()
                .addressId(null)
                .country("Polska")
                .city("Warszawa")
                .postalCode("00-000")
                .street("addressStreet")
                .buildingNumber("20/20")
                .additionalInfo("Wejście od tyłu budynku")
                .build();
    }
    private static AddressEntity addressEntitySaved(){
        return AddressEntity.builder()
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
    private static HeadmasterEntity competitionOrganizerEntity() {
        return HeadmasterEntityTestData.headmasterEntitySaved1();
    }
}
