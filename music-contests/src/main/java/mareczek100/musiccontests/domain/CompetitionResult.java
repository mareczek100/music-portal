package mareczek100.musiccontests.domain;

import lombok.Builder;


@Builder
public record CompetitionResult(String competitionResultId,
                                Competition competition,
                                Student student,
                                String competitionPlace,
                                String specialAward) {
}
