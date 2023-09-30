package mareczek100.musiccontests.business.instrument_storage_service;

import mareczek100.infrastructure.api.InstrumentRestControllerApi;
import mareczek100.infrastructure.model.InstrumentDto;
import mareczek100.infrastructure.model.InstrumentsDto;
import mareczek100.musiccontests.business.instrument_storage_service.dao.InstrumentDAO;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
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
class InstrumentApiServiceTest {

    @Mock
    private InstrumentRestControllerApi instrumentRestControllerApi;
    @Mock
    private InstrumentDAO instrumentDAO;
    @Mock
    private InstrumentStorageApiMapper instrumentStorageApiMapper;
    @InjectMocks
    private InstrumentApiService instrumentApiService;

    @BeforeEach
    void setUp() {
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentRestControllerApi);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentDAO);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentStorageApiMapper);
        org.junit.jupiter.api.Assertions.assertNotNull(instrumentApiService);
    }

    @Test
    void insertInstrument() {
        //given
        Instrument instrumentToSave = InstrumentDomainTestData.instrumentToSave1();
        Instrument instrumentSaved = InstrumentDomainTestData.instrumentSaved1();
        InstrumentDto instrumentApiStorageToSave1 = InstrumentApiStorageDtoTestData.instrumentApiStorageToSave1();
        InstrumentDto instrumentApiStorageSaved1 = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1();

        //when
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentToInstrumentApiDto(instrumentToSave))
                .thenReturn(instrumentApiStorageToSave1);
        Mockito.when(instrumentRestControllerApi.addInstrumentToExistingInstrumentList(
                instrumentApiStorageToSave1)).thenReturn(Mono.just(instrumentApiStorageSaved1));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageSaved1)).thenReturn(instrumentSaved);
        Instrument insertedInstrument = instrumentApiService.insertInstrument(instrumentToSave);

        //then
        Assertions.assertThat(instrumentSaved).usingRecursiveComparison().isEqualTo(insertedInstrument);
    }

    @Test
    void updateInstrument() {
        //given
        Instrument instrumentToUpdate = InstrumentDomainTestData.instrumentSaved1();
        String oldName = instrumentToUpdate.name();
        String newName = "new instrument name";
        InstrumentDto instrumentApiStorageSaved1 = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1();
        instrumentApiStorageSaved1.setName(newName);
        Instrument updatedInstrument = instrumentToUpdate.withName(newName);

        //when
        Mockito.when(instrumentRestControllerApi.updateExistingInstrumentByName(
                oldName, newName))
                .thenReturn(Mono.just(instrumentApiStorageSaved1));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageSaved1)).thenReturn(updatedInstrument);
        Instrument instrumentAfterUpdate = instrumentApiService.updateInstrument(oldName, newName);

        //then
        Assertions.assertThat(updatedInstrument).isEqualTo(instrumentAfterUpdate);
    }

    @Test
    void findInstrumentByName() {
        //given
        InstrumentDto instrumentApiStorageSaved = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1();
        String instrumentName = instrumentApiStorageSaved.getName();
        Instrument instrument = InstrumentDomainTestData.instrumentSaved1();

        //when
        Mockito.when(instrumentRestControllerApi.findInstrumentByName(instrumentName))
                .thenReturn(Mono.just(instrumentApiStorageSaved));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageSaved)).thenReturn(instrument);
        Instrument instrumentByName = instrumentApiService.findInstrumentByName(instrumentName);

        //then
        Assertions.assertThat(instrument).isEqualTo(instrumentByName);
    }

    @Test
    void findInstrumentsByCategoryName() {
        //given
        InstrumentDto instrumentApiStorageSaved = InstrumentApiStorageDtoTestData.instrumentApiStorageSaved1();
        List<InstrumentDto> instrumentApiStorageList = List.of(instrumentApiStorageSaved);
        InstrumentsDto instrumentsApiStorageDto = new InstrumentsDto();
        instrumentsApiStorageDto.setInstrumentDtoList(instrumentApiStorageList);
        Instrument instrument = InstrumentDomainTestData.instrumentSaved1();
        String instrumentCategory = instrument.category().instrumentCategory();

        //when
        Mockito.when(instrumentRestControllerApi.findInstrumentsByCategory(instrumentCategory))
                .thenReturn(Mono.just(instrumentsApiStorageDto));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageSaved)).thenReturn(instrument);
        List<Instrument> instrumentsByCategoryName
                = instrumentApiService.findInstrumentsByCategoryName(instrumentCategory);

        //then
        Assertions.assertThatCollection(instrumentsByCategoryName).containsOnly(instrument);
    }

    @Test
    void findAllInstrumentsWorksCorrectly() {
        //given
        List<InstrumentDto> instrumentApiStorageList = InstrumentApiStorageDtoTestData.instrumentApiStorageList();
        InstrumentsDto instrumentsApiStorageDto = new InstrumentsDto();
        instrumentsApiStorageDto.setInstrumentDtoList(instrumentApiStorageList);
        List<Instrument> instrumentList = InstrumentDomainTestData.instrumentList();

        //when
        Mockito.when(instrumentRestControllerApi.allInstrumentList())
                .thenReturn(Mono.just(instrumentsApiStorageDto));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageList.get(0))).thenReturn(instrumentList.get(0));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageList.get(1))).thenReturn(instrumentList.get(1));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageList.get(2))).thenReturn(instrumentList.get(2));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageList.get(3))).thenReturn(instrumentList.get(3));
        Mockito.when(instrumentStorageApiMapper.mapFromInstrumentApiDtoToInstrument(
                instrumentApiStorageList.get(4))).thenReturn(instrumentList.get(4));
        List<Instrument> allInstruments = instrumentApiService.findAllInstruments();

        //then
        Assertions.assertThatCollection(allInstruments).isEqualTo(instrumentList);
    }
    @Test
    void findAllInstrumentsThrowExceptionIfThereIsNoInstruments() {
        //given
        List<InstrumentDto> instrumentApiStorageList = Collections.emptyList();
        InstrumentsDto instrumentsApiStorageDto = new InstrumentsDto();
        instrumentsApiStorageDto.setInstrumentDtoList(instrumentApiStorageList);

        //when
        Mockito.when(instrumentRestControllerApi.allInstrumentList())
                .thenReturn(Mono.just(instrumentsApiStorageDto));
        Executable exception = () -> instrumentApiService.findAllInstruments();

        //then
        org.junit.jupiter.api.Assertions.assertThrowsExactly(
                RuntimeException.class, exception, EXCEPTION_API_MESSAGE);
    }

    @Test
    void deleteInstrumentByName() {
        //given
        Instrument instrumentSaved = InstrumentDomainTestData.instrumentSaved1();

        //when, then
        Mockito.when(instrumentRestControllerApi.deleteInstrumentByName(instrumentSaved.name()))
                .thenReturn(Mono.empty());
        instrumentApiService.deleteInstrumentByName(instrumentSaved);
    }
}