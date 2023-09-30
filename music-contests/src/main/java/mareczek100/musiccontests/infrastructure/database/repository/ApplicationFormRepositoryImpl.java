package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.ApplicationFormRepositoryDAO;
import mareczek100.musiccontests.domain.ApplicationForm;
import mareczek100.musiccontests.infrastructure.database.entity.ApplicationFormEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.ApplicationFormEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.ApplicationFormJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@AllArgsConstructor
public class ApplicationFormRepositoryImpl implements ApplicationFormRepositoryDAO {

    private final ApplicationFormJpaRepository applicationFormJpaRepository;
    private final ApplicationFormEntityMapper applicationFormEntityMapper;

    @Override
    public ApplicationForm insertApplicationForm(ApplicationForm applicationForm)
    {
        ApplicationFormEntity applicationFormEntity
                = applicationFormEntityMapper.mapFromDomainToEntity(applicationForm);
        ApplicationFormEntity savedApplicationFormEntity
                = applicationFormJpaRepository.saveAndFlush(applicationFormEntity);
        return applicationFormEntityMapper.mapFromEntityToDomain(savedApplicationFormEntity);
    }

    @Override
    public List<ApplicationForm> findAllApplicationForms()
    {
        Sort sort = Sort.by("competition.name").ascending();
        return applicationFormJpaRepository.findAll(sort).stream()
                .map(applicationFormEntityMapper::mapFromEntityToDomain)
                .toList();
    }

    @Override
    public void deleteApplicationForm(ApplicationForm applicationForm) {
        ApplicationFormEntity applicationFormEntity = applicationFormEntityMapper.mapFromDomainToEntity(applicationForm);
        applicationFormJpaRepository.delete(applicationFormEntity);
    }

}