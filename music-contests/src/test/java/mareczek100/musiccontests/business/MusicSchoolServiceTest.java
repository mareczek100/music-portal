package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.MusicSchoolRepositoryDAO;
import mareczek100.musiccontests.domain.MusicSchool;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDomainTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MusicSchoolServiceTest {

    @Mock
    private MusicSchoolRepositoryDAO musicSchoolRepositoryDAO;
    @InjectMocks
    private MusicSchoolService musicSchoolService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(musicSchoolRepositoryDAO);
        Assertions.assertNotNull(musicSchoolService);
    }

    @Test
    void insertMusicSchool() {
        //given
        MusicSchool musicSchoolToSave = MusicSchoolDomainTestData.musicSchoolToSave1();
        MusicSchool musicSchoolSaved = MusicSchoolDomainTestData.musicSchoolSaved1();

        //when
        Mockito.when(musicSchoolRepositoryDAO.insertMusicSchool(musicSchoolToSave))
                .thenReturn(musicSchoolSaved);
        MusicSchool insertedMusicSchool = musicSchoolService.insertMusicSchool(musicSchoolToSave);

        //then
        Assertions.assertEquals(insertedMusicSchool, musicSchoolSaved);
    }

    @Test
    void findAllMusicSchools() {
        //given
        MusicSchool musicSchoolSaved1 = MusicSchoolDomainTestData.musicSchoolSaved1();
        MusicSchool musicSchoolSaved2 = MusicSchoolDomainTestData.musicSchoolSaved2();
        MusicSchool musicSchoolSaved3 = MusicSchoolDomainTestData.musicSchoolSaved3();
        List<MusicSchool> musicSchoolsSaved
                = List.of(musicSchoolSaved1, musicSchoolSaved2, musicSchoolSaved3);

        //when
        Mockito.when(musicSchoolRepositoryDAO.findAllMusicSchools()).thenReturn(musicSchoolsSaved);
        List<MusicSchool> musicSchoolList = musicSchoolService.findAllMusicSchools();

        //then
        Assertions.assertEquals(musicSchoolList, musicSchoolsSaved);
    }

    @Test
    void findMusicSchoolById() {
        //given
        MusicSchool musicSchoolSaved1 = MusicSchoolDomainTestData.musicSchoolSaved1();
        String musicSchoolId = musicSchoolSaved1.musicSchoolId();

        //when
        Mockito.when(musicSchoolRepositoryDAO.findMusicSchoolById(musicSchoolId))
                .thenReturn(Optional.of(musicSchoolSaved1));
        MusicSchool musicSchoolById = musicSchoolService.findMusicSchoolById(musicSchoolId);

        //then
        Assertions.assertEquals(musicSchoolById, musicSchoolSaved1);
    }
    @Test
    void findMusicSchoolByIdThrowsExceptionIfMusicSchoolDoesNotExists() {
        //given
        String musicSchoolId = "nonExistsMusicSchoolId";
        String exceptionMessage = "Music School with id [%s] doesn't exist".formatted(musicSchoolId);

        //when
        Mockito.when(musicSchoolRepositoryDAO.findMusicSchoolById(musicSchoolId))
                .thenReturn(Optional.empty());
        Executable exception = () ->  musicSchoolService.findMusicSchoolById(musicSchoolId);

        //then
        Assertions.assertThrowsExactly(RuntimeException.class, exception, exceptionMessage);
    }

    @Test
    void findMusicSchoolByPatron() {
        //given
        String patron = "School patron";
        MusicSchool musicSchoolSaved1 = MusicSchoolDomainTestData.musicSchoolSaved1().withPatron(patron);

        //when
        Mockito.when(musicSchoolRepositoryDAO.findMusicSchoolByPatron(patron))
                .thenReturn(Optional.ofNullable(musicSchoolSaved1));
        Optional<MusicSchool> musicSchoolByPatron = musicSchoolService.findMusicSchoolByPatron(patron);

        //then
        Assertions.assertTrue(musicSchoolByPatron.isPresent());
        Assertions.assertEquals(musicSchoolByPatron.get(), musicSchoolSaved1);
    }
}