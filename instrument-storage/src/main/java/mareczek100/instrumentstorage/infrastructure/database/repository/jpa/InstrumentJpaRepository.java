package mareczek100.instrumentstorage.infrastructure.database.repository.jpa;

import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InstrumentJpaRepository extends JpaRepository<InstrumentEntity, Integer> {
    Optional<InstrumentEntity> findInstrumentByName(String instrumentEntityName);
    @Query("""
            SELECT ins FROM InstrumentEntity ins
            JOIN FETCH ins.category cat
            WHERE cat.categoryName = :category
            """)
    List<InstrumentEntity> findInstrumentsByCategory(@Param("category") InstrumentCategoryName category);
}