package mareczek100.musiccontests.infrastructure.security;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "music_contests_role")
@EqualsAndHashCode(of = "roleName")
@ToString(exclude = "users")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id")
    private UUID roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", unique = true)
    private RoleName roleName;

    @OneToMany(mappedBy = "role")
    private Set<MusicContestsPortalUserEntity> users;

    public enum RoleName {
        ADMIN,
        HEADMASTER,
        TEACHER,
        STUDENT
    }
}
