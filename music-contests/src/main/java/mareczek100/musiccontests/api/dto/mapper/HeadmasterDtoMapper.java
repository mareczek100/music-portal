package mareczek100.musiccontests.api.dto.mapper;

import mareczek100.musiccontests.api.dto.HeadmasterDto;
import mareczek100.musiccontests.domain.Headmaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MusicSchoolDtoMapper.class)
public interface HeadmasterDtoMapper {

    @Mapping(source = "headmasterDto.musicSchool", target = "musicSchool",
            qualifiedByName = "musicSchoolMapFromDtoToDomain")
    Headmaster mapFromDtoToDomain(HeadmasterDto headmasterDto);

    @Mapping(source = "headmaster.musicSchool", target = "musicSchool",
            qualifiedByName = "musicSchoolMapFromDomainToDto")
    HeadmasterDto mapFromDomainToDto(Headmaster headmaster);
}
