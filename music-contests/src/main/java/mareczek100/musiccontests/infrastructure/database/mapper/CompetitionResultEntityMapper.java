package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.CompetitionResult;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionResultEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CompetitionEntityMapper.class, StudentEntityMapper.class})
public interface CompetitionResultEntityMapper {
    @Mapping(source = "competitionResultEntity.competition", target = "competition",
            qualifiedByName = "competitionMapFromEntityToDomain")
    @Mapping(source = "competitionResultEntity.student", target = "student",
            qualifiedByName = "studentMapFromEntityToDomain")
    CompetitionResult mapFromEntityToDomain(CompetitionResultEntity competitionResultEntity);
    
    CompetitionResultEntity mapFromDomainToEntity(CompetitionResult competitionResult);
}
