package mareczek100.musiccontests.domain;

import lombok.Builder;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.Set;
@With
@Builder
public record Competition (String competitionId,
                           String name,
                           String instrument,
                           Boolean online,
                           Boolean primaryDegree,
                           Boolean secondaryDegree,
                           OffsetDateTime beginning,
                           OffsetDateTime end,
                           OffsetDateTime resultAnnouncement,
                           OffsetDateTime applicationDeadline,
                           String requirementsDescription,
                           Headmaster headmaster,
                           CompetitionLocation competitionLocation,
                           Boolean finished,
                           Set<ApplicationForm> applicationForms,
                           Set<CompetitionResult> competitionResults){
}
