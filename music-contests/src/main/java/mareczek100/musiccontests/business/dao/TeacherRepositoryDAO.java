package mareczek100.musiccontests.business.dao;

import mareczek100.musiccontests.domain.Teacher;

import java.util.List;
import java.util.Optional;


public interface TeacherRepositoryDAO {


    Teacher insertTeacher(Teacher teacher);

    List<Teacher> findAllTeachers();

    Optional<Teacher> findTeacherByPesel(String pesel);

    Optional<Teacher> findTeacherByEmail(String email);
    void deleteTeacher(Teacher teacher);
}
