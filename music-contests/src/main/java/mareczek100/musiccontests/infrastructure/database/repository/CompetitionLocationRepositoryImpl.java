package mareczek100.musiccontests.infrastructure.database.repository;


import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.CompetitionLocationRepositoryDAO;
import mareczek100.musiccontests.domain.CompetitionLocation;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionLocationEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.CompetitionLocationEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.CompetitionLocationJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@AllArgsConstructor
public class CompetitionLocationRepositoryImpl implements CompetitionLocationRepositoryDAO {

    private final CompetitionLocationJpaRepository competitionLocationJpaRepository;
    private final CompetitionLocationEntityMapper competitionLocationEntityMapper;

    @Override
    public CompetitionLocation insertCompetitionLocation(CompetitionLocation competitionLocation)
    {
        CompetitionLocationEntity competitionLocationEntity
                = competitionLocationEntityMapper.mapFromDomainToEntity(competitionLocation);
        CompetitionLocationEntity savedCompetitionLocationEntity
                = competitionLocationJpaRepository.saveAndFlush(competitionLocationEntity);
        return competitionLocationEntityMapper.mapFromEntityToDomain(savedCompetitionLocationEntity);
    }

    @Override
    public List<CompetitionLocation> findAllCompetitionLocations()
    {
        return competitionLocationJpaRepository.findAll().stream()
                .map(competitionLocationEntityMapper::mapFromEntityToDomain)
                .toList();
    }
}



