package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.CompetitionResultRepositoryDAO;
import mareczek100.musiccontests.domain.CompetitionResult;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionResultEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.CompetitionResultEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.CompetitionResultJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@AllArgsConstructor
public class CompetitionResultRepositoryImpl implements CompetitionResultRepositoryDAO {

    private final CompetitionResultJpaRepository competitionResultJpaRepository;
    private final CompetitionResultEntityMapper competitionResultEntityMapper;

    @Override
    public CompetitionResult insertCompetitionResult(CompetitionResult competitionResult)
    {
        CompetitionResultEntity competitionResultEntity
                = competitionResultEntityMapper.mapFromDomainToEntity(competitionResult);
        CompetitionResultEntity savedCompetitionResultEntity
                = competitionResultJpaRepository.saveAndFlush(competitionResultEntity);
        return competitionResultEntityMapper.mapFromEntityToDomain(savedCompetitionResultEntity);
    }

    @Override
    public List<CompetitionResult> findAllCompetitionResults()
    {
        Sort sort = Sort.by("competitionPlace").ascending();
        return competitionResultJpaRepository.findAll(sort).stream()
                .map(competitionResultEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public List<CompetitionResult> insertAllCompetitionResults(List<CompetitionResult> competitionResults)
    {
        List<CompetitionResultEntity> competitionResultEntities = competitionResults.stream()
                .map(competitionResultEntityMapper::mapFromDomainToEntity)
                .toList();

        return competitionResultJpaRepository.saveAllAndFlush(competitionResultEntities).stream()
                .map(competitionResultEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public void deleteCompetitionResult(CompetitionResult competitionResult) {
        CompetitionResultEntity competitionResultEntity
                = competitionResultEntityMapper.mapFromDomainToEntity(competitionResult);
        competitionResultJpaRepository.delete(competitionResultEntity);
    }
}
