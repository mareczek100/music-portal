package mareczek100.musiccontests.api.dto.mapper;

import mareczek100.musiccontests.api.dto.CompetitionResultDto;
import mareczek100.musiccontests.domain.CompetitionResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CompetitionDtoMapper.class, StudentDtoMapper.class})
public interface CompetitionResultDtoMapper {

    @Mapping(source = "competitionResultDto.competition", target = "competition",
            qualifiedByName = "competitionMapFromDtoToDomain")
    @Mapping(source = "competitionResultDto.student", target = "student",
            qualifiedByName = "studentMapFromDtoToDomain")
    CompetitionResult mapFromDtoToDomain(CompetitionResultDto competitionResultDto);
    @Mapping(source = "competitionResult.competition", target = "competition",
            qualifiedByName = "competitionMapFromDomainToDto")
    @Mapping(source = "competitionResult.student", target = "student",
            qualifiedByName = "studentMapFromDomainToDto")
    CompetitionResultDto mapFromDomainToDto(CompetitionResult competitionResult);
}
