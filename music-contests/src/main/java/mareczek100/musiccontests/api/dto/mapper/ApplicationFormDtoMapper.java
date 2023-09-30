package mareczek100.musiccontests.api.dto.mapper;

import mareczek100.musiccontests.api.dto.ApplicationFormDto;
import mareczek100.musiccontests.domain.ApplicationForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CompetitionDtoMapper.class)
public interface ApplicationFormDtoMapper {

    @Mapping(source = "applicationFormDto.competition", target = "competition",
            qualifiedByName = "competitionMapFromDtoToDomain")
    ApplicationForm mapFromDtoToDomain(ApplicationFormDto applicationFormDto);

    @Mapping(source = "applicationForm.competition", target = "competition",
            qualifiedByName = "competitionMapFromDomainToDto")
    ApplicationFormDto mapFromDomainToDto(ApplicationForm applicationForm);
}
