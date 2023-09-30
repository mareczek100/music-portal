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
@Table(name = "music_school")
@EqualsAndHashCode(of = "musicSchoolId")
@ToString(exclude = {"headmaster", "teachers", "students"})
public class MusicSchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "music_school_id")
    private String musicSchoolId;

    @Column(name = "name")
    private String name;

    @Column(name = "patron")
    private String patron;

    @Column(name = "primary_degree")
    private Boolean primaryDegree;

    @Column(name = "secondary_degree")
    private Boolean secondaryDegree;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToOne(mappedBy = "musicSchool")
    private HeadmasterEntity headmaster;

    @OneToMany(mappedBy = "musicSchool")
    private Set<TeacherEntity> teachers;

    @OneToMany(mappedBy = "musicSchool")
    private Set<StudentEntity> students;
}
