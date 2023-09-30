package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.AddressJpaRepository;
import mareczek100.musiccontests.test_configuration.PersistenceTestConfig;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolEntityTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceTestConfig.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AddressRepositoryImplTest {

    private final AddressJpaRepository addressJpaRepository;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(addressJpaRepository);
    }

    @Test
    void insertAddress() {
        //given
        AddressEntity addressEntityToSave = MusicSchoolEntityTestData.musicSchoolEntityToSave1().getAddress();
        AddressEntity addressEntitySaved = MusicSchoolEntityTestData.musicSchoolEntitySaved1().getAddress();

        //when
        AddressEntity savedAndFlushAddressEntity = addressJpaRepository.saveAndFlush(addressEntityToSave);

        //then
        Assertions.assertThat(savedAndFlushAddressEntity.getAddressId()).isNotNull();
        Assertions.assertThat(addressEntitySaved).usingRecursiveComparison()
                .ignoringFields("addressId").isEqualTo( savedAndFlushAddressEntity);
    }

    @Test
    void findAllAddresses() {
        //given
        AddressEntity addressEntityToSave1 = MusicSchoolEntityTestData.musicSchoolEntityToSave1().getAddress();
        AddressEntity addressEntityToSave2 = MusicSchoolEntityTestData.musicSchoolEntityToSave2().getAddress();
        AddressEntity addressEntityToSave3 = MusicSchoolEntityTestData.musicSchoolEntityToSave3().getAddress();
        List<AddressEntity> addressEntitiesToSave
                = List.of(addressEntityToSave1, addressEntityToSave2, addressEntityToSave3);

        //when
        List<AddressEntity> addressEntitiesSaved = addressJpaRepository.saveAllAndFlush(addressEntitiesToSave);
        List<AddressEntity> addressEntityList = addressJpaRepository.findAll();

        //then
        Assertions.assertThatCollection(addressEntityList).containsAll(addressEntitiesSaved);
    }

}