package mareczek100.musiccontests.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.MusicSchoolRepositoryDAO;
import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.MusicSchoolEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.MusicSchoolJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@AllArgsConstructor
public class MusicSchoolRepositoryImpl implements MusicSchoolRepositoryDAO {

    private final MusicSchoolJpaRepository musicSchoolJpaRepository;
    private final MusicSchoolEntityMapper musicSchoolEntityMapper;
    @Override
    public MusicSchool insertMusicSchool(MusicSchool musicSchool)
    {
        MusicSchoolEntity musicSchoolEntity = musicSchoolEntityMapper.mapFromDomainToEntity(musicSchool);
        MusicSchoolEntity savedMusicSchoolEntity = musicSchoolJpaRepository.saveAndFlush(musicSchoolEntity);
        return musicSchoolEntityMapper.mapFromEntityToDomain(savedMusicSchoolEntity);
    }

    @Override
    public List<MusicSchool> findAllMusicSchools()
    {
        Sort sort = Sort.by("address.city").ascending();
        return musicSchoolJpaRepository.findAll(sort).stream()
                .map(musicSchoolEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public Optional<MusicSchool> findMusicSchoolByPatron(String patron)
    {
        return musicSchoolJpaRepository.findMusicSchoolByPatron(patron)
                .map(musicSchoolEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public Optional<MusicSchool> findMusicSchoolById(String musicSchoolId) {
        return musicSchoolJpaRepository.findById(musicSchoolId)
                .map(musicSchoolEntityMapper::mapFromEntityToDomain);
    }
}
