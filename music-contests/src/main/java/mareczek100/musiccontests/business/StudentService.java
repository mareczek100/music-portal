package mareczek100.musiccontests.business;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.StudentRepositoryDAO;
import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import mareczek100.musiccontests.infrastructure.security.SecurityService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepositoryDAO studentRepositoryDAO;
    private final MusicSchoolService musicSchoolService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Student insertStudent(Student student) {
        checkIfStudentExistByPesel(student.pesel());

        RoleEntity.RoleName studentRole = RoleEntity.RoleName.STUDENT;
        securityService.setRoleWhileCreateNewPortalUser(student.email(), student.password(), studentRole);
        String encodedPesel = passwordEncoder.encode(student.pesel());
        MusicSchool musicSchool = student.musicSchool();

        if (!musicSchool.musicSchoolId().isEmpty()) {
            return studentRepositoryDAO.insertStudent(student
                    .withMusicSchool(musicSchool)
                    .withPesel(encodedPesel));
        }

        MusicSchool insertedMusicSchool = musicSchoolService.insertMusicSchool(musicSchool);
        return studentRepositoryDAO.insertStudent(student
                .withMusicSchool(insertedMusicSchool)
                .withPesel(encodedPesel));
    }

    @Transactional
    public List<Student> findAllStudents() {
        return studentRepositoryDAO.findAllStudents();
    }

    @Transactional
    public void checkIfStudentExistByPesel(String pesel)
    {
        Optional<Student> studentByPesel = findAllStudents().stream()
                .filter(student -> BCrypt.checkpw(pesel, student.pesel()))
                .findAny();

        if (studentByPesel.isPresent()) {
            throw new RuntimeException("Student with pesel [%s] already exist!".formatted(pesel));
        }
    }

    @Transactional
    public Student findStudentByEmail(String email) {
        return studentRepositoryDAO.findStudentByEmail(email).orElseThrow(
                () -> new RuntimeException("Student with email [%s] doesn't exist!"
                        .formatted(email))
        );
    }

    @Transactional
    public Student findStudentById(String studentId) {
        return studentRepositoryDAO.findStudentById(studentId).orElseThrow(
                () -> new RuntimeException("Student with id [%s] doesn't exist!"
                        .formatted(studentId))
        );
    }

    @Transactional
    public void deleteStudent(Student student) {
        studentRepositoryDAO.deleteStudent(student);
        securityService.deleteUserByUserName(student.email());
    }

}