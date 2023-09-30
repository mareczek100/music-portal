package mareczek100.musiccontests.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.With;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.domain.enums.Degree;
import mareczek100.musiccontests.domain.enums.EducationProgram;

@With
@Builder
public record StudentDto(String studentId,
                         String name,
                         String surname,
                         @Email String email,
                         @Pattern(regexp = "^\\d{11}$") String pesel,
                         ClassLevel classYear,
                         EducationProgram educationDuration,
                         Degree musicSchoolDegree,
                         MusicSchoolWithAddressDto musicSchool,
                         String mainInstrument,
                         String secondInstrument,
                         TeacherDto teacher) {
}