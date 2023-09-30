package mareczek100.musiccontests.infrastructure.database.repository.jpaRepository;

import mareczek100.musiccontests.infrastructure.database.entity.CompetitionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CompetitionJpaRepository extends JpaRepository<CompetitionEntity, String> {

    List<CompetitionEntity> findCompetitionByInstrument(String instrument);

    List<CompetitionEntity> findCompetitionByOnline(Boolean online);

    List<CompetitionEntity> findCompetitionByPrimaryDegree(Boolean primaryDegree);

    List<CompetitionEntity> findCompetitionBySecondaryDegree(Boolean secondaryDegree);
    @Query("""
    SELECT com FROM CompetitionEntity com
    WHERE com.finished = false""")
    Page<CompetitionEntity> findAll(@NonNull Pageable pageable);
    @Query("""
    SELECT com FROM CompetitionEntity com
    WHERE com.instrument = :instrument
    AND com.online = :online
    AND com.primaryDegree = :primaryDegree
    AND com.secondaryDegree = :secondaryDegree
    """)
    List<CompetitionEntity> findCompetitionsByFilters(@Param("instrument") String instrument,
                                                      @Param("online") Boolean online,
                                                      @Param("primaryDegree") Boolean primaryDegree,
                                                      @Param("secondaryDegree") Boolean secondaryDegree);
}