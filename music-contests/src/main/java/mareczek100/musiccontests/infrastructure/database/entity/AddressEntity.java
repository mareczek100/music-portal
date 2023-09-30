package mareczek100.musiccontests.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
@With
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
@EqualsAndHashCode(exclude = {"addressId", "competitionLocation", "musicSchool"})
@ToString(exclude = {"competitionLocation", "musicSchool"})
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    private String addressId;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street")
    private String street;

    @Column(name = "building_number")
    private String buildingNumber;

    @Column(name = "additional_info")
    private String additionalInfo;

    @OneToOne(mappedBy = "address")
    private CompetitionLocationEntity competitionLocation;

    @OneToOne(mappedBy = "address")
    private MusicSchoolEntity musicSchool;
}
