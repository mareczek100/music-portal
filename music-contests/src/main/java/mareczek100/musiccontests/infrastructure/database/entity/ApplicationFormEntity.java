package mareczek100.musiccontests.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import mareczek100.musiccontests.domain.enums.ClassLevel;
@With
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "application_form")
@EqualsAndHashCode(of = "applicationFormId")
public class ApplicationFormEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "application_form_id")
    private String applicationFormId;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private CompetitionEntity competition;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "class_level")
    private ClassLevel classLevel;

    @Column(name = "performance_pieces")
    private String performancePieces;

}
