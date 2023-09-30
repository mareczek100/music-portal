package mareczek100.musiccontests.infrastructure.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import mareczek100.musiccontests.domain.enums.ClassLevel;
import mareczek100.musiccontests.domain.enums.Degree;
import mareczek100.musiccontests.domain.enums.EducationProgram;

import java.util.Set;
@With
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
@EqualsAndHashCode(of = "pesel")
@ToString(exclude = {"competitionResults", "applicationForms"})
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "pesel", unique = true)
    private String pesel;

    @Enumerated(EnumType.STRING)
    @Column(name = "class")
    private ClassLevel classYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "education_duration")
    private EducationProgram educationDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "music_school_degree")
    private Degree musicSchoolDegree;

    @ManyToOne
    @JoinColumn(name = "music_school_id")
    private MusicSchoolEntity musicSchool;

    @Column(name = "main_instrument")
    private String mainInstrument;

    @Column(name = "second_instrument")
    private String secondInstrument;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;

    @OneToMany(mappedBy = "student")
    private Set<CompetitionResultEntity> competitionResults;

    @OneToMany(mappedBy = "student")
    private Set<ApplicationFormEntity> applicationForms;

}