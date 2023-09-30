package mareczek100.musiccontests.api.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record MusicSchoolWithAddressDto(String musicSchoolId,
                                        String musicSchoolName,
                                        String musicSchoolPatron,
                                        Boolean musicSchoolPrimaryDegree,
                                        Boolean musicSchoolSecondaryDegree,
                                        String addressCountry,
                                        String addressCity,
                                        String addressPostalCode,
                                        String addressStreet,
                                        String addressBuildingNumber,
                                        String addressAdditionalInfo,
                                        HeadmasterDto headmaster) {
}
