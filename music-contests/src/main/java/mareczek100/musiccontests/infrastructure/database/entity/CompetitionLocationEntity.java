package mareczek100.musiccontests.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@With
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competition_location")
@EqualsAndHashCode(exclude = {"competitionLocationId", "competitions"})
@ToString(exclude = "competitions")
public class CompetitionLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "competition_location_id")
    private String competitionLocationId;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToMany(mappedBy = "competitionLocation")
    private Set<CompetitionEntity> competitions;
}
