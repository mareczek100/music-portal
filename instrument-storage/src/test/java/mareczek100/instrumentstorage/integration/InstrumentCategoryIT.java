package mareczek100.instrumentstorage.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoriesDto;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoryDto;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entityDtoMapper.InstrumentCategoryEntityDtoMapper;
import mareczek100.instrumentstorage.service.InstrumentCategoryService;
import mareczek100.instrumentstorage.test_configuration.RestAssuredITConfig;
import mareczek100.instrumentstorage.test_configuration.integration_test_support.InstrumentCategoryControllerTestSupport;
import mareczek100.instrumentstorage.test_data_storage.InputEntityTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstrumentCategoryIT extends RestAssuredITConfig implements InstrumentCategoryControllerTestSupport {

    private final InstrumentCategoryService instrumentCategoryService;

    private final InstrumentCategoryEntityDtoMapper instrumentCategoryEntityDtoMapper;

    private final ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentCategoryService);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentCategoryEntityDtoMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(objectMapper);
    }

    @Test
    void allInstrumentCategoryList(){
        //given
        InstrumentCategoryName strunowe = InstrumentCategoryName.strunowe;
        InstrumentCategoryName dęte = InstrumentCategoryName.dęte;
        InstrumentCategoryName perkusyjne = InstrumentCategoryName.perkusyjne;

        //when
        InstrumentCategoriesDto instrumentCategoriesDto = listOfAllInstrumentCategories();
        List<InstrumentCategoryName> instrumentCategoryList = instrumentCategoriesDto.instrumentCategoryDtoList().stream()
                .map(InstrumentCategoryDto::category)
                .toList();

        //then
        Assertions.assertThat(instrumentCategoriesDto.instrumentCategoryDtoList()).hasSize(3);
        Assertions.assertThatCollection(instrumentCategoryList).contains(strunowe);
        Assertions.assertThatCollection(instrumentCategoryList).contains(dęte);
        Assertions.assertThatCollection(instrumentCategoryList).contains(perkusyjne);
    }

    @Test
    void findInstrumentCategoryById(){
        //given
        InstrumentCategoryName strunowe = InstrumentCategoryName.strunowe;
        InstrumentCategoryName dęte = InstrumentCategoryName.dęte;
        InstrumentCategoryName perkusyjne = InstrumentCategoryName.perkusyjne;
        Short instrumentCategoryId1 = InputEntityTestData.instrumentCategoryEntity1().getInstrumentCategoryId();
        Short instrumentCategoryId2 = InputEntityTestData.instrumentCategoryEntity2().getInstrumentCategoryId();
        Short instrumentCategoryId3 = InputEntityTestData.instrumentCategoryEntity3().getInstrumentCategoryId();
        InstrumentCategoriesDto instrumentCategoriesDto = listOfAllInstrumentCategories();

        //when
        InstrumentCategoryDto instrumentCategoryDtoFoundById1 = findInstrumentCategoryById(instrumentCategoryId1);
        InstrumentCategoryDto instrumentCategoryDtoFoundById2 = findInstrumentCategoryById(instrumentCategoryId2);
        InstrumentCategoryDto instrumentCategoryDtoFoundById3 = findInstrumentCategoryById(instrumentCategoryId3);

        //then
        Assertions.assertThat(instrumentCategoryDtoFoundById1.category()).isEqualTo(strunowe);
        Assertions.assertThat(instrumentCategoryDtoFoundById2.category()).isEqualTo(dęte);
        Assertions.assertThat(instrumentCategoryDtoFoundById3.category()).isEqualTo(perkusyjne);
        Assertions.assertThatCollection(instrumentCategoriesDto.instrumentCategoryDtoList())
                .contains(instrumentCategoryDtoFoundById1);
        Assertions.assertThatCollection(instrumentCategoriesDto.instrumentCategoryDtoList())
                .contains(instrumentCategoryDtoFoundById2);
        Assertions.assertThatCollection(instrumentCategoriesDto.instrumentCategoryDtoList())
                .contains(instrumentCategoryDtoFoundById3);
    }

    @Test
    void findInstrumentCategoryByCategoryName(){
        //given
        InstrumentCategoryName categoryStrunowe = InstrumentCategoryName.strunowe;
        InstrumentCategoryName categoryDęte = InstrumentCategoryName.dęte;
        InstrumentCategoryName categoryPerkusyjne = InstrumentCategoryName.perkusyjne;
        String strunowe = InstrumentCategoryName.strunowe.name();
        String dęte = InstrumentCategoryName.dęte.name();
        String perkusyjne = InstrumentCategoryName.perkusyjne.name();

        //when
        InstrumentCategoryDto instrumentCategoryDtoFoundByNameStrunowe
                = findInstrumentCategoryByCategoryName(strunowe);
        InstrumentCategoryDto instrumentCategoryDtoFoundByNameDęte
                = findInstrumentCategoryByCategoryName(dęte);
        InstrumentCategoryDto instrumentCategoryDtoFoundByNamePerkusyjne
                = findInstrumentCategoryByCategoryName(perkusyjne);

        //then
        Assertions.assertThat(instrumentCategoryDtoFoundByNameStrunowe.category())
                .isEqualTo(categoryStrunowe);
        Assertions.assertThat(instrumentCategoryDtoFoundByNameDęte.category())
                .isEqualTo(categoryDęte);
        Assertions.assertThat(instrumentCategoryDtoFoundByNamePerkusyjne.category())
                .isEqualTo(categoryPerkusyjne);
    }

}
