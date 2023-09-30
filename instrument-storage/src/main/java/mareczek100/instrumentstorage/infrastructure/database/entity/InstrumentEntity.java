package mareczek100.instrumentstorage.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@With
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "instrument")
@EqualsAndHashCode(of = "name")
public class InstrumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrument_id")
    private Integer instrumentId;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "primary_school_degree")
    private Boolean primarySchoolDegree;

    @Column(name = "secondary_school_degree")
    private Boolean secondarySchoolDegree;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Fetch(FetchMode.JOIN)
    private InstrumentCategoryEntity category;
}