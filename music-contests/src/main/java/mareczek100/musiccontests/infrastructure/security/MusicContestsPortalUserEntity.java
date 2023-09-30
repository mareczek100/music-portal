package mareczek100.musiccontests.infrastructure.security;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.UUID;
@With
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "music_contests_user")
@EqualsAndHashCode(of = "userName")
public class MusicContestsPortalUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    UUID userId;

    @Email
    @Column(name = "user_name", unique = true)
    String userName;

    @Column(name = "password")
    String password;

    @Column(name = "active")
    Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "role_id")
    RoleEntity role;
}