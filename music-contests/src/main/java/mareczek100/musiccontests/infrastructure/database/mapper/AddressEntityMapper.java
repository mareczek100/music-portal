package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressEntityMapper {

    Address mapFromEntityToDomain(AddressEntity addressEntity);

    AddressEntity mapFromDomainToEntity(Address address);
}
