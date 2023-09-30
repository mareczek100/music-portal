package mareczek100.musiccontests.domain.instrument_storage_domain.mapper;

import mareczek100.infrastructure.model.InstrumentCategoryDto;
import mareczek100.infrastructure.model.InstrumentDto;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.domain.instrument_storage_domain.InstrumentCategory;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentApiStorageDtoTestData;
import mareczek100.musiccontests.test_data_storage.instrument.InstrumentDomainTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static mareczek100.musiccontests.business.instrument_storage_service.InstrumentApiService.EXCEPTION_API_MESSAGE;

@ExtendWith(MockitoExtension.class)
class InstrumentStorageApiMapperTest {

    @InjectMocks
    private InstrumentStorageApiMapper instrumentStorageApiMapper;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentStorageApiMapper);
    }

    @Test
    void mapFromInstrumentApiDtoToInstrument() {
        //given
        Instrument instrument = InstrumentDomainTestData.instrumentSaved1();
        InstrumentDto instrumentDto = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1();

        //when
        Instrument mappedInstrument
                = instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(instrumentDto);

        //then
        Assertions.assertThat(mappedInstrument).usingRecursiveComparison()
                .ignoringFields("instrumentId", "category.instrumentCategory", "category.instrumentCategoryId")
                .isEqualTo(instrument);
        Assertions.assertThat(mappedInstrument.category().instrumentCategory())
                .isEqualToIgnoringCase(instrument.category().instrumentCategory());
    }
    @Test
    void mapFromInstrumentApiDtoToInstrumentThrowsExceptionDuringCollectingData() {
        //given
        InstrumentDto instrumentDto = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1();
        instrumentDto.setName(null);

        //when
        Executable exception =
                () -> instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(instrumentDto);

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, exception, EXCEPTION_API_MESSAGE);
    }

    @Test
    void mapFromInstrumentApiDtoCategoryToInstrumentCategory() {
        //given
        InstrumentCategory instrumentCategory = InstrumentDomainTestData.instrumentSaved1().category();
        InstrumentCategoryDto instrumentCategoryDto
                = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1().getCategory();

        //when

        InstrumentCategory mappedInstrumentCategory
                = instrumentStorageApiMapper.mapFromInstrumentApiDtoCategoryToInstrumentCategory(
                Objects.requireNonNull(instrumentCategoryDto));

        //then
        Assertions.assertThat(mappedInstrumentCategory).usingRecursiveComparison()
                .ignoringFields("instrumentCategoryId").isEqualTo(instrumentCategory);
        Assertions.assertThat(mappedInstrumentCategory.instrumentCategory())
                .isEqualToIgnoringCase(instrumentCategory.instrumentCategory());
    }

    @Test
    void mapFromInstrumentToInstrumentApiDto() {
        //given
        InstrumentDto instrumentDto = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1();
        Instrument instrument = InstrumentDomainTestData.instrumentSaved1();

        //when
        InstrumentDto mappedInstrumentDto
                = instrumentStorageApiMapper.mapFromInstrumentToInstrumentApiDto(instrument);

        //then
        Assertions.assertThat(mappedInstrumentDto).usingRecursiveComparison()
                .ignoringFields("instrumentId", "category.instrumentCategoryId")
                .isEqualTo(instrumentDto);
        Assertions.assertThat(Objects.requireNonNull(mappedInstrumentDto.getCategory()).getCategory())
                .isEqualTo(Objects.requireNonNull(instrumentDto.getCategory()).getCategory());
    }

    @Test
    void mapFromInstrumentCategoryToInstrumentApiDtoCategory() {
        //given
        InstrumentCategoryDto instrumentCategoryDto
                = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1().getCategory();
        InstrumentCategory instrumentCategory = InstrumentDomainTestData.instrumentSaved1().category();

        //when

        InstrumentCategoryDto mappedInstrumentCategoryDto
                = instrumentStorageApiMapper.mapFromInstrumentCategoryToInstrumentApiDtoCategory(instrumentCategory);

        //then
        Assertions.assertThat(mappedInstrumentCategoryDto).usingRecursiveComparison()
                .ignoringFields("instrumentCategory", "instrumentCategoryId")
                .isEqualTo(instrumentCategoryDto);
        Assertions.assertThat(mappedInstrumentCategoryDto.getCategory())
                .isEqualTo(Objects.requireNonNull(instrumentCategoryDto).getCategory());
    }
}