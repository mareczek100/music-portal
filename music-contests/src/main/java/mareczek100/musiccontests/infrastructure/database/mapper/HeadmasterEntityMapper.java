package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.domain.Headmaster;
import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import mareczek100.musiccontests.infrastructure.database.entity.HeadmasterEntity;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HeadmasterEntityMapper {


    default Headmaster mapFromEntityToDomain(HeadmasterEntity headmasterEntity){
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

    @Mapping(target = "competitions", ignore = true)
    HeadmasterEntity mapFromDomainToEntity(Headmaster headmaster);
}
