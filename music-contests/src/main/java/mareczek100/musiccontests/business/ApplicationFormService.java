package mareczek100.musiccontests.business;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.ApplicationFormRepositoryDAO;
import mareczek100.musiccontests.domain.ApplicationForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationFormService {
    private final ApplicationFormRepositoryDAO applicationFormRepositoryDAO;

    @Transactional
    public ApplicationForm insertApplicationForm(ApplicationForm applicationForm) {
        return applicationFormRepositoryDAO.insertApplicationForm(applicationForm);
    }
    @Transactional
    public List<ApplicationForm> findAllApplicationForms() {
        return applicationFormRepositoryDAO.findAllApplicationForms();
    }
    @Transactional
    public void deleteApplicationForm(ApplicationForm applicationForm) {
        applicationFormRepositoryDAO.deleteApplicationForm(applicationForm);
    }
}
