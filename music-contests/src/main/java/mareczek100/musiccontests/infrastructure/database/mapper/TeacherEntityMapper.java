package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeacherEntityMapper {

    @Mapping(target = "students", ignore = true)
    @Mapping(target = "applicationForms", ignore = true)
    @Mapping(target = "musicSchool.headmaster", ignore = true)
    @Mapping(target = "musicSchool.teachers", ignore = true)
    @Mapping(target = "musicSchool.students", ignore = true)
    @Named("teacherMapFromEntityToDomain")
    Teacher mapFromEntityToDomain(TeacherEntity teacherEntity);

    TeacherEntity mapFromDomainToEntity(Teacher teacher);
}
