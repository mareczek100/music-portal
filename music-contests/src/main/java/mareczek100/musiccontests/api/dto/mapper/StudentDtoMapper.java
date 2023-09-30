package mareczek100.musiccontests.api.dto.mapper;

import mareczek100.musiccontests.api.dto.StudentDto;
import mareczek100.musiccontests.domain.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = MusicSchoolDtoMapper.class)
public interface StudentDtoMapper {

    @Mapping(source = "studentDto.musicSchool", target = "musicSchool",
            qualifiedByName = "musicSchoolMapFromDtoToDomain")
    @Named("studentMapFromDtoToDomain")
    Student mapFromDtoToDomain(StudentDto studentDto);
    @Mapping(source = "student.musicSchool", target = "musicSchool",
            qualifiedByName = "musicSchoolMapFromDomainToDto")
    @Named("studentMapFromDomainToDto")
    StudentDto mapFromDomainToDto(Student student);
}