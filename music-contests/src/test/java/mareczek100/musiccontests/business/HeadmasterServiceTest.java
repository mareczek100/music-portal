package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.HeadmasterRepositoryDAO;
import mareczek100.musiccontests.domain.Headmaster;
import mareczek100.musiccontests.infrastructure.security.MusicContestsPortalUserEntity;
import mareczek100.musiccontests.infrastructure.security.RoleEntity;
import mareczek100.musiccontests.infrastructure.security.SecurityService;
import mareczek100.musiccontests.test_data_storage.headmaster.HeadmasterDomainTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class HeadmasterServiceTest {

    @Mock
    private HeadmasterRepositoryDAO headmasterRepositoryDAO;
    @Mock
    private MusicSchoolService musicSchoolService;
    @Mock
    private SecurityService securityService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private HeadmasterService headmasterService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(headmasterRepositoryDAO);
        Assertions.assertNotNull(musicSchoolService);
        Assertions.assertNotNull(securityService);
        Assertions.assertNotNull(passwordEncoder);
        Assertions.assertNotNull(headmasterService);
    }

    @Test
    void insertHeadmaster() {
        //given
        Headmaster headmasterToSave = HeadmasterDomainTestData.headmasterToSave1();
        Headmaster headmasterSaved = HeadmasterDomainTestData.headmasterSaved1();
        String encodedPesel = headmasterSaved.pesel();
        List<Headmaster> headmasterList = Collections.emptyList();
        RoleEntity.RoleName headmasterRole = RoleEntity.RoleName.HEADMASTER;
        MusicContestsPortalUserEntity headmasterPortalUserEntity
                = MusicContestsPortalUserEntity.builder()
                .userId(UUID.fromString("94a7025a-59f2-4a9c-b1ab-a573afd15bb8"))
                .userName(headmasterToSave.email())
                .password(headmasterToSave.pesel())
                .build();

        //when
        Mockito.when(headmasterRepositoryDAO.findAllHeadmasters()).thenReturn(headmasterList);
        Mockito.when(securityService.setRoleWhileCreateNewPortalUser(
                headmasterToSave.email(), headmasterToSave.password(), headmasterRole)).thenReturn(headmasterPortalUserEntity);
        Mockito.when(passwordEncoder.encode(headmasterToSave.pesel())).thenReturn(encodedPesel);
        Mockito.when(headmasterRepositoryDAO.insertHeadmaster(headmasterToSave.withPesel(encodedPesel)))
                .thenReturn(headmasterSaved);
        Headmaster insertedHeadmaster = headmasterService.insertHeadmaster(headmasterToSave);

        //then
        Assertions.assertEquals(insertedHeadmaster, headmasterSaved);
    }

    @Test
    void findAllHeadmasters() {
        //given
        Headmaster headmasterSaved1 = HeadmasterDomainTestData.headmasterSaved1();
        List<Headmaster> headmastersSaved = List.of(headmasterSaved1);

        //when
        Mockito.when(headmasterRepositoryDAO.findAllHeadmasters()).thenReturn(headmastersSaved);
        List<Headmaster> headmasterList = headmasterService.findAllHeadmasters();

        //then
        Assertions.assertEquals(headmasterList, headmastersSaved);
    }

    @Test
    void findHeadmasterByEmail() {
        //given
        String headmasterEmail = "headmaster-email@music-contests.com";
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1().withEmail(headmasterEmail);


        //when
        Mockito.when(headmasterRepositoryDAO.findHeadmasterByEmail(headmasterEmail))
                .thenReturn(Optional.of(headmaster));
        Headmaster headmasterByEmail = headmasterService.findHeadmasterByEmail(headmasterEmail);

        //then
        Assertions.assertEquals(headmaster, headmasterByEmail);
    }

    @Test
    void checkIfHeadmasterExistsByPeselThrowsExceptionIfExists() throws Throwable {
        //given
        String headmasterPesel = "11111111111";
        Headmaster headmaster = HeadmasterDomainTestData.headmasterSaved1();
        String exceptionMessage = "Headmaster with pesel [%s] already exist!".formatted(headmasterPesel);

        //when
        Mockito.when(headmasterRepositoryDAO.findAllHeadmasters()).thenReturn(List.of(headmaster));
        Executable exception = () -> headmasterService.checkIfHeadmasterExistByPesel(headmasterPesel);

        //then
        Assertions.assertThrowsExactly(RuntimeException.class, exception, exceptionMessage);
    }

    @Test
    void deleteHeadmaster() {
        //given
        Headmaster headmasterSaved1 = HeadmasterDomainTestData.headmasterSaved1();
        List<Headmaster> headmastersSaved = new ArrayList<>(List.of(headmasterSaved1));

        //when
        Mockito.when(headmasterRepositoryDAO.findAllHeadmasters()).thenReturn(headmastersSaved);
        List<Headmaster> headmasterListBefore = headmasterService.findAllHeadmasters();
        Assertions.assertEquals(headmasterListBefore, headmastersSaved);
        headmasterService.deleteHeadmaster(headmasterSaved1);
        headmastersSaved.remove(headmasterSaved1);
        List<Headmaster> headmasterListAfter = headmasterService.findAllHeadmasters();

        //then
        org.assertj.core.api.Assertions.assertThatCollection(headmasterListAfter).isEmpty();
        Mockito.verify(headmasterRepositoryDAO).deleteHeadmaster(headmasterSaved1);
    }
}