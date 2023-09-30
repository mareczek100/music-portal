package mareczek100.musiccontests.domain;

import lombok.Builder;
import lombok.With;

import java.util.Set;

@With
@Builder
public record Teacher(String teacherId,
                      String name,
                      String surname,
                      String email,
                      String pesel,
                      String password,
                      MusicSchool musicSchool,
                      String instrument,
                      Set<Student> students,
                      Set<ApplicationForm> applicationForms) {
}
