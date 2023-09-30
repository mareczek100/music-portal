package mareczek100.instrumentstorage.api.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoriesDto;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoryDto;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entityDtoMapper.InstrumentCategoryEntityDtoMapper;
import mareczek100.instrumentstorage.service.InstrumentCategoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = InstrumentCategoryRestController.API_INSTRUMENT_CATEGORY, produces = MediaType.APPLICATION_JSON_VALUE)
public class InstrumentCategoryRestController {
    public static final String API_INSTRUMENT_CATEGORY = "/api/instrument/category";
    public static final String FIND_INSTRUMENT_CATEGORY_BY_ID = "/{instrumentCategoryId}/id";
    public static final String FIND_INSTRUMENT_CATEGORY_BY_CATEGORY_NAME = "/{instrumentCategoryName}/category";

    private final InstrumentCategoryService instrumentCategoryService;
    private final InstrumentCategoryEntityDtoMapper instrumentCategoryEntityDtoMapper;
    @ApiResponses(@ApiResponse(code = 200, message = "Instrument storage found all instrument category in our base!"))
    @Operation(summary = "Find list of available instrument categories.")
    @GetMapping
    public InstrumentCategoriesDto allInstrumentCategoryList() {
        return InstrumentCategoriesDto.builder().instrumentCategoryDtoList(
                        instrumentCategoryService.findAllInstrumentCategories().stream()
                                .map(instrumentCategoryEntityDtoMapper::mapToDtoFromEntity)
                                .toList())
                .build();
    }

    @ApiResponses(@ApiResponse(code = 200, message = "Instrument storage found instrument category by id!"))
    @Operation(summary = "Find instrument category by id number. Id is an integer between 1 and 3 - just main categories.")
    @GetMapping(FIND_INSTRUMENT_CATEGORY_BY_ID)
    public InstrumentCategoryDto findInstrumentCategoryById(
            @PathVariable("instrumentCategoryId") Short instrumentCategoryId) {

        InstrumentCategoryEntity instrumentCategoryEntity = instrumentCategoryService.findInstrumentCategoryById(instrumentCategoryId);
        return instrumentCategoryEntityDtoMapper.mapToDtoFromEntity(instrumentCategoryEntity);
    }

    @ApiResponses(@ApiResponse(code = 200, message = "Instrument storage found instrument category by name!"))
    @Operation(summary = "Find instrument category by category name (with polish diacritical marks).")
    @GetMapping(FIND_INSTRUMENT_CATEGORY_BY_CATEGORY_NAME)
    public InstrumentCategoryDto findInstrumentCategoryByCategoryName(
            @PathVariable("instrumentCategoryName") String instrumentCategoryName) {

        InstrumentCategoryEntity instrumentCategoryEntity = instrumentCategoryService.findInstrumentCategoryByName(instrumentCategoryName);
        return instrumentCategoryEntityDtoMapper.mapToDtoFromEntity(instrumentCategoryEntity);
    }

}