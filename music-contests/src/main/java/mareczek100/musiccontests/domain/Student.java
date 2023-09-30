package mareczek100.musiccontests.domain;

import lombok.Builder;
import lombok.With;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.domain.enums.Degree;
import mareczek100.musiccontests.domain.enums.EducationProgram;

import java.util.Set;

@With
@Builder
public record Student(String studentId,
                      String name,
                      String surname,
                      String email,
                      String pesel,
                      String password,
                      ClassLevel classYear,
                      EducationProgram educationDuration,
                      Degree musicSchoolDegree,
                      MusicSchool musicSchool,
                      String mainInstrument,
                      String secondInstrument,
                      Teacher teacher,
                      Set<CompetitionResult> competitionResults,
                      Set<ApplicationForm> applicationForms) {
}