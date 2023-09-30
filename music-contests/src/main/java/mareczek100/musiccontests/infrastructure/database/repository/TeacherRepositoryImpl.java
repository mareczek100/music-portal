package mareczek100.musiccontests.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.TeacherRepositoryDAO;
import mareczek100.musiccontests.domain.Teacher;
import mareczek100.musiccontests.infrastructure.database.entity.TeacherEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.TeacherEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.TeacherJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@AllArgsConstructor
public class TeacherRepositoryImpl implements TeacherRepositoryDAO {

    private final TeacherJpaRepository teacherJpaRepository;
    private final TeacherEntityMapper teacherEntityMapper;


    @Override
    public Teacher insertTeacher(Teacher teacher)
    {
        TeacherEntity teacherEntity = teacherEntityMapper.mapFromDomainToEntity(teacher);
        TeacherEntity savedTeacherEntity = teacherJpaRepository.saveAndFlush(teacherEntity);
        return teacherEntityMapper.mapFromEntityToDomain(savedTeacherEntity);
    }

    @Override
    public List<Teacher> findAllTeachers()
    {
        Sort sort = Sort.by("surname").ascending();
        return teacherJpaRepository.findAll(sort).stream()
                .map(teacherEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public Optional<Teacher> findTeacherByPesel(String pesel)
    {
        return teacherJpaRepository.findTeacherByPesel(pesel)
                .map(teacherEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public Optional<Teacher> findTeacherByEmail(String email)
    {
        return teacherJpaRepository.findTeacherByEmail(email)
                .map(teacherEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public void deleteTeacher(Teacher teacher) {
        TeacherEntity teacherEntity = teacherEntityMapper.mapFromDomainToEntity(teacher);
        teacherJpaRepository.delete(teacherEntity);
    }
}
