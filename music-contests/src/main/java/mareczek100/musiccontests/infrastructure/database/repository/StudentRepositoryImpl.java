package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.StudentRepositoryDAO;
import mareczek100.musiccontests.domain.Student;
import mareczek100.musiccontests.infrastructure.database.entity.StudentEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.StudentEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.StudentJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@AllArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryDAO {

    private final StudentJpaRepository studentJpaRepository;
    private final StudentEntityMapper studentEntityMapper;


    @Override
    public Student insertStudent(Student student)
    {
        StudentEntity studentEntity = studentEntityMapper.mapFromDomainToEntity(student);
        StudentEntity savedStudentEntity = studentJpaRepository.saveAndFlush(studentEntity);
        return studentEntityMapper.mapFromEntityToDomain(savedStudentEntity);
    }

    @Override
    public List<Student> findAllStudents()
    {
        Sort sort = Sort.by("surname").ascending();
        return studentJpaRepository.findAll(sort).stream()
                .map(studentEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public Optional<Student> findStudentByPesel(String pesel)
    {
        return studentJpaRepository.findStudentByPesel(pesel)
                .map(studentEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public Optional<Student> findStudentByEmail(String email) {
        return studentJpaRepository.findStudentByEmail(email)
                .map(studentEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public Optional<Student> findStudentById(String studentId) {
        return studentJpaRepository.findById(studentId)
                .map(studentEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public void deleteStudent(Student student) {
        StudentEntity studentEntity = studentEntityMapper.mapFromDomainToEntity(student);
        studentJpaRepository.delete(studentEntity);
    }
}