package mareczek100.musiccontests.api.dto.mapper;

import mareczek100.musiccontests.api.dto.InstrumentDto;
import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InstrumentDtoMapper {

    Instrument mapFromDtoToDomain(InstrumentDto instrumentDto);
    
    InstrumentDto mapFromDomainToDto(Instrument instrument);
}