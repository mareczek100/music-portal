package mareczek100.instrumentstorage.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;
import mareczek100.instrumentstorage.infrastructure.database.repository.jpa.InstrumentJpaRepository;
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
class InstrumentRepositoryImplTest {

    private final InstrumentJpaRepository instrumentJpaRepository;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(instrumentJpaRepository);
    }


    @Test
    void insertInstrumentWorksCorrectly() {
        //given
        InstrumentEntity instrumentEntityToSave = InputEntityTestData.instrumentEntityToSave();
        InstrumentEntity instrumentEntitySaved = InputEntityTestData.instrumentEntity1();

        //when
        InstrumentEntity instrumentEntity = instrumentJpaRepository.saveAndFlush(instrumentEntityToSave);

        //then
        Assertions.assertNotNull(instrumentEntity.getInstrumentId());
        Assertions.assertEquals(instrumentEntity, instrumentEntitySaved);
    }

    @Test
    void updateInstrumentWorksCorrectly() {
        //given
        String instrumentEntityNewName = "newName";
        InstrumentEntity instrumentEntityWithNewName =
                InputEntityTestData.instrumentEntity1().withName(instrumentEntityNewName);

        //when
        InstrumentEntity instrumentEntitySaved = instrumentJpaRepository.saveAndFlush(instrumentEntityWithNewName);

        //then
        Assertions.assertEquals(instrumentEntitySaved, instrumentEntityWithNewName);
        Assertions.assertEquals(instrumentEntitySaved.getInstrumentId(),
                instrumentEntityWithNewName.getInstrumentId());
    }

    @Test
    void findAllInstrumentsWorksCorrectly() {
        //given
        InstrumentEntity instrumentEntityToSave = InputEntityTestData.instrumentEntityToSave();
        List<InstrumentEntity> instrumentEntitiesBeforeAdd = instrumentJpaRepository.findAll();

        //when
        instrumentJpaRepository.saveAndFlush(instrumentEntityToSave);
        List<InstrumentEntity> instrumentEntitiesAfterAdd = instrumentJpaRepository.findAll();

        //then
        Assertions.assertTrue(instrumentEntitiesAfterAdd.size() >= 1);
        Assertions.assertNotEquals(instrumentEntitiesBeforeAdd, instrumentEntitiesAfterAdd);
        Assertions.assertEquals(instrumentEntitiesBeforeAdd.size(), instrumentEntitiesAfterAdd.size() - 1);
    }

    @Test
    void findInstrumentByIdWorksCorrectly() {
        //given
        Integer instrumentId = 1;
        InstrumentEntity instrumentEntity = InputEntityTestData.instrumentEntity1();

        //when
        Optional<InstrumentEntity> optionalInstrumentEntity = instrumentJpaRepository.findById(instrumentId);

        //then
        Assertions.assertTrue(optionalInstrumentEntity.isPresent());
        Assertions.assertEquals(instrumentEntity.getInstrumentId(),
                optionalInstrumentEntity.orElseThrow().getInstrumentId());
    }

    @Test
    void findInstrumentByNameWorksCorrectly() {
        //given
        InstrumentEntity instrumentEntity = InputEntityTestData.instrumentEntity1();
        InstrumentEntity instrumentEntityFromStorage = instrumentJpaRepository.findAll().stream()
                .findAny().orElse(instrumentEntity);

        //when
        Optional<InstrumentEntity> instrumentByName = instrumentJpaRepository.findInstrumentByName(
                instrumentEntityFromStorage.getName());

        //then
        Assertions.assertTrue(instrumentByName.isPresent());
        Assertions.assertEquals(instrumentEntityFromStorage, instrumentByName.get());
    }

    @Test
    void findInstrumentsByCategoryWorksCorrectly() {
        //given
        InstrumentCategoryName instrumentEntityCategoryNameStrunowe = InstrumentCategoryName.strunowe;
        InstrumentCategoryEntity instrumentCategoryStrunowe = InputEntityTestData.instrumentCategoryEntity1();
        InstrumentEntity instrumentEntityWithCategoryStrunowe = InputEntityTestData.instrumentEntity1()
                .withCategory(instrumentCategoryStrunowe);

        //when
        instrumentJpaRepository.saveAndFlush(instrumentEntityWithCategoryStrunowe);
        List<InstrumentEntity> instrumentsByCategory
                = instrumentJpaRepository.findInstrumentsByCategory(instrumentEntityCategoryNameStrunowe);

        //then
        Assertions.assertTrue(instrumentsByCategory.size() >= 1);
        Assertions.assertEquals(instrumentsByCategory.get(0).getCategory().getCategoryName(),
                instrumentEntityCategoryNameStrunowe);
    }


    @Test
    void deleteInstrumentByNameWorksCorrectly() {
        //given
        InstrumentEntity instrumentEntityToSave = InputEntityTestData.instrumentEntityToSave();
        InstrumentEntity instrumentEntitySaved = instrumentJpaRepository.saveAndFlush(instrumentEntityToSave);

        //when
        List<InstrumentEntity> instrumentEntitiesBeforeDelete = instrumentJpaRepository.findAll();
        instrumentJpaRepository.delete(instrumentEntitySaved);
        List<InstrumentEntity> instrumentEntitiesAfterDelete = instrumentJpaRepository.findAll();

        //then
        Assertions.assertNotEquals(instrumentEntitiesBeforeDelete, instrumentEntitiesAfterDelete);
        Assertions.assertEquals(instrumentEntitiesBeforeDelete.size() - 1, instrumentEntitiesAfterDelete.size());
    }
}