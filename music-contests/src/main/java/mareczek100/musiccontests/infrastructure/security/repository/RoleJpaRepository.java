package mareczek100.musiccontests.infrastructure.security.repository;

import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findRoleByRoleName(RoleEntity.RoleName roleName);
}