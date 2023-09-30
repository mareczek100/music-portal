package mareczek100.musiccontests.business.instrument_storage_service;

import lombok.AllArgsConstructor;
import mareczek100.infrastructure.api.InstrumentRestControllerApi;
import mareczek100.infrastructure.model.InstrumentDto;
import mareczek100.musiccontests.business.instrument_storage_service.dao.InstrumentDAO;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.domain.instrument_storage_domain.mapper.InstrumentStorageApiMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class InstrumentApiService implements InstrumentDAO {

    public final static String EXCEPTION_API_MESSAGE = "Problem during response from INSTRUMENT API STORAGE";

    private final InstrumentRestControllerApi instrumentApiController;
    private final InstrumentStorageApiMapper instrumentApiMapper;

    @Override
    public Instrument insertInstrument(Instrument instrument)
    {
        InstrumentDto instrumentDto = instrumentApiMapper.mapFromInstrumentToInstrumentApiDto(instrument);
        InstrumentDto insertedInstrumentDto
                = instrumentApiController.addInstrumentToExistingInstrumentList(instrumentDto).block();
        return instrumentApiMapper.mapFromInstrumentApiDtoToInstrument(
                Optional.ofNullable(insertedInstrumentDto)
                        .orElseThrow(() -> new RuntimeException(EXCEPTION_API_MESSAGE)));
    }

    @Override
    public Instrument updateInstrument(String oldName, String newName)
    {
        InstrumentDto instrumentDto = instrumentApiController
                .updateExistingInstrumentByName(oldName, newName).block();
        return instrumentApiMapper.mapFromInstrumentApiDtoToInstrument(
                Optional.ofNullable(instrumentDto)
                        .orElseThrow(() -> new RuntimeException(EXCEPTION_API_MESSAGE)));
    }

    @Override
    public Instrument findInstrumentByName(String instrumentName)
    {
        InstrumentDto instrumentDto = instrumentApiController.findInstrumentByName(instrumentName).block();
        return instrumentApiMapper.mapFromInstrumentApiDtoToInstrument(
                Optional.ofNullable(instrumentDto).orElseThrow(
                        () -> new RuntimeException(EXCEPTION_API_MESSAGE)));
    }

    @Override
    public List<Instrument> findInstrumentsByCategoryName(String instrumentCategory)
    {
        List<InstrumentDto> instrumentDtoList
                = Objects.requireNonNull(instrumentApiController.findInstrumentsByCategory(instrumentCategory)
                .block()).getInstrumentDtoList();
        if (instrumentDtoList == null || instrumentDtoList.isEmpty())
        {
            throw new RuntimeException(EXCEPTION_API_MESSAGE);
        }
        return instrumentDtoList.stream()
                .map(instrumentApiMapper::mapFromInstrumentApiDtoToInstrument)
                .toList();
    }

    @Override
    public List<Instrument> findAllInstruments()
    {
        List<InstrumentDto> instrumentDtoList
                = Objects.requireNonNull(instrumentApiController.allInstrumentList().block()).getInstrumentDtoList();
        if (Objects.isNull(instrumentDtoList) || instrumentDtoList.isEmpty())
        {
            throw new RuntimeException(EXCEPTION_API_MESSAGE);
        }
        return instrumentDtoList.stream()
                .map(instrumentApiMapper::mapFromInstrumentApiDtoToInstrument)
                .toList();
    }

    @Override
    public void deleteInstrumentByName(Instrument instrument)
    {
        instrumentApiController.deleteInstrumentByName(instrument.name());
    }
}
