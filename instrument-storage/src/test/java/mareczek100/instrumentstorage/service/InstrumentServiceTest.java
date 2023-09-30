package mareczek100.instrumentstorage.service;

import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;
import mareczek100.instrumentstorage.infrastructure.database.repository.InstrumentRepository;
import mareczek100.instrumentstorage.test_data_storage.InputEntityTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class InstrumentServiceTest {

    @Mock
    private InstrumentRepository instrumentRepository;
    @Mock
    private InstrumentCategoryService instrumentCategoryService;
    @InjectMocks
    private InstrumentService instrumentService;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentCategoryService);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentService);
    }

    @Test
    void insertNewInstrument() {
        //given
        String newInstrument = "newInstrument";
        List<InstrumentEntity> instrumentEntityList = InputEntityTestData.instrumentEntityList();
        InstrumentEntity instrumentEntityToSave = InputEntityTestData.instrumentEntityToSave().withName(newInstrument);
        InstrumentCategoryEntity instrumentCategoryEntity = InputEntityTestData.instrumentCategoryEntity1();
        String categoryName = instrumentEntityToSave.getCategory().getCategoryName().name();
        InstrumentEntity instrumentEntitySaved = InputEntityTestData.instrumentEntity1().withName(newInstrument);

        //when
        Mockito.when(instrumentRepository.findAllInstruments()).thenReturn(instrumentEntityList);
        Mockito.when(instrumentCategoryService.findInstrumentCategoryByName(categoryName))
                .thenReturn(instrumentCategoryEntity);
        Mockito.when(instrumentRepository.insertInstrument(
                instrumentEntityToSave.withCategory(instrumentCategoryEntity)))
                .thenReturn(instrumentEntitySaved);

        InstrumentEntity insertedInstrumentEntity = instrumentService.insertNewInstrument(instrumentEntityToSave);


        //then
        Assertions.assertThat(instrumentEntitySaved).isEqualTo(insertedInstrumentEntity);

    }
    @Test
    void insertNewInstrumentWithTooLongNameAndThenThrowException() {
        //given
        String toLongName = "12345678910123456789101234567891012345678910instrumentName";
        List<InstrumentEntity> instrumentEntityList = InputEntityTestData.instrumentEntityList();
        InstrumentEntity instrumentEntityToSave = InputEntityTestData.instrumentEntityToSave().withName(toLongName);
        InstrumentCategoryEntity instrumentCategoryEntity = InputEntityTestData.instrumentCategoryEntity1();
        String categoryName = instrumentEntityToSave.getCategory().getCategoryName().name();

        Throwable tooLongNameException = new RuntimeException("Instrument name too long, max length is 40 characters!");
        String exceptionMessage = "Instrument name too long, max length is 40 characters!";

        //when
        Mockito.when(instrumentRepository.findAllInstruments()).thenReturn(instrumentEntityList);
        Mockito.when(instrumentCategoryService.findInstrumentCategoryByName(categoryName))
                .thenReturn(instrumentCategoryEntity);
        Mockito.when(instrumentRepository.insertInstrument(
                        instrumentEntityToSave.withCategory(instrumentCategoryEntity)))
                .thenThrow(tooLongNameException);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> instrumentService.insertNewInstrument(instrumentEntityToSave));
        Assertions.assertThat(tooLongNameException.getMessage()).isEqualTo(exceptionMessage);

    }

    @Test
    void updateInstrument() {
        //given
        String oldInstrumentName = "oldInstrumentName";
        String newInstrumentName = "newInstrumentName";
        InstrumentEntity instrumentEntityWithOldName = InputEntityTestData.instrumentEntity1()
                .withName(oldInstrumentName);
        Integer instrumentEntityWithOldNameId = instrumentEntityWithOldName.getInstrumentId();
        InstrumentEntity instrumentEntityToUpdate = InputEntityTestData.instrumentEntityToSave()
                .withName(newInstrumentName).withInstrumentId(instrumentEntityWithOldNameId);

        //when
        Mockito.when(instrumentRepository.updateInstrument(instrumentEntityToUpdate))
                .thenReturn(instrumentEntityToUpdate);
        InstrumentEntity instrumentEntityUpdated = instrumentService.updateInstrument(instrumentEntityToUpdate);

        //then
        Assertions.assertThat(instrumentEntityWithOldNameId).isEqualTo(instrumentEntityToUpdate.getInstrumentId());
        Assertions.assertThat(newInstrumentName).isEqualTo(instrumentEntityUpdated.getName());
        Assertions.assertThat(instrumentEntityUpdated)
                .isEqualTo(instrumentEntityWithOldName.withName(newInstrumentName));
    }

    @Test
    void findInstrumentById() {
        //given
        Integer instrumentId = 1;
        InstrumentEntity instrumentEntity = InputEntityTestData.instrumentEntity1();

        //when
        Mockito.when(instrumentRepository.findInstrumentById(instrumentId))
                .thenReturn(Optional.of(instrumentEntity));
        InstrumentEntity instrumentFoundById = instrumentService.findInstrumentById(instrumentId);

        //then
        Assertions.assertThat(instrumentFoundById).isEqualTo(instrumentEntity);
    }
    @Test
    void findInstrumentByIdAndThrowExceptionWhenThatIdIsNotPresent() {
        //given
        Integer instrumentId = 1;
        Throwable instrumentWithIdNotPresentException = new RuntimeException("Instrument with id number [%s] doesn't exist!"
                .formatted(instrumentId));
        String exceptionMessage = "Instrument with id number [%s] doesn't exist!".formatted(instrumentId);

        //when
        Mockito.when(instrumentRepository.findInstrumentById(instrumentId))
                .thenThrow(instrumentWithIdNotPresentException);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> instrumentService.findInstrumentById(instrumentId));
        Assertions.assertThat(instrumentWithIdNotPresentException.getMessage()).isEqualTo(exceptionMessage);
    }

    @Test
    void findInstrumentByName() {
        //given
        String instrumentName = "testInstrument1";
        InstrumentEntity instrumentEntity = InputEntityTestData.instrumentEntity1().withName(instrumentName);

        //when
        Mockito.when(instrumentRepository.findInstrumentByName(instrumentName))
                .thenReturn(Optional.of(instrumentEntity));
        InstrumentEntity instrumentFoundByName = instrumentService.findInstrumentByName(instrumentName);

        //then
        Assertions.assertThat(instrumentFoundByName).isEqualTo(instrumentEntity);

       
    }
    @Test
    void findInstrumentByNameAndThrowExceptionWhenThatNameIsNotPresent() {
        //given
        String instrumentName = "testInstrument1";
        Throwable instrumentWithNameNotPresentException = new RuntimeException("Instrument [%s] doesn't exist in our storage, sorry!"
                .formatted(instrumentName));
        String exceptionMessage = "Instrument [%s] doesn't exist in our storage, sorry!".formatted(instrumentName);

        //when
        Mockito.when(instrumentRepository.findInstrumentByName(instrumentName))
                .thenThrow(instrumentWithNameNotPresentException);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> instrumentService.findInstrumentByName(instrumentName));
        Assertions.assertThat(instrumentWithNameNotPresentException.getMessage()).isEqualTo(exceptionMessage);
    }

    @Test
    void findInstrumentByCategory() {
        //given
        String instrumentCategory = InstrumentCategoryName.strunowe.name();
        List<InstrumentEntity> instrumentEntitiesByCategory = InputEntityTestData.instrumentEntityListByCategoryStrunowe();

        //when
        Mockito.when(instrumentRepository.findInstrumentsByCategoryName(instrumentCategory))
                .thenReturn(instrumentEntitiesByCategory);
        List<InstrumentEntity> instrumentsFoundByCategory
                = instrumentService.findInstrumentsByCategory(instrumentCategory);

        //then
        Assertions.assertThatCollection(instrumentEntitiesByCategory).isEqualTo(instrumentsFoundByCategory);
    }
    @Test
    void findInstrumentByCategoryThrowExceptionWhenInstrumentsNotOccurred() {
        //given
        String instrumentCategory = InstrumentCategoryName.strunowe.name();
        List<InstrumentEntity> instrumentEntitiesByCategory = InputEntityTestData.instrumentEntityListByCategoryStrunowe();
        List<InstrumentEntity> instrumentCategoryListEmpty = new ArrayList<>(instrumentEntitiesByCategory);
        instrumentCategoryListEmpty.clear();

        Throwable noInstrumentsByCategoryException
                = new RuntimeException("We have no instruments in category [%s], sorry!");
        String exceptionMessage = "We have no instruments in category [%s], sorry!";

        //when
        Mockito.when(instrumentRepository.findInstrumentsByCategoryName(
                instrumentCategory)).thenReturn(instrumentCategoryListEmpty);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> instrumentService.findInstrumentsByCategory(instrumentCategory));
        Assertions.assertThat(noInstrumentsByCategoryException.getMessage()).isEqualTo(exceptionMessage);
    }

    @Test
    void findAllInstruments() {
        //given
        List<InstrumentEntity> instrumentEntityList = InputEntityTestData.instrumentEntityList();
        InstrumentEntity instrumentEntity = instrumentEntityList.stream().findAny().orElse(null);
        int size = instrumentEntityList.size();

        //when
        Mockito.when(instrumentRepository.findAllInstruments()).thenReturn(instrumentEntityList);
        List<InstrumentEntity> allInstruments = instrumentService.findAllInstruments();

        //then
        Assertions.assertThatCollection(allInstruments).hasSize(size);
        Assertions.assertThatCollection(allInstruments).contains(instrumentEntity);
    }
    @Test
    void findAllInstrumentsThrowExceptionWhenListIsEmpty() {
        //given
        List<InstrumentEntity> instrumentEntityList = InputEntityTestData.instrumentEntityList();
        List<InstrumentEntity> instrumentListToPurge = new ArrayList<>(instrumentEntityList);
        instrumentListToPurge.clear();
        Throwable noInstrumentsException = new RuntimeException("We have no instruments at all - our storage is empty. Sorry!");
        String exceptionMessage = "We have no instruments at all - our storage is empty. Sorry!";

        //when
        Mockito.when(instrumentRepository.findAllInstruments()).thenReturn(instrumentListToPurge);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> instrumentService.findAllInstruments());
        Assertions.assertThat(noInstrumentsException.getMessage()).isEqualTo(exceptionMessage);
    }

    @Test
    void deleteInstrumentByNameThrowExceptionWhenDoesntExist() {
        //given
        List<InstrumentEntity> instrumentEntityList = InputEntityTestData.instrumentEntityList();
        InstrumentEntity instrumentEntityToDelete = instrumentEntityList.get(0);
        Throwable instrumentToDeleteNotPresentException = new RuntimeException("Instrument [%s] doesn't exist in our storage, sorry!"
                .formatted(instrumentEntityToDelete.getName()));
        String exceptionMessage = "Instrument [%s] doesn't exist in our storage, sorry!"
                .formatted(instrumentEntityToDelete.getName());

        //when
        Mockito.when(instrumentRepository.findInstrumentByName(instrumentEntityToDelete.getName()))
                .thenThrow(instrumentToDeleteNotPresentException);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class,
                () -> instrumentService.deleteInstrumentByName(instrumentEntityToDelete.getName()));
        Assertions.assertThat(instrumentToDeleteNotPresentException.getMessage()).isEqualTo(exceptionMessage);
    }
}