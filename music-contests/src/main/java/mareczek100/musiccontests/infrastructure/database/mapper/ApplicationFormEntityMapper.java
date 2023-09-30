package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.ApplicationForm;
import mareczek100.musiccontests.infrastructure.database.entity.ApplicationFormEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CompetitionEntityMapper.class, StudentEntityMapper.class, TeacherEntityMapper.class})
public interface ApplicationFormEntityMapper {

    @Mapping(source = "applicationFormEntity.competition", target = "competition",
            qualifiedByName = "competitionMapFromEntityToDomain")
    @Mapping(source = "applicationFormEntity.teacher", target = "teacher",
            qualifiedByName = "teacherMapFromEntityToDomain")
    @Mapping(source = "applicationFormEntity.student", target = "student",
            qualifiedByName = "studentMapFromEntityToDomain")
//    @Mapping(target = "teacher.musicSchool", ignore = true)
//    @Mapping(target = "teacher.students", ignore = true)
//    @Mapping(target = "teacher.applicationForms", ignore = true)
//    @Mapping(target = "student.musicSchool", ignore = true)
//    @Mapping(target = "student.teacher", ignore = true)
//    @Mapping(target = "student.competitionResults", ignore = true)
//    @Mapping(target = "student.applicationForms", ignore = true)
    ApplicationForm mapFromEntityToDomain(ApplicationFormEntity applicationFormEntity);

    ApplicationFormEntity mapFromDomainToEntity(ApplicationForm applicationForm);
}
