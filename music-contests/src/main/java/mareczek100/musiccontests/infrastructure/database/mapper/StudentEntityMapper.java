package mareczek100.musiccontests.infrastructure.database.mapper;

import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;
import mareczek100.musiccontests.infrastructure.database.entity.StudentEntity;
import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentEntityMapper {

    @Named("studentMapFromEntityToDomain")
    default Student mapFromEntityToDomain(StudentEntity studentEntity){
        return Student.builder()
                .studentId(studentEntity.getStudentId())
                .name(studentEntity.getName())
                .surname(studentEntity.getSurname())
                .email(studentEntity.getEmail())
                .pesel(studentEntity.getPesel())
                .classYear(studentEntity.getClassYear())
                .educationDuration(studentEntity.getEducationDuration())
                .musicSchoolDegree(studentEntity.getMusicSchoolDegree())
                .musicSchool(getMusicSchool(studentEntity.getMusicSchool()))
                .mainInstrument(studentEntity.getMainInstrument())
                .secondInstrument(studentEntity.getSecondInstrument())
                .teacher(getTeacher(studentEntity.getTeacher()))
                .build();
    }

    private MusicSchool getMusicSchool(MusicSchoolEntity musicSchoolEntity){
        return MusicSchool.builder()
                .musicSchoolId(musicSchoolEntity.getMusicSchoolId())
                .name(musicSchoolEntity.getName())
                .patron(musicSchoolEntity.getPatron())
                .primaryDegree(musicSchoolEntity.getPrimaryDegree())
                .secondaryDegree(musicSchoolEntity.getSecondaryDegree())
                .address(getAddress(musicSchoolEntity.getAddress()))
                .build();
    }
    private Address getAddress(AddressEntity addressEntity){
        return Address.builder()
                .addressId(addressEntity.getAddressId())
                .country(addressEntity.getCountry())
                .city(addressEntity.getCity())
                .postalCode(addressEntity.getPostalCode())
                .street(addressEntity.getStreet())
                .buildingNumber(addressEntity.getBuildingNumber())
                .additionalInfo(addressEntity.getAdditionalInfo())
                .build();
    }

    private Teacher getTeacher(TeacherEntity teacherEntity){
        return Teacher.builder()
                .teacherId(teacherEntity.getTeacherId())
                .name(teacherEntity.getName())
                .surname(teacherEntity.getSurname())
                .email(teacherEntity.getEmail())
                .pesel(teacherEntity.getPesel())
                .instrument(teacherEntity.getInstrument())
                .build();
    }

    @Mapping(target = "musicSchool.headmaster", ignore = true)
    @Mapping(target = "musicSchool.teachers", ignore = true)
    @Mapping(target = "musicSchool.students", ignore = true)
    StudentEntity mapFromDomainToEntity(Student student);
}