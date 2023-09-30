package mareczek100.instrumentstorage.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mareczek100.instrumentstorage.api.dto.InstrumentDto;
import mareczek100.instrumentstorage.api.dto.InstrumentsDto;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;
import mareczek100.instrumentstorage.infrastructure.database.entityDtoMapper.InstrumentEntityDtoMapper;
import mareczek100.instrumentstorage.service.InstrumentService;
import mareczek100.instrumentstorage.test_data_storage.InputDtoTestData;
import mareczek100.instrumentstorage.test_data_storage.InputEntityTestData;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InstrumentRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class InstrumentRestControllerTest {

    @MockBean
    private final InstrumentService instrumentService;

    @MockBean
    private final InstrumentEntityDtoMapper instrumentEntityDtoMapper;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(instrumentService);
        Assertions.assertNotNull(instrumentEntityDtoMapper);
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void allInstrumentList() throws Exception {
        //given
        List<InstrumentEntity> instrumentEntityList = InputEntityTestData.instrumentEntityList();
        List<InstrumentDto> instrumentDtoList = InputDtoTestData.instrumentDtoList();
        InstrumentsDto instrumentsDto = InputDtoTestData.instrumentsDto();

        //when
        Mockito.when(instrumentService.findAllInstruments()).thenReturn(instrumentEntityList);
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntityList.get(0)))
                .thenReturn(instrumentDtoList.get(0));
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntityList.get(1)))
                .thenReturn(instrumentDtoList.get(1));
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntityList.get(2)))
                .thenReturn(instrumentDtoList.get(2));
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntityList.get(3)))
                .thenReturn(instrumentDtoList.get(3));
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntityList.get(4)))
                .thenReturn(instrumentDtoList.get(4));
        String responseBodyInstrumentsDto = objectMapper.writeValueAsString(instrumentsDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                        InstrumentRestController.API_INSTRUMENT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.instrumentDtoList[0].name",
                        Matchers.is(instrumentsDto.instrumentDtoList().get(0).name())))
                .andExpect(jsonPath("$.instrumentDtoList[1].name",
                        Matchers.is(instrumentsDto.instrumentDtoList().get(1).name())))
                .andExpect(jsonPath("$.instrumentDtoList[2].name",
                        Matchers.is(instrumentsDto.instrumentDtoList().get(2).name())))
                .andExpect(jsonPath("$.instrumentDtoList[3].name",
                        Matchers.is(instrumentsDto.instrumentDtoList().get(3).name())))
                .andExpect(jsonPath("$.instrumentDtoList[4].name",
                        Matchers.is(instrumentsDto.instrumentDtoList().get(4).name())))
                .andReturn();

        org.assertj.core.api.Assertions
                .assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(responseBodyInstrumentsDto);
    }

    @Test
    void addInstrumentToExistingInstrumentList() throws Exception {
        //given
        InstrumentDto instrumentDto = InputDtoTestData.instrumentDto1();
        InstrumentEntity instrumentEntityToSave = InputEntityTestData.instrumentEntityToSave();
        InstrumentEntity instrumentEntitySaved = InputEntityTestData.instrumentEntity1();

        //when
        Mockito.when(instrumentEntityDtoMapper.mapToEntityFromDto(instrumentDto)).thenReturn(instrumentEntityToSave);
        Mockito.when(instrumentService.insertNewInstrument(instrumentEntityToSave)).thenReturn(instrumentEntitySaved);
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntitySaved)).thenReturn(instrumentDto);
        String instrumentDtoJson = objectMapper.writeValueAsString(instrumentDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(
                                InstrumentRestController.API_INSTRUMENT + InstrumentRestController.ADD_INSTRUMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(instrumentDtoJson))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.is(instrumentEntitySaved.getName())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), instrumentDtoJson);

    }

    @Test
    void updateExistingInstrumentByName() throws Exception {
        //given
        String oldInstrumentName = "oldInstrumentName";
        String newInstrumentName = "newInstrumentName";
        InstrumentEntity instrumentEntityToUpdate = InputEntityTestData.instrumentEntityToSave().withName(oldInstrumentName);
        InstrumentEntity instrumentEntityUpdated = InputEntityTestData.instrumentEntity1().withName(newInstrumentName);
        InstrumentDto instrumentDtoUpdated = InputDtoTestData.instrumentDto1().withName(newInstrumentName);

        //when
        Mockito.when(instrumentService.findInstrumentByName(oldInstrumentName)).thenReturn(instrumentEntityToUpdate);
        Mockito.when(instrumentService.updateInstrument(
                instrumentEntityToUpdate.withName(newInstrumentName))).thenReturn(instrumentEntityUpdated);
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntityUpdated)).thenReturn(instrumentDtoUpdated);
        String updatedInstrumentDtoJson = objectMapper.writeValueAsString(instrumentDtoUpdated);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.patch(
                                InstrumentRestController.API_INSTRUMENT + InstrumentRestController.UPDATE_INSTRUMENT)
                        .param("oldInstrumentName", oldInstrumentName)
                        .param("newInstrumentName", newInstrumentName))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(instrumentDtoUpdated.name())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), updatedInstrumentDtoJson);

    }

    @Test
    void findInstrumentById() throws Exception {
        //given
        Integer instrumentId = 1;
        InstrumentEntity instrumentEntity = InputEntityTestData.instrumentEntity1();
        InstrumentDto instrumentDto = InputDtoTestData.instrumentDto1();

        //when
        Mockito.when(instrumentService.findInstrumentById(instrumentId)).thenReturn(instrumentEntity);
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntity)).thenReturn(instrumentDto);
        String instrumentDtoJson = objectMapper.writeValueAsString(instrumentDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                InstrumentRestController.API_INSTRUMENT + InstrumentRestController.FIND_INSTRUMENT_BY_ID,
                                instrumentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(instrumentDto.name())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), instrumentDtoJson);
    }

    @Test
    void findInstrumentByName() throws Exception {
        //given
        String instrumentName = "testInstrument1";
        InstrumentEntity instrumentEntity = InputEntityTestData.instrumentEntity1();
        InstrumentDto instrumentDto = InputDtoTestData.instrumentDto1();

        //when
        Mockito.when(instrumentService.findInstrumentByName(instrumentName)).thenReturn(instrumentEntity);
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntity)).thenReturn(instrumentDto);
        String instrumentDtoJson = objectMapper.writeValueAsString(instrumentDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                InstrumentRestController.API_INSTRUMENT + InstrumentRestController.FIND_INSTRUMENT_BY_NAME,
                                instrumentName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is(instrumentDto.name())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), instrumentDtoJson);
    }

    @Test
    void findInstrumentByCategory() throws Exception {
        //given
        String instrumentCategory = InstrumentCategoryName.strunowe.name();
        List<InstrumentEntity> instrumentEntitiesByCategory = InputEntityTestData.instrumentEntityListByCategoryStrunowe();
        List<InstrumentDto> instrumentDtoListByCategory = InputDtoTestData.instrumentDtoListByCategoryStrunowe();
        InstrumentsDto instrumentsDtoByCategory = InstrumentsDto.builder()
                .instrumentDtoList(instrumentDtoListByCategory).build();

        //when
        Mockito.when(instrumentService.findInstrumentsByCategory(instrumentCategory))
                .thenReturn(instrumentEntitiesByCategory);
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntitiesByCategory.get(0)))
                .thenReturn(instrumentDtoListByCategory.get(0));
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntitiesByCategory.get(1)))
                .thenReturn(instrumentDtoListByCategory.get(1));
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntitiesByCategory.get(2)))
                .thenReturn(instrumentDtoListByCategory.get(2));
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntitiesByCategory.get(3)))
                .thenReturn(instrumentDtoListByCategory.get(3));
        Mockito.when(instrumentEntityDtoMapper.mapToDtoFromEntity(instrumentEntitiesByCategory.get(4)))
                .thenReturn(instrumentDtoListByCategory.get(4));
        String instrumentsDtoJson = objectMapper.writeValueAsString(instrumentsDtoByCategory);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                InstrumentRestController.API_INSTRUMENT + InstrumentRestController.FIND_INSTRUMENTS_BY_CATEGORY,
                                instrumentCategory)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instrumentDtoList[0].name",
                        Matchers.is(instrumentDtoListByCategory.get(0).name())))
                .andExpect(jsonPath("$.instrumentDtoList[1].name",
                        Matchers.is(instrumentDtoListByCategory.get(1).name())))
                .andExpect(jsonPath("$.instrumentDtoList[2].name",
                        Matchers.is(instrumentDtoListByCategory.get(2).name())))
                .andExpect(jsonPath("$.instrumentDtoList[3].name",
                        Matchers.is(instrumentDtoListByCategory.get(3).name())))
                .andExpect(jsonPath("$.instrumentDtoList[4].name",
                        Matchers.is(instrumentDtoListByCategory.get(4).name())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), instrumentsDtoJson);
    }

    @Test
    void deleteInstrumentByName() throws Exception {
        //given
        String instrumentName = "testInstrument1";
        List<InstrumentEntity> instrumentEntityList = InputEntityTestData.instrumentEntityList();
        int beforeRemoveSize = instrumentEntityList.size();
        List<InstrumentEntity> instrumentEntityListToElementRemove = new ArrayList<>(instrumentEntityList);

        //when
        instrumentService.deleteInstrumentByName(instrumentName);
        instrumentEntityListToElementRemove.remove(instrumentEntityList.get(0));
        int afterRemoveSize = instrumentEntityListToElementRemove.size();
        Mockito.when(instrumentService.findInstrumentByName(instrumentName))
                .thenThrow(new RuntimeException("Instrument [%s] doesn't exist in our storage, sorry!"
                        .formatted(instrumentName)));

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(
                                InstrumentRestController.API_INSTRUMENT + InstrumentRestController.DELETE_INSTRUMENT_BY_NAME)
                        .param("instrumentName", instrumentName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Assertions.assertEquals(beforeRemoveSize, 5);
        Assertions.assertEquals(afterRemoveSize, beforeRemoveSize - 1);
    }
}