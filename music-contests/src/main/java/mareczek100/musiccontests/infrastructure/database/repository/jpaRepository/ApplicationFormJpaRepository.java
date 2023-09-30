package mareczek100.musiccontests.infrastructure.database.repository.jpaRepository;

import mareczek100.musiccontests.infrastructure.database.entity.ApplicationFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationFormJpaRepository extends JpaRepository<ApplicationFormEntity, String> {


}
