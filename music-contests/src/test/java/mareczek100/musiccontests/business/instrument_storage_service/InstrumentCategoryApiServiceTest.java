package mareczek100.musiccontests.business.instrument_storage_service;

import mareczek100.infrastructure.api.InstrumentCategoryRestControllerApi;
import mareczek100.infrastructure.model.InstrumentCategoriesDto;
import mareczek100.infrastructure.model.InstrumentCategoryDto;
import mareczek100.musiccontests.business.instrument_storage_service.dao.InstrumentCategoryDAO;
import mareczek100.musiccontests.domain.instrument_storage_domain.InstrumentCategory;
import mareczek100.musiccontests.domain.instrument_storage_domain.mapper.InstrumentStorageApiMapper;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentApiStorageDtoTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDomainTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

import static mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService.EXCEPTION_API_MESSAGE;

@ExtendWith(MockitoExtension.class)
class InstrumentCategoryApiServiceTest {

    @Mock
    private InstrumentCategoryRestControllerApi instrumentCategoryRestControllerApi;
    @Mock
    private InstrumentCategoryDAO instrumentCategoryDAO;
    @Mock
    private InstrumentStorageApiMapper instrumentStorageApiMapper;
    @InjectMocks
    private InstrumentCategoryApiService instrumentCategoryApiService;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentCategoryRestControllerApi);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentCategoryDAO);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentStorageApiMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentCategoryApiService);
    }

    @Test
    void findAllCategoriesWorksCorrectly() {
        //given
        InstrumentCategoryDto categoryStrunowe = InstrumentApiStorageDtoTestData.buildCategoryStrunowe();
        InstrumentCategoryDto categoryDęte = InstrumentApiStorageDtoTestData.buildCategoryDęte();
        InstrumentCategoryDto categoryPerkusyjne = InstrumentApiStorageDtoTestData.buildCategoryPerkusyjne();
        List<InstrumentCategoryDto> categoryDtoList
                = List.of(categoryStrunowe, categoryDęte, categoryPerkusyjne);
        InstrumentCategoriesDto instrumentCategoriesApiStorageDto = new InstrumentCategoriesDto();
        instrumentCategoriesApiStorageDto.setInstrumentCategoryDtoList(categoryDtoList);
        InstrumentCategory strunowe = InstrumentDomainTestData.buildCategoryStrunowe();
        InstrumentCategory dęte = InstrumentDomainTestData.buildCategoryDęte();
        InstrumentCategory perkusyjne = InstrumentDomainTestData.buildCategoryPerkusyjne();
        List<InstrumentCategory> instrumentCategories = List.of(strunowe, dęte, perkusyjne);

        //when
        Mockito.when(instrumentCategoryRestControllerApi.allInstrumentCategoryList())
                .thenReturn(Mono.just(instrumentCategoriesApiStorageDto));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryDtoList.get(0))).thenReturn(instrumentCategories.get(0));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryDtoList.get(1))).thenReturn(instrumentCategories.get(1));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryDtoList.get(2))).thenReturn(instrumentCategories.get(2));
        List<InstrumentCategory> allCategories = instrumentCategoryApiService.findAllCategories();

        //then
        Assertions.assertThatCollection(allCategories).isEqualTo(instrumentCategories);
    }
    @Test
    void findAllCategoriesThrowExceptionIfThereIsNoCategories() {
        //given
        List<InstrumentCategoryDto> instrumentCategoryDtoList = Collections.emptyList();
        InstrumentCategoriesDto instrumentCategoriesApiStorageDto = new InstrumentCategoriesDto();
        instrumentCategoriesApiStorageDto.setInstrumentCategoryDtoList(instrumentCategoryDtoList);

        //when
        Mockito.when(instrumentCategoryRestControllerApi.allInstrumentCategoryList())
                .thenReturn(Mono.just(instrumentCategoriesApiStorageDto));
        Executable exception = () -> instrumentCategoryApiService.findAllCategories();

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, exception, EXCEPTION_API_MESSAGE);
    }

    @Test
    void findCategoryStrunoweById() {
        //given
        InstrumentCategoryDto categoryStrunowe = InstrumentApiStorageDtoTestData.buildCategoryStrunowe();
        int strunoweId = 1;
        InstrumentCategory strunowe = InstrumentDomainTestData.buildCategoryStrunowe();

        //when
        Mockito.when(instrumentCategoryRestControllerApi.findInstrumentCategoryById(strunoweId))
                .thenReturn(Mono.just(categoryStrunowe));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryStrunowe)).thenReturn(strunowe);
        InstrumentCategory categoryById = instrumentCategoryApiService.findCategoryById(strunoweId);

        //then
        Assertions.assertThat(categoryById).isEqualTo(strunowe);
    }
    @Test
    void findCategoryDęteById() {
        //given
        InstrumentCategoryDto categoryDęte = InstrumentApiStorageDtoTestData.buildCategoryDęte();
        int dęteId = 1;
        InstrumentCategory dęte = InstrumentDomainTestData.buildCategoryDęte();

        //when
        Mockito.when(instrumentCategoryRestControllerApi.findInstrumentCategoryById(dęteId))
                .thenReturn(Mono.just(categoryDęte));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryDęte)).thenReturn(dęte);
        InstrumentCategory categoryById = instrumentCategoryApiService.findCategoryById(dęteId);

        //then
        Assertions.assertThat(categoryById).isEqualTo(dęte);
    }
    @Test
    void findCategoryPerkusyjneById() {
        //given
        InstrumentCategoryDto categoryPerkusyjne = InstrumentApiStorageDtoTestData.buildCategoryPerkusyjne();
        int perkusyjneId = 1;
        InstrumentCategory perkusyjne = InstrumentDomainTestData.buildCategoryPerkusyjne();

        //when
        Mockito.when(instrumentCategoryRestControllerApi.findInstrumentCategoryById(perkusyjneId))
                .thenReturn(Mono.just(categoryPerkusyjne));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryPerkusyjne)).thenReturn(perkusyjne);
        InstrumentCategory categoryById = instrumentCategoryApiService.findCategoryById(perkusyjneId);

        //then
        Assertions.assertThat(categoryById).isEqualTo(perkusyjne);
    }

    @Test
    void findCategoryByStrunoweName() {
        //given
        InstrumentCategoryDto categoryStrunowe = InstrumentApiStorageDtoTestData.buildCategoryStrunowe();
        String strunoweName = categoryStrunowe.getCategory().name();
        InstrumentCategory strunowe = InstrumentDomainTestData.buildCategoryStrunowe();

        //when
        Mockito.when(instrumentCategoryRestControllerApi.findInstrumentCategoryByCategoryName(
                strunoweName)).thenReturn(Mono.just(categoryStrunowe));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryStrunowe)).thenReturn(strunowe);
        InstrumentCategory categoryById = instrumentCategoryApiService.findCategoryByName(strunoweName);

        //then
        Assertions.assertThat(categoryById).isEqualTo(strunowe);
    }
    @Test
    void findCategoryByDęteName() {
        //given
        InstrumentCategoryDto categoryDęte = InstrumentApiStorageDtoTestData.buildCategoryDęte();
        String dęteName = categoryDęte.getCategory().name();
        InstrumentCategory dęte = InstrumentDomainTestData.buildCategoryDęte();

        //when
        Mockito.when(instrumentCategoryRestControllerApi.findInstrumentCategoryByCategoryName(
                dęteName)).thenReturn(Mono.just(categoryDęte));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryDęte)).thenReturn(dęte);
        InstrumentCategory categoryById = instrumentCategoryApiService.findCategoryByName(dęteName);

        //then
        Assertions.assertThat(categoryById).isEqualTo(dęte);
    }
    @Test
    void findCategoryByPerkusyjneName() {
        //given
        InstrumentCategoryDto categoryPerkusyjne = InstrumentApiStorageDtoTestData.buildCategoryPerkusyjne();
        String perkusyjneName = categoryPerkusyjne.getCategory().name();
        InstrumentCategory perkusyjne = InstrumentDomainTestData.buildCategoryPerkusyjne();

        //when
        Mockito.when(instrumentCategoryRestControllerApi.findInstrumentCategoryByCategoryName(
                perkusyjneName)).thenReturn(Mono.just(categoryPerkusyjne));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                categoryPerkusyjne)).thenReturn(perkusyjne);
        InstrumentCategory categoryById = instrumentCategoryApiService.findCategoryByName(perkusyjneName);

        //then
        Assertions.assertThat(categoryById).isEqualTo(perkusyjne);
    }
}