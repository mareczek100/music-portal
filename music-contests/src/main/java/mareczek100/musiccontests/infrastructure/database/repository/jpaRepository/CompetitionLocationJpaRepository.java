package mareczek100.musiccontests.infrastructure.database.repository.jpaRepository;


import mareczek100.musiccontests.infrastructure.database.entity.CompetitionLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionLocationJpaRepository extends JpaRepository<CompetitionLocationEntity, String> {
}



