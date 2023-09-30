package mareczek100.musiccontests.api.dto.dto_class_support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetitionResultListDto {

    private List<CompetitionResultSupport> competitionResultsSupport;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompetitionResultSupport {
        private String studentId;
        private String competitionPlace;
        private String specialAward;
    }
}