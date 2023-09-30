package mareczek100.musiccontests.business;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.MusicSchoolRepositoryDAO;
import mareczek100.musiccontests.domain.MusicSchool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MusicSchoolService {

    private final MusicSchoolRepositoryDAO musicSchoolRepositoryDAO;

    @Transactional
    public MusicSchool insertMusicSchool(MusicSchool musicSchool) {
        return musicSchoolRepositoryDAO.insertMusicSchool(musicSchool);
    }

    @Transactional
    public List<MusicSchool> findAllMusicSchools() {
        return musicSchoolRepositoryDAO.findAllMusicSchools();
    }

    @Transactional
    public MusicSchool findMusicSchoolById(String musicSchoolId) {
        return musicSchoolRepositoryDAO.findMusicSchoolById(musicSchoolId).orElseThrow(
                () -> new RuntimeException("Music School with id [%s] doesn't exist"
                        .formatted(musicSchoolId))
        );
    }

    @Transactional
    public Optional<MusicSchool> findMusicSchoolByPatron(String patron) {
        return musicSchoolRepositoryDAO.findMusicSchoolByPatron(patron);
    }
}
