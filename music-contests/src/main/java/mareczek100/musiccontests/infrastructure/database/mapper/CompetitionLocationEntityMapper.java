package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.CompetitionLocation;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionLocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompetitionLocationEntityMapper {
    
    @Mapping(target = "competitions", ignore = true)
    CompetitionLocation mapFromEntityToDomain(CompetitionLocationEntity competitionLocationEntity);

    CompetitionLocationEntity mapFromDomainToEntity(CompetitionLocation competitionLocation);
}
