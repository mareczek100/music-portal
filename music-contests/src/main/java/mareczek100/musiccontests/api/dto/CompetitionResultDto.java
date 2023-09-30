package mareczek100.musiccontests.api.dto;

import lombok.Builder;


@Builder
public record CompetitionResultDto(String competitionResultId,
                                   CompetitionWithLocationDto competition,
                                   StudentDto student,
                                   String competitionPlace,
                                   String specialAward) {
}
