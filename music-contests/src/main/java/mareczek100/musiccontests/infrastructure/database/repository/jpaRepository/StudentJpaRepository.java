package mareczek100.musiccontests.infrastructure.database.repository.jpaRepository;

import mareczek100.musiccontests.infrastructure.database.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<StudentEntity, String> {


    Optional<StudentEntity> findStudentByPesel(String pesel);

    Optional<StudentEntity> findStudentByEmail(String email);
}