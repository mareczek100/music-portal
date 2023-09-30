package mareczek100.musiccontests.infrastructure.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.Set;
@With
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher")
@EqualsAndHashCode(of = "pesel")
@ToString(exclude = {"students", "applicationForms"})
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "pesel", unique = true)
    private String pesel;

    @ManyToOne
    @JoinColumn(name = "music_school_id")
    private MusicSchoolEntity musicSchool;

    @Column(name = "instrument")
    private String instrument;

    @OneToMany(mappedBy = "teacher")
    private Set<StudentEntity> students;

    @OneToMany(mappedBy = "teacher")
    private Set<ApplicationFormEntity> applicationForms;
}
