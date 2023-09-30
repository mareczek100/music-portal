package mareczek100.musiccontests.business.dao;

import mareczek100.musiccontests.domain.Headmaster;

import java.util.List;
import java.util.Optional;


public interface HeadmasterRepositoryDAO {

    Headmaster insertHeadmaster(Headmaster headmaster);

    List<Headmaster> findAllHeadmasters();

    Optional<Headmaster> findHeadmasterByEmail(String email);

    Optional<Headmaster> findHeadmasterByPesel(String pesel);

    void deleteHeadmaster(Headmaster headmaster);
}
