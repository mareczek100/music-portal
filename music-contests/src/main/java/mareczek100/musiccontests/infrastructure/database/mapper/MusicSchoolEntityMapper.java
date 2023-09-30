package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MusicSchoolEntityMapper {

    @Mapping(target = "headmaster", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "students", ignore = true)
    MusicSchool mapFromEntityToDomain(MusicSchoolEntity musicSchoolEntity);
    @Mapping(target = "headmaster", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "students", ignore = true)
    MusicSchoolEntity mapFromDomainToEntity(MusicSchool musicSchool);
}
