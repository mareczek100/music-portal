package mareczek100.musiccontests.infrastructure.security.repository;

import mareczek100.musiccontests.infrastructure.security.MusicContestsPortalUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MusicContestsPortalUserJpaRepository extends JpaRepository<MusicContestsPortalUserEntity, UUID> {

    Optional<MusicContestsPortalUserEntity> findByUserName(String userName);
}
