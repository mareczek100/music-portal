package mareczek100.instrumentstorage.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoryDto;
import mareczek100.instrumentstorage.api.dto.InstrumentDto;
import mareczek100.instrumentstorage.api.dto.InstrumentsDto;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;
import mareczek100.instrumentstorage.infrastructure.database.entityDtoMapper.InstrumentEntityDtoMapper;
import mareczek100.instrumentstorage.service.InstrumentService;
import mareczek100.instrumentstorage.test_configuration.RestAssuredITConfig;
import mareczek100.instrumentstorage.test_configuration.integration_test_support.InstrumentControllerTestSupport;
import mareczek100.instrumentstorage.test_data_storage.InputDtoTestData;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InstrumentIT extends RestAssuredITConfig implements InstrumentControllerTestSupport {


    private final InstrumentService instrumentService;

    private final InstrumentEntityDtoMapper instrumentEntityDtoMapper;

    private final ObjectMapper objectMapper;

    private final Flyway flyway;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentService);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentEntityDtoMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(objectMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(flyway);
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void thatAddInstrumentToExistingInstrumentListWorksCorrectly() {
        //given
        InstrumentDto instrumentDtoToSave = InputDtoTestData.instrumentDto1();
        InstrumentsDto instrumentsDtoBeforeAddInstrument = listOfAllInstruments();

        //when
        InstrumentDto insertedInstrumentDto = insertInstrument(instrumentDtoToSave);
        InstrumentsDto instrumentsDtoAfterAddInstrument = listOfAllInstruments();

        //then
        Assertions.assertThat(instrumentsDtoAfterAddInstrument.instrumentDtoList())
                .hasSizeGreaterThanOrEqualTo(1);
        Assertions.assertThat(instrumentDtoToSave).isEqualTo(insertedInstrumentDto);
        Assertions.assertThatCollection(instrumentsDtoAfterAddInstrument.instrumentDtoList())
                .contains(insertedInstrumentDto);
        Assertions.assertThatCollection(instrumentsDtoBeforeAddInstrument.instrumentDtoList())
                .isNotEqualTo(instrumentsDtoAfterAddInstrument);
        Assertions.assertThatCollection(instrumentsDtoBeforeAddInstrument.instrumentDtoList())
                .hasSizeLessThan(instrumentsDtoAfterAddInstrument.instrumentDtoList().size());

    }

    @Test
    void thatUpdateExistingInstrumentByNameWorksCorrectly() {
        //given
        String oldInstrumentName = "oldInstrumentName";
        String newInstrumentName = "newInstrumentName";
        InstrumentsDto instrumentsDtoBeforeUpdateInstrument = listOfAllInstruments();
        InstrumentDto instrumentDtoToSave = InputDtoTestData.instrumentDto1().withName(oldInstrumentName);
        InstrumentDto instrumentDtoToUpdate = insertInstrument(instrumentDtoToSave);

        //when
        InstrumentDto instrumentDtoUpdated = updateExistingInstrument(oldInstrumentName, newInstrumentName);
        InstrumentsDto instrumentsDtoAfterUpdateInstrument = listOfAllInstruments();

        //then
        Assertions.assertThat(instrumentDtoToUpdate).isNotEqualTo(instrumentDtoUpdated);
        Assertions.assertThatCollection(instrumentsDtoAfterUpdateInstrument.instrumentDtoList())
                .contains(instrumentDtoUpdated);
        Assertions.assertThatCollection(instrumentsDtoBeforeUpdateInstrument.instrumentDtoList())
                .isNotEqualTo(instrumentsDtoAfterUpdateInstrument);

    }

    @Test
    void thatFindInstrumentByIdWorksCorrectly() {
        //given
        InstrumentDto instrumentDto = InputDtoTestData.instrumentDto1();
        InstrumentEntity instrumentEntityToSave = instrumentEntityDtoMapper.mapToEntityFromDto(instrumentDto);
        InstrumentEntity insertedInstrumentEntity = instrumentService.insertNewInstrument(instrumentEntityToSave);
        InstrumentsDto instrumentsDto = listOfAllInstruments();
        Integer instrumentId = insertedInstrumentEntity.getInstrumentId();

        //when
        InstrumentDto instrumentDtoFoundById = findInstrumentById(instrumentId);

        //then
        Assertions.assertThat(insertedInstrumentEntity).usingRecursiveComparison()
                .comparingOnlyFields("name").isEqualTo(instrumentDtoFoundById);
        Assertions.assertThatCollection(instrumentsDto.instrumentDtoList()).contains(instrumentDtoFoundById);
    }

    @Test
    void thatFindInstrumentByNameWorksCorrectly() {
        //given
        InstrumentDto instrumentDto = InputDtoTestData.instrumentDto1();
        InstrumentDto insertedInstrument = insertInstrument(instrumentDto);
        InstrumentsDto instrumentsDto = listOfAllInstruments();
        String instrumentName = insertedInstrument.name();

        //when
        InstrumentDto instrumentDtoFoundByName = findInstrumentByName(instrumentName);

        //then
        Assertions.assertThat(insertedInstrument).usingRecursiveComparison()
                .comparingOnlyFields("name").isEqualTo(instrumentDtoFoundByName);
        Assertions.assertThatCollection(instrumentsDto.instrumentDtoList()).contains(instrumentDtoFoundByName);
    }

    @Test
    void thatFindInstrumentsByCategoryWorksCorrectly() {
        //given
        InstrumentCategoryName categoryStrunowe = InstrumentCategoryName.strunowe;
        InstrumentCategoryDto instrumentCategoryDtoStrunowe = InputDtoTestData.instrumentCategoryDto1()
                .withCategory(categoryStrunowe);
        InstrumentDto instrumentDto = InputDtoTestData.instrumentDto1().withCategory(instrumentCategoryDtoStrunowe);
        InstrumentDto insertedInstrumentDtoWithCategoryStrunowe = insertInstrument(instrumentDto);
        String instrumentCategoryStrunowe = insertedInstrumentDtoWithCategoryStrunowe.category().category().name();

        //when
        InstrumentsDto instrumentsDtoFoundByCategory = findInstrumentsByCategory(instrumentCategoryStrunowe);

        //then
        Assertions.assertThat(instrumentsDtoFoundByCategory.instrumentDtoList().get(0).category().category().name())
                .isEqualTo(instrumentCategoryStrunowe);
        Assertions.assertThat(instrumentsDtoFoundByCategory.instrumentDtoList().stream().findAny().get().category().category())
                .isEqualTo(categoryStrunowe);
        Assertions.assertThatCollection(instrumentsDtoFoundByCategory.instrumentDtoList())
                .contains(insertedInstrumentDtoWithCategoryStrunowe);
    }

    @Test
    void thatDeleteInstrumentByNameWorksCorrectly() {
        //given
        InstrumentDto instrumentDtoToSave = InputDtoTestData.instrumentDto1();
        InstrumentDto insertedInstrumentDto = insertInstrument(instrumentDtoToSave);
        InstrumentsDto instrumentsDtoBeforeDeleteInstrument = listOfAllInstruments();
        String instrumentToDeleteName = insertedInstrumentDto.name();

        //when
        deleteInstrumentByName(instrumentToDeleteName);
        InstrumentsDto instrumentsDtoAfterDeleteInstrument = listOfAllInstruments();

        //then
        Assertions.assertThat(instrumentsDtoBeforeDeleteInstrument.instrumentDtoList())
                .hasSizeGreaterThanOrEqualTo(1);
        Assertions.assertThatCollection(instrumentsDtoBeforeDeleteInstrument.instrumentDtoList())
                .contains(insertedInstrumentDto);
        Assertions.assertThatCollection(instrumentsDtoAfterDeleteInstrument.instrumentDtoList())
                .doesNotContain(insertedInstrumentDto);
        Assertions.assertThatCollection(instrumentsDtoAfterDeleteInstrument.instrumentDtoList())
                .isNotEqualTo(instrumentsDtoBeforeDeleteInstrument);
        Assertions.assertThatCollection(instrumentsDtoBeforeDeleteInstrument.instrumentDtoList())
                .hasSizeGreaterThan(instrumentsDtoAfterDeleteInstrument.instrumentDtoList().size());
    }

}