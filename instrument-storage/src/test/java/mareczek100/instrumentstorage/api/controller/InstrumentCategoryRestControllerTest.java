package mareczek100.instrumentstorage.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoriesDto;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoryDto;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entityDtoMapper.InstrumentCategoryEntityDtoMapper;
import mareczek100.instrumentstorage.service.InstrumentCategoryService;
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

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InstrumentCategoryRestController.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class InstrumentCategoryRestControllerTest {

    @MockBean
    private final InstrumentCategoryService instrumentCategoryService;

    @MockBean
    private final InstrumentCategoryEntityDtoMapper instrumentCategoryEntityDtoMapper;

    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(instrumentCategoryService);
        Assertions.assertNotNull(instrumentCategoryEntityDtoMapper);
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    void allInstrumentCategoryList() throws Exception {
        //given
        List<InstrumentCategoryEntity> instrumentCategoryEntityList = InputEntityTestData.instrumentCategoryEntityList();
        List<InstrumentCategoryDto> instrumentCategoryDtoList = InputDtoTestData.instrumentCategoryDtoList();
        InstrumentCategoriesDto instrumentCategoriesDto = InputDtoTestData.instrumentCategoriesDto();

        //when
        Mockito.when(instrumentCategoryService.findAllInstrumentCategories()).thenReturn(instrumentCategoryEntityList);
        Mockito.when(instrumentCategoryEntityDtoMapper.mapToDtoFromEntity(instrumentCategoryEntityList.get(0)))
                .thenReturn(instrumentCategoryDtoList.get(0));
        Mockito.when(instrumentCategoryEntityDtoMapper.mapToDtoFromEntity(instrumentCategoryEntityList.get(1)))
                .thenReturn(instrumentCategoryDtoList.get(1));
        Mockito.when(instrumentCategoryEntityDtoMapper.mapToDtoFromEntity(instrumentCategoryEntityList.get(2)))
                .thenReturn(instrumentCategoryDtoList.get(2));
        String responseBodyInstrumentCategoriesDto = objectMapper.writeValueAsString(instrumentCategoriesDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                        InstrumentCategoryRestController.API_INSTRUMENT_CATEGORY))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.instrumentCategoryDtoList[0].category",
                        Matchers.is(instrumentCategoriesDto.instrumentCategoryDtoList().get(0).category().name())))
                .andExpect(jsonPath("$.instrumentCategoryDtoList[1].category",
                        Matchers.is(instrumentCategoriesDto.instrumentCategoryDtoList().get(1).category().name())))
                .andExpect(jsonPath("$.instrumentCategoryDtoList[2].category",
                        Matchers.is(instrumentCategoriesDto.instrumentCategoryDtoList().get(2).category().name())))
                .andReturn();

        mvcResult.getResponse().setCharacterEncoding("UTF-8");

        org.assertj.core.api.Assertions
                .assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualTo(responseBodyInstrumentCategoriesDto);
    }

    @Test
    void findInstrumentCategoryCategoryById() throws Exception {
        //given
        Short instrumentCategoryId = 1;
        InstrumentCategoryEntity instrumentCategoryEntity = InputEntityTestData.instrumentCategoryEntity1();
        InstrumentCategoryDto instrumentCategoryDto = InputDtoTestData.instrumentCategoryDto1();

        //when
        Mockito.when(instrumentCategoryService.findInstrumentCategoryById(instrumentCategoryId))
                .thenReturn(instrumentCategoryEntity);
        Mockito.when(instrumentCategoryEntityDtoMapper.mapToDtoFromEntity(instrumentCategoryEntity))
                .thenReturn(instrumentCategoryDto);
        String instrumentCategoryDtoJson = objectMapper.writeValueAsString(instrumentCategoryDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                InstrumentCategoryRestController.API_INSTRUMENT_CATEGORY +
                                          InstrumentCategoryRestController.FIND_INSTRUMENT_CATEGORY_BY_ID,
                                instrumentCategoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", Matchers.is(instrumentCategoryDto.category().name())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), instrumentCategoryDtoJson);
    }

    @Test
    void findInstrumentCategoryCategoryByCategoryName() throws Exception {
        //given
        String instrumentCategoryName = "testInstrument1";
        InstrumentCategoryEntity instrumentCategoryEntity = InputEntityTestData.instrumentCategoryEntity1();
        InstrumentCategoryDto instrumentCategoryDto = InputDtoTestData.instrumentCategoryDto1();

        //when
        Mockito.when(instrumentCategoryService.findInstrumentCategoryByName(instrumentCategoryName)).thenReturn(instrumentCategoryEntity);
        Mockito.when(instrumentCategoryEntityDtoMapper.mapToDtoFromEntity(instrumentCategoryEntity)).thenReturn(instrumentCategoryDto);
        String instrumentCategoryDtoJson = objectMapper.writeValueAsString(instrumentCategoryDto);

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(
                                InstrumentCategoryRestController.API_INSTRUMENT_CATEGORY +
                                          InstrumentCategoryRestController.FIND_INSTRUMENT_CATEGORY_BY_CATEGORY_NAME,
                                instrumentCategoryName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", Matchers.is(instrumentCategoryDto.category().name())))
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), instrumentCategoryDtoJson);
    }
}