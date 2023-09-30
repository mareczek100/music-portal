package mareczek100.musiccontests.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;
@With
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competition")
@EqualsAndHashCode(of = "competitionId")
@ToString(exclude = {"applicationForms", "competitionResults"})
public class CompetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "competition_id")
    private String competitionId;

    @Column(name = "name")
    private String name;

    @Column(name = "instrument")
    private String instrument;

    @Column(name = "online")
    private Boolean online;

    @Column(name = "primary_degree")
    private Boolean primaryDegree;

    @Column(name = "secondary_degree")
    private Boolean secondaryDegree;

    @Column(name = "beginning_date_time")
    private OffsetDateTime beginning;

    @Column(name = "end_date_time")
    private OffsetDateTime end;

    @Column(name = "result_announcement")
    private OffsetDateTime resultAnnouncement;

    @Column(name = "application_deadline")
    private OffsetDateTime applicationDeadline;

    @Column(name = "requirements_description")
    private String requirementsDescription;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private HeadmasterEntity headmaster;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private CompetitionLocationEntity competitionLocation;

    @Column(name = "finished")
    private Boolean finished;

    @OneToMany(mappedBy = "competition")
    private Set<ApplicationFormEntity> applicationForms;

    @OneToMany(mappedBy = "competition")
    private Set<CompetitionResultEntity> competitionResults;
}
