package mareczek100.musiccontests.business.instrument_storage_service.dao;

import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentDAO {
    Instrument insertInstrument(Instrument instrument);

    Instrument updateInstrument(String oldName, String newName);

    Instrument findInstrumentByName(String instrumentName);

    List<Instrument> findInstrumentsByCategoryName(String instrumentCategory);

    List<Instrument> findAllInstruments();

    void deleteInstrumentByName(Instrument instrument);
}