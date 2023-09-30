package mareczek100.musiccontests.business.instrument_storage_service;

import lombok.AllArgsConstructor;
import mareczek100.infrastructure.api.InstrumentCategoryRestControllerApi;
import mareczek100.infrastructure.model.InstrumentCategoryDto;
import mareczek100.musiccontests.business.instrument_storage_service.dao.InstrumentCategoryDAO;
import mareczek100.musiccontests.domain.instrument_storage_domain.InstrumentCategory;
import mareczek100.musiccontests.domain.instrument_storage_domain.mapper.InstrumentStorageApiMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService.EXCEPTION_API_MESSAGE;

@Component
@AllArgsConstructor
public class InstrumentCategoryApiService implements InstrumentCategoryDAO {

    private final InstrumentCategoryRestControllerApi instrumentCategoryApiController;
    private final InstrumentStorageApiMapper instrumentApiMapper;
    @Override
    public List<InstrumentCategory> findAllCategories() {
        List<InstrumentCategoryDto> instrumentCategoryDtoList
                = Objects.requireNonNull(instrumentCategoryApiController.allInstrumentCategoryList()
                .block()).getInstrumentCategoryDtoList();
        if (Objects.isNull(instrumentCategoryDtoList) || instrumentCategoryDtoList.isEmpty())
        {
            throw new RuntimeException(EXCEPTION_API_MESSAGE);
        }
        return instrumentCategoryDtoList.stream()
                .map(instrumentApiMapper::mapFromInstrumentApiDtoCategoryToInstrumentCategory)
                .toList();
    }

    @Override
    public InstrumentCategory findCategoryById(Integer instrumentCategoryId) {
        InstrumentCategoryDto instrumentCategoryDto
                = instrumentCategoryApiController.findInstrumentCategoryById(instrumentCategoryId).block();
        return instrumentApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                Optional.ofNullable(instrumentCategoryDto).orElseThrow(
                        () -> new RuntimeException(EXCEPTION_API_MESSAGE)));
    }

    @Override
    public InstrumentCategory findCategoryByName(String instrumentCategoryName) {
        InstrumentCategoryDto instrumentCategoryDto
                = instrumentCategoryApiController.findInstrumentCategoryByCategoryName(instrumentCategoryName).block();
        return instrumentApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                Optional.ofNullable(instrumentCategoryDto).orElseThrow(
                        () -> new RuntimeException(EXCEPTION_API_MESSAGE)));
    }
}
