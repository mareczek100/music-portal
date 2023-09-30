package mareczek100.musiccontests.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.With;

@With
@Builder
public record HeadmasterDto(String headmasterId,
                            String name,
                            String surname,
                            @Email String email,
                            @Pattern(regexp = "^\\d{11}$") String pesel,
                            MusicSchoolWithAddressDto musicSchool) {
}
