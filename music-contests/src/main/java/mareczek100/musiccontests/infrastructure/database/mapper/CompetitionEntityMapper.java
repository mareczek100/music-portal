package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.*;
import mareczek100.musiccontests.infrastructure.database.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompetitionEntityMapper {

    @Named("competitionMapFromEntityToDomain")
    default Competition mapFromEntityToDomain(CompetitionEntity competitionEntity){
        return Competition.builder()
                .competitionId(competitionEntity.getCompetitionId())
                .name(competitionEntity.getName())
                .instrument(competitionEntity.getInstrument())
                .online(competitionEntity.getOnline())
                .primaryDegree(competitionEntity.getPrimaryDegree())
                .secondaryDegree(competitionEntity.getSecondaryDegree())
                .beginning(competitionEntity.getBeginning())
                .end(competitionEntity.getEnd())
                .resultAnnouncement(competitionEntity.getResultAnnouncement())
                .applicationDeadline(competitionEntity.getApplicationDeadline())
                .requirementsDescription(competitionEntity.getRequirementsDescription())
                .headmaster(getHeadmaster(competitionEntity.getHeadmaster()))
                .competitionLocation(getCompetitionLocation(competitionEntity.getCompetitionLocation()))
                .finished(competitionEntity.getFinished())
                .build();
    }

    private Headmaster getHeadmaster(HeadmasterEntity headmasterEntity){
        return Headmaster.builder()
                .headmasterId(headmasterEntity.getHeadmasterId())
                .name(headmasterEntity.getName())
                .surname(headmasterEntity.getSurname())
                .email(headmasterEntity.getEmail())
                .pesel(headmasterEntity.getPesel())
                .musicSchool(getMusicSchool(headmasterEntity.getMusicSchool()))
                .build();
    }

    private MusicSchool getMusicSchool(MusicSchoolEntity musicSchoolEntity){
        return MusicSchool.builder()
                .musicSchoolId(musicSchoolEntity.getMusicSchoolId())
                .name(musicSchoolEntity.getName())
                .patron(musicSchoolEntity.getPatron())
                .primaryDegree(musicSchoolEntity.getPrimaryDegree())
                .secondaryDegree(musicSchoolEntity.getSecondaryDegree())
                .address(getAddress(musicSchoolEntity.getAddress()))
                .build();
    }

    private Address getAddress(AddressEntity addressEntity){
        return Address.builder()
                .addressId(addressEntity.getAddressId())
                .country(addressEntity.getCountry())
                .city(addressEntity.getCity())
                .postalCode(addressEntity.getPostalCode())
                .street(addressEntity.getStreet())
                .buildingNumber(addressEntity.getBuildingNumber())
                .additionalInfo(addressEntity.getAdditionalInfo())
                .build();
    }

    private CompetitionLocation getCompetitionLocation(CompetitionLocationEntity competitionLocationEntity){
        return CompetitionLocation.builder()
                .competitionLocationId(competitionLocationEntity.getCompetitionLocationId())
                .name(competitionLocationEntity.getName())
                .address(getCompetitionLocationAddress(competitionLocationEntity.getAddress()))
                .build();
    }

    private Address getCompetitionLocationAddress(AddressEntity addressEntity){
        return Address.builder()
                .addressId(addressEntity.getAddressId())
                .country(addressEntity.getCountry())
                .city(addressEntity.getCity())
                .postalCode(addressEntity.getPostalCode())
                .street(addressEntity.getStreet())
                .buildingNumber(addressEntity.getBuildingNumber())
                .additionalInfo(addressEntity.getAdditionalInfo())
                .build();
    }

    CompetitionEntity mapFromDomainToEntity(Competition competition);
}
