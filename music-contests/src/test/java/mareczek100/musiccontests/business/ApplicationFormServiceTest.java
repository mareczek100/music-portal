package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.ApplicationFormRepositoryDAO;
import mareczek100.musiccontests.domain.ApplicationForm;
import mareczek100.musiccontests.test_data_storage.application_form.ApplicationFormDomainTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ApplicationFormServiceTest {

    @Mock
    private ApplicationFormRepositoryDAO applicationFormRepositoryDAO;
    @InjectMocks
    private ApplicationFormService applicationFormService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(applicationFormRepositoryDAO);
        Assertions.assertNotNull(applicationFormService);
    }

    @Test
    void insertApplicationForm() {
        //given
        ApplicationForm applicationFormToSave = ApplicationFormDomainTestData.applicationFormToSave1();
        ApplicationForm applicationFormSaved = ApplicationFormDomainTestData.applicationFormSaved1();

        //when
        Mockito.when(applicationFormRepositoryDAO.insertApplicationForm(applicationFormToSave))
                .thenReturn(applicationFormSaved);
        ApplicationForm insertedApplicationForm = applicationFormService.insertApplicationForm(applicationFormToSave);

        //then
        Assertions.assertEquals(insertedApplicationForm, applicationFormSaved);
    }

    @Test
    void findAllApplicationForms() {
        //given
        ApplicationForm applicationFormSaved1 = ApplicationFormDomainTestData.applicationFormSaved1();
        ApplicationForm applicationFormSaved2 = ApplicationFormDomainTestData.applicationFormSaved2();
        ApplicationForm applicationFormSaved3 = ApplicationFormDomainTestData.applicationFormSaved3();
        List<ApplicationForm> applicationFormsSaved
                = List.of(applicationFormSaved1, applicationFormSaved2, applicationFormSaved3);

        //when
        Mockito.when(applicationFormRepositoryDAO.findAllApplicationForms()).thenReturn(applicationFormsSaved);
        List<ApplicationForm> applicationFormList = applicationFormService.findAllApplicationForms();

        //then
        Assertions.assertEquals(applicationFormList, applicationFormsSaved);
    }

    @Test
    void deleteApplicationForm() {
        //given
        ApplicationForm applicationFormSaved1 = ApplicationFormDomainTestData.applicationFormSaved1();
        ApplicationForm applicationFormSaved2 = ApplicationFormDomainTestData.applicationFormSaved2();
        ApplicationForm applicationFormSaved3 = ApplicationFormDomainTestData.applicationFormSaved3();
        List<ApplicationForm> applicationFormsSaved = new ArrayList<>(
                List.of(applicationFormSaved1, applicationFormSaved2, applicationFormSaved3));

        //when
        Mockito.when(applicationFormRepositoryDAO.findAllApplicationForms()).thenReturn(applicationFormsSaved);
        List<ApplicationForm> applicationFormListBefore = applicationFormService.findAllApplicationForms();
        Assertions.assertEquals(applicationFormListBefore, applicationFormsSaved);
        applicationFormService.deleteApplicationForm(applicationFormSaved1);
        applicationFormsSaved.remove(applicationFormSaved1);
        List<ApplicationForm> applicationFormListAfter = applicationFormService.findAllApplicationForms();

        //then
        org.assertj.core.api.Assertions.assertThatCollection(applicationFormListAfter)
                .doesNotContain(applicationFormSaved1);
        Mockito.verify(applicationFormRepositoryDAO).deleteApplicationForm(applicationFormSaved1);
    }
}