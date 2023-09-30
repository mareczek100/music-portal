package mareczek100.musiccontests.api.dto.mapper;

import mareczek100.musiccontests.api.dto.TeacherDto;
import mareczek100.musiccontests.domain.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MusicSchoolDtoMapper.class)
public interface TeacherDtoMapper {

    @Mapping(source = "teacherDto.musicSchool", target = "musicSchool",
            qualifiedByName = "musicSchoolMapFromDtoToDomain")
    Teacher mapFromDtoToDomain(TeacherDto teacherDto);
    @Mapping(source = "teacher.musicSchool", target = "musicSchool",
            qualifiedByName = "musicSchoolMapFromDomainToDto")
    TeacherDto mapFromDomainToDto(Teacher teacher);
}
