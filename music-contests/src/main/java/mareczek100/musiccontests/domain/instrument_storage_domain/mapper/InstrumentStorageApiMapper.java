package mareczek100.musiccontests.domain.instrument_storage_domain.mapper;

import mareczek100.infrastructure.model.InstrumentCategoryDto;
import mareczek100.infrastructure.model.InstrumentDto;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.domain.instrument_storage_domain.InstrumentCategory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService.EXCEPTION_API_MESSAGE;

@Component
public class InstrumentStorageApiMapper {


    public Instrument mapFromInstrumentApiDtoToInstrument(InstrumentDto instrumentApiDto)
    {
        return Instrument.builder()
                .instrumentId(UUID.randomUUID().toString())
                .name(Optional.ofNullable(instrumentApiDto.getName()).orElseThrow(
                        () -> new RuntimeException(EXCEPTION_API_MESSAGE)))
                .primarySchoolDegree(instrumentApiDto.getPrimarySchoolDegree())
                .secondarySchoolDegree(instrumentApiDto.getSecondarySchoolDegree())
                .category(mapFromInstrumentApiDtoCategoryToInstrumentCategory(Optional.ofNullable(instrumentApiDto.getCategory())
                        .orElseThrow(() -> new RuntimeException(EXCEPTION_API_MESSAGE))))
                .build();
    }
    public InstrumentCategory mapFromInstrumentApiDtoCategoryToInstrumentCategory(InstrumentCategoryDto category)
    {
        return InstrumentCategory.builder()
                .instrumentCategoryId(UUID.randomUUID().toString())
                .instrumentCategory(
                        Objects.requireNonNull(category.getCategory()).name().toUpperCase())
                .build();
    }
    public InstrumentDto mapFromInstrumentToInstrumentApiDto(Instrument instrument)
    {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(instrument.name());
        instrumentDto.setPrimarySchoolDegree(instrument.primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(instrument.secondarySchoolDegree());
        instrumentDto.setCategory(mapFromInstrumentCategoryToInstrumentApiDtoCategory(instrument.category()));
        return instrumentDto;
    }

    public InstrumentCategoryDto mapFromInstrumentCategoryToInstrumentApiDtoCategory(InstrumentCategory category)
    {
        InstrumentCategoryDto instrumentCategoryDto = new InstrumentCategoryDto();
        instrumentCategoryDto.setCategory(
                InstrumentCategoryDto.CategoryEnum.valueOf(category.instrumentCategory()));
        return instrumentCategoryDto;
    }

}
