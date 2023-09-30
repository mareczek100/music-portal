package mareczek100.musiccontests.api.dto.dto_rest_support;

import lombok.Builder;
import lombok.With;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static mareczek100.musiccontests.api.dto.CompetitionWithLocationDto.DATE_TIME_FORMAT;

@With
@Builder
public record CompetitionWithLocationRestDto(String competitionId,
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
                                             String competitionLocationName,
                                             String addressCountry,
                                             String addressCity,
                                             String addressPostalCode,
                                             String addressStreet,
                                             String addressBuildingNumber,
                                             String addressAdditionalInfo,
                                             Boolean competitionFinished)
{
}
