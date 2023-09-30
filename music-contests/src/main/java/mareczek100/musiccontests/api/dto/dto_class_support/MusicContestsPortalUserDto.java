package mareczek100.musiccontests.api.dto.dto_class_support;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;

@Data
@Builder
public class MusicContestsPortalUserDto {

    private String name;

    private String surname;

    @Email
    private String email;

    @Pattern(regexp = "^\\d{11}$")
    private String pesel;

    @Pattern(regexp = "^[A-Z](.{7,})$")
    private String password;

    private RoleEntity.RoleName role;

}