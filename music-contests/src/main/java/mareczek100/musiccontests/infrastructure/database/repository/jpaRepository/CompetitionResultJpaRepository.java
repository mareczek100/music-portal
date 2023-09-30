package mareczek100.musiccontests.infrastructure.database.repository.jpaRepository;

import mareczek100.musiccontests.infrastructure.database.entity.CompetitionResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompetitionResultJpaRepository extends JpaRepository<CompetitionResultEntity, String> {


}
