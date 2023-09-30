package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.CompetitionRepositoryDAO;
import mareczek100.musiccontests.domain.Competition;
import mareczek100.musiccontests.infrastructure.database.entity.CompetitionEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.CompetitionEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.CompetitionJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CompetitionRepositoryImpl implements CompetitionRepositoryDAO {

    private final CompetitionJpaRepository competitionJpaRepository;
    private final CompetitionEntityMapper competitionEntityMapper;

    @Override
    public Competition insertCompetition(Competition competition)
    {
        CompetitionEntity competitionEntity = competitionEntityMapper.mapFromDomainToEntity(competition);
        CompetitionEntity savedCompetitionEntity = competitionJpaRepository.saveAndFlush(competitionEntity);
        return competitionEntityMapper.mapFromEntityToDomain(savedCompetitionEntity);
    }

    @Override
    public List<Competition> findAllCompetitions()
    {
        Sort sort = Sort.by("beginning").ascending();
        return competitionJpaRepository.findAll(sort).stream()
                .map(competitionEntityMapper::mapFromEntityToDomain)
                .toList();
    }
    @Override
    public Page<Competition> findAllCompetitionsPageable(Pageable pageable)
    {
        return competitionJpaRepository.findAll(pageable).map(competitionEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public List<Competition> findCompetitionsByInstrument(String instrument)
    {
        return competitionJpaRepository.findCompetitionByInstrument(instrument).stream()
                .map(competitionEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public List<Competition> findCompetitionByOnline(Boolean online)
    {
        return competitionJpaRepository.findCompetitionByOnline(online).stream()
                .map(competitionEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public List<Competition> findCompetitionByPrimaryDegree(Boolean primaryDegree)
    {
        return competitionJpaRepository.findCompetitionByPrimaryDegree(primaryDegree).stream()
                .map(competitionEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public List<Competition> findCompetitionBySecondaryDegree(Boolean secondaryDegree)
    {
        return competitionJpaRepository.findCompetitionBySecondaryDegree(secondaryDegree).stream()
                .map(competitionEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    public List<Competition> findCompetitionsByFilters(
            String instrument, Boolean online, Boolean primaryDegree, Boolean secondaryDegree, String locationCity)
    {
        return competitionJpaRepository.findCompetitionsByFilters(
                        instrument, online, primaryDegree, secondaryDegree).stream()
                .filter(competitionEntity -> locationCity.equalsIgnoreCase(
                        competitionEntity.getCompetitionLocation().getAddress().getCity()))
                .map(competitionEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public Optional<Competition> findCompetitionById(String competitionId)
    {
        return competitionJpaRepository.findById(competitionId)
                .map(competitionEntityMapper::mapFromEntityToDomain);
    }

    @Override
    public void deleteCompetition(Competition competition) {
        CompetitionEntity competitionEntity = competitionEntityMapper.mapFromDomainToEntity(competition);
        competitionJpaRepository.delete(competitionEntity);
    }
}
