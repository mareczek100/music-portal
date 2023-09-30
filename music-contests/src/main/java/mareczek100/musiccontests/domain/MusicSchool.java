package mareczek100.musiccontests.domain;

import lombok.Builder;
import lombok.With;

import java.util.Set;
@With
@Builder
public record MusicSchool(String musicSchoolId,
                          String name,
                          String patron,
                          Boolean primaryDegree,
                          Boolean secondaryDegree,
                          Address address,
                          Headmaster headmaster,
                          Set<Teacher> teachers,
                          Set<Student> students) {
}
