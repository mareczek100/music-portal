package mareczek100.musiccontests.api.dto;

import lombok.Builder;
import mareczek100.musiccontests.domain.enums.ClassLevel;

@Builder
public record ApplicationFormDto(String applicationFormId,
                                 CompetitionWithLocationDto competition,
                                 TeacherDto teacher,
                                 StudentDto student,
                                 ClassLevel classLevel,
                                 String performancePieces) {
}
