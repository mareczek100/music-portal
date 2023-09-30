package mareczek100.musiccontests.domain;

import lombok.Builder;
import lombok.With;
import mareczek100.musiccontests.domain.enums.ClassLevel;
@With
@Builder
public record ApplicationForm(String applicationFormId,
                              Competition competition,
                              Teacher teacher,
                              Student student,
                              ClassLevel classLevel,
                              String performancePieces) {
}