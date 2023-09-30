package mareczek100.musiccontests.business.dao;

import mareczek100.musiccontests.domain.ApplicationForm;

import java.util.List;


public interface ApplicationFormRepositoryDAO {


    ApplicationForm insertApplicationForm(ApplicationForm applicationForm);

    List<ApplicationForm> findAllApplicationForms();

    void deleteApplicationForm(ApplicationForm applicationForm);
}
