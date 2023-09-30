package mareczek100.musiccontests.api.dto;

import lombok.Builder;
import lombok.With;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@With
@Builder
public record CompetitionWithLocationDto(String competitionId,
                                         String competitionName,
                                         String competitionInstrument,
                                         Boolean competitionOnline,
                                         Boolean competitionPrimaryDegree,
                                         Boolean competitionSecondaryDegree,
                                         @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                         LocalDateTime competitionBeginning,
                                         @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                         LocalDateTime competitionEnd,
                                         @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                         LocalDateTime competitionResultAnnouncement,
                                         @DateTimeFormat(pattern = DATE_TIME_FORMAT)
                                         LocalDateTime competitionApplicationDeadline,
                                         String competitionRequirementsDescription,
                                         HeadmasterDto competitionOrganizer,
                                         String competitionLocationName,
                                         String addressCountry,
                                         String addressCity,
                                         String addressPostalCode,
                                         String addressStreet,
                                         String addressBuildingNumber,
                                         String addressAdditionalInfo,
                                         Boolean competitionFinished)
{
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
}
