package mareczek100.musiccontests.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.HeadmasterRepositoryDAO;
import mareczek100.musiccontests.domain.Headmaster;
import mareczek100.musiccontests.infrastructure.database.entity.HeadmasterEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.HeadmasterEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.HeadmasterJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@AllArgsConstructor
public class HeadmasterRepositoryImpl implements HeadmasterRepositoryDAO {

    private final HeadmasterJpaRepository headmasterJpaRepository;
    private final HeadmasterEntityMapper headmasterEntityMapper;


    @Override
    public Headmaster insertHeadmaster(Headmaster headmaster) {
        HeadmasterEntity headmasterEntity = headmasterEntityMapper.mapFromDomainToEntity(headmaster);
        HeadmasterEntity savedHeadmasterEntity = headmasterJpaRepository.saveAndFlush(headmasterEntity);
        return headmasterEntityMapper.mapFromEntityToDomain(savedHeadmasterEntity);
    }

    @Override
    public List<Headmaster> findAllHeadmasters()
    {
        Sort sort = Sort.by("surname").ascending();
        List<HeadmasterEntity> headmasterEntityList = headmasterJpaRepository.findAll(sort);
        return headmasterEntityList.stream()
                .map(headmasterEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public Optional<Headmaster> findHeadmasterByEmail(String email) {
        Optional<HeadmasterEntity> headmasterEntity = headmasterJpaRepository.findHeadmasterByEmail(email);
        return headmasterEntity.map(headmasterEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public Optional<Headmaster> findHeadmasterByPesel(String pesel) {
        Optional<HeadmasterEntity> headmasterEntity = headmasterJpaRepository.findHeadmasterByPesel(pesel);
        return headmasterEntity.map(headmasterEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public void deleteHeadmaster(Headmaster headmaster) {
        HeadmasterEntity headmasterEntity = headmasterEntityMapper.mapFromDomainToEntity(headmaster);
        headmasterJpaRepository.delete(headmasterEntity);
    }
}
