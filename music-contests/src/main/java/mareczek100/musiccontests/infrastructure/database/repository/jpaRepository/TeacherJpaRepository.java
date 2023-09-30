package mareczek100.musiccontests.infrastructure.database.repository.jpaRepository;


import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherJpaRepository extends JpaRepository<TeacherEntity, String> {


    Optional<TeacherEntity> findTeacherByPesel(String pesel);

    Optional<TeacherEntity> findTeacherByEmail(String email);
}
