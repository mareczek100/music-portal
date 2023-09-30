package mareczek100.instrumentstorage.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.repository.jpa.InstrumentCategoryJpaRepository;
import mareczek100.instrumentstorage.test_configuration.PersistenceTestConfig;
import mareczek100.instrumentstorage.test_data_storage.InputEntityTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceTestConfig.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class InstrumentCategoryRepositoryImplTest {

    private final InstrumentCategoryJpaRepository instrumentCategoryJpaRepository;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(instrumentCategoryJpaRepository);
    }

    @Test
    void findAllCategories() {
        //given
        int categorySize = InstrumentCategoryName.values().length;

        //when
        List<InstrumentCategoryEntity> instrumentCategoryEntities = instrumentCategoryJpaRepository.findAll();

        //then
        Assertions.assertEquals(instrumentCategoryEntities.size(), categorySize);
    }

    @Test
    void findCategoryById() {
        //given
        Short instrumentCategoryId = 1;
        InstrumentCategoryEntity instrumentCategoryEntity = InputEntityTestData.instrumentCategoryEntity1();

        //when
        Optional<InstrumentCategoryEntity> optionalInstrumentCategoryEntity
                = instrumentCategoryJpaRepository.findById(instrumentCategoryId);

        //then
        Assertions.assertTrue(optionalInstrumentCategoryEntity.isPresent());
        Assertions.assertEquals(instrumentCategoryEntity.getInstrumentCategoryId(),
                optionalInstrumentCategoryEntity.orElseThrow().getInstrumentCategoryId());
    }


    @Test
    void findCategoryByName() {
        //given
        InstrumentCategoryName instrumentCategoryName = InstrumentCategoryName.strunowe;

        //when
        Optional<InstrumentCategoryEntity> instrumentCategoryByName =
                instrumentCategoryJpaRepository.findInstrumentCategoryByCategoryName(instrumentCategoryName);

        //then
        Assertions.assertTrue(instrumentCategoryByName.isPresent());
        Assertions.assertEquals(instrumentCategoryName, instrumentCategoryByName.get().getCategoryName());

    }
}