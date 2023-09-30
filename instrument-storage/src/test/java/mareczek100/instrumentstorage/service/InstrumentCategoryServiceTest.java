package mareczek100.instrumentCategorystorage.service;

import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.repository.InstrumentCategoryRepository;
import mareczek100.instrumentstorage.service.InstrumentCategoryService;
import mareczek100.instrumentstorage.test_data_storage.InputEntityTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
class InstrumentCategoryServiceTest {

    @Mock
    private InstrumentCategoryRepository instrumentCategoryRepository;
    @InjectMocks
    private InstrumentCategoryService instrumentCategoryService;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentCategoryRepository);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentCategoryService);
    }

    @Test
    void findAllInstrumentCategoriesWorksCorrectly() {
        //given
        List<InstrumentCategoryEntity> instrumentCategoryEntityList = InputEntityTestData.instrumentCategoryEntityList();
        InstrumentCategoryEntity instrumentCategoryEntity = instrumentCategoryEntityList.stream().findAny().orElse(null);
        int size = instrumentCategoryEntityList.size();

        //when
        Mockito.when(instrumentCategoryRepository.findAllCategories()).thenReturn(instrumentCategoryEntityList);
        List<InstrumentCategoryEntity> allInstrumentCategories = instrumentCategoryService.findAllInstrumentCategories();

        //then
        Assertions.assertThatCollection(allInstrumentCategories).hasSize(size);
        Assertions.assertThatCollection(allInstrumentCategories).contains(instrumentCategoryEntity);
    }
    @Test
    void findAllInstrumentCategoriesThrowsExceptionIfCategoriesDoesNotExists() {
        //given
        String exceptionMessage = "We have no instrument categories right now. Sorry!";

        //when
        Mockito.when(instrumentCategoryRepository.findAllCategories()).thenReturn(Collections.emptyList());
        Executable exception = () ->  instrumentCategoryService.findAllInstrumentCategories();

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, exception, exceptionMessage);
    }

    @Test
    void findInstrumentCategoryByIdWorksCorrectly() {
        //given
        Short instrumentCategoryId1 = 1;
        Short instrumentCategoryId2 = 2;
        Short instrumentCategoryId3 = 3;
        InstrumentCategoryEntity instrumentCategoryEntity1 = InputEntityTestData.instrumentCategoryEntity1();
        InstrumentCategoryEntity instrumentCategoryEntity2 = InputEntityTestData.instrumentCategoryEntity2();
        InstrumentCategoryEntity instrumentCategoryEntity3 = InputEntityTestData.instrumentCategoryEntity3();

        //when
        Mockito.when(instrumentCategoryRepository.findCategoryById(instrumentCategoryId1))
                .thenReturn(Optional.of(instrumentCategoryEntity1));
        Mockito.when(instrumentCategoryRepository.findCategoryById(instrumentCategoryId2))
                .thenReturn(Optional.of(instrumentCategoryEntity2));
        Mockito.when(instrumentCategoryRepository.findCategoryById(instrumentCategoryId3))
                .thenReturn(Optional.of(instrumentCategoryEntity3));
        InstrumentCategoryEntity instrumentCategoryFoundById1
                = instrumentCategoryService.findInstrumentCategoryById(instrumentCategoryId1);
        InstrumentCategoryEntity instrumentCategoryFoundById2
                = instrumentCategoryService.findInstrumentCategoryById(instrumentCategoryId2);
        InstrumentCategoryEntity instrumentCategoryFoundById3
                = instrumentCategoryService.findInstrumentCategoryById(instrumentCategoryId3);

        //then
        Assertions.assertThat(instrumentCategoryFoundById1).isEqualTo(instrumentCategoryEntity1);
        Assertions.assertThat(instrumentCategoryFoundById2).isEqualTo(instrumentCategoryEntity2);
        Assertions.assertThat(instrumentCategoryFoundById3).isEqualTo(instrumentCategoryEntity3);
    }
    @Test
    void findInstrumentCategoryByIdThrowsExceptionIfCategoriesDoesNotExists() {
        //given
        short nonExistingCategoryId = 15;
        String exceptionMessage = "Instrument category with id number [%s] doesn't exist! Put 1, 2 or 3."
                .formatted(nonExistingCategoryId);

        //when
        Mockito.when(instrumentCategoryRepository.findCategoryById(nonExistingCategoryId))
                .thenReturn(Optional.empty());
        Executable exception =
                () -> instrumentCategoryService.findInstrumentCategoryById(nonExistingCategoryId);

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, exception, exceptionMessage);
    }

    @Test
    void findInstrumentCategoryByNameWorksCorrectly() {
        //given
        String instrumentCategoryStrunoweName = InstrumentCategoryName.strunowe.name();
        String instrumentCategoryDęteName = InstrumentCategoryName.dęte.name();
        String instrumentCategoryPerkusyjneName = InstrumentCategoryName.perkusyjne.name();
        InstrumentCategoryEntity instrumentCategoryStrunowe = InputEntityTestData.instrumentCategoryEntity1();
        InstrumentCategoryEntity instrumentCategoryDęte = InputEntityTestData.instrumentCategoryEntity2();
        InstrumentCategoryEntity instrumentCategoryPerkusyjne = InputEntityTestData.instrumentCategoryEntity3();
        List<InstrumentCategoryEntity> instrumentCategoryEntities = InputEntityTestData.instrumentCategoryEntityList();

        //when
        Mockito.when(instrumentCategoryRepository.findAllCategories()).thenReturn(instrumentCategoryEntities);
        Mockito.when(instrumentCategoryRepository.findCategoryByName(instrumentCategoryStrunoweName))
                .thenReturn(Optional.of(instrumentCategoryStrunowe));
        Mockito.when(instrumentCategoryRepository.findCategoryByName(instrumentCategoryDęteName))
                .thenReturn(Optional.of(instrumentCategoryDęte));
        Mockito.when(instrumentCategoryRepository.findCategoryByName(instrumentCategoryPerkusyjneName))
                .thenReturn(Optional.of(instrumentCategoryPerkusyjne));
        InstrumentCategoryEntity instrumentCategoryByName1
                = instrumentCategoryService.findInstrumentCategoryByName(instrumentCategoryStrunoweName);
        InstrumentCategoryEntity instrumentCategoryByName2
                = instrumentCategoryService.findInstrumentCategoryByName(instrumentCategoryDęteName);
        InstrumentCategoryEntity instrumentCategoryByName3
                = instrumentCategoryService.findInstrumentCategoryByName(instrumentCategoryPerkusyjneName);
        List<InstrumentCategoryEntity> allInstrumentCategories = instrumentCategoryService.findAllInstrumentCategories();

        //then
        Assertions.assertThat(instrumentCategoryByName1).isEqualTo(instrumentCategoryStrunowe);
        Assertions.assertThat(instrumentCategoryByName2).isEqualTo(instrumentCategoryDęte);
        Assertions.assertThat(instrumentCategoryByName3).isEqualTo(instrumentCategoryPerkusyjne);
        Assertions.assertThatCollection(allInstrumentCategories).contains(instrumentCategoryByName1);
        Assertions.assertThatCollection(allInstrumentCategories).contains(instrumentCategoryByName2);
        Assertions.assertThatCollection(allInstrumentCategories).contains(instrumentCategoryByName3);
    }
    @Test
    void findInstrumentCategoryByNameThrowsExceptionIfCategoriesDoesNotExists() {
        //given
        String nonExistingCategoryName = "nonExistingCategoryName";
        List<String> categoryNames = Arrays.stream(InstrumentCategoryName.values())
                .map(Enum::name)
                .toList();
        String exceptionMessage = "Instrument category [%s] doesn't exist, sorry!%nAvailable categories: %s."
                .formatted(nonExistingCategoryName, categoryNames);

        //when
        Executable exception =
                () -> instrumentCategoryService.findInstrumentCategoryByName(nonExistingCategoryName);

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, exception, exceptionMessage);
    }
}