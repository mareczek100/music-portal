package mareczek100.instrumentstorage.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "instrument_category")
@EqualsAndHashCode(of = "categoryName")
@ToString(exclude = "instruments")
public class InstrumentCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrument_category_id")
    private Short instrumentCategoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name", unique = true)
    private InstrumentCategoryName categoryName;

    @OneToMany(mappedBy = "category")
    private Set<InstrumentEntity> instruments;
}