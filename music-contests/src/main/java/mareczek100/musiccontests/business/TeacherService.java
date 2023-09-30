package mareczek100.musiccontests.business;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.HeadmasterRepositoryDAO;
import mareczek100.musiccontests.business.dao.TeacherRepositoryDAO;
import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.domain.Teacher;
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
public class TeacherService {

    private final TeacherRepositoryDAO teacherRepositoryDAO;
    private final HeadmasterRepositoryDAO headmasterRepositoryDAO;
    private final MusicSchoolService musicSchoolService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Teacher insertTeacher(Teacher teacher)
    {
        checkIfTeacherExistByPesel(teacher.pesel());

        if (headmasterRepositoryDAO.findHeadmasterByEmail(teacher.email()).isPresent()) {
            return teacherRepositoryDAO.insertTeacher(teacher);
        }

        RoleEntity.RoleName teacherRole = RoleEntity.RoleName.TEACHER;
        securityService.setRoleWhileCreateNewPortalUser(teacher.email(), teacher.password(), teacherRole);
        String encodedPesel = passwordEncoder.encode(teacher.pesel());

        MusicSchool musicSchool = teacher.musicSchool();
        if (!musicSchool.musicSchoolId().isEmpty()) {
            return teacherRepositoryDAO.insertTeacher(teacher.withPesel(encodedPesel));
        }

        MusicSchool insertedMusicSchool = musicSchoolService.insertMusicSchool(musicSchool);
        return teacherRepositoryDAO.insertTeacher(teacher
                .withMusicSchool(insertedMusicSchool)
                .withPesel(encodedPesel));
    }

    @Transactional
    public List<Teacher> findAllTeachers()
    {
        return teacherRepositoryDAO.findAllTeachers();
    }

    @Transactional
    public void checkIfTeacherExistByPesel(String pesel)
    {
        Optional<Teacher> teacherByPesel = findAllTeachers().stream()
                .filter(teacher -> BCrypt.checkpw(pesel, teacher.pesel()))
                .findAny();

        if (teacherByPesel.isPresent()) {
            throw new RuntimeException("Teacher with pesel [%s] already exist!".formatted(pesel));
        }
    }

    @Transactional
    public Teacher findTeacherByEmail(String email)
    {
        return teacherRepositoryDAO.findTeacherByEmail(email).orElseThrow(
                () -> new RuntimeException("Teacher with email [%s] doesn't exist!"
                        .formatted(email))
        );
    }

    @Transactional
    public void deleteTeacher(Teacher teacher) {
        teacherRepositoryDAO.deleteTeacher(teacher);
        securityService.deleteUserByUserName(teacher.email());
    }
}
