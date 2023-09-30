package mareczek100.musiccontests.api.dto.mapper;

import mareczek100.musiccontests.api.dto.InstrumentCategoryDto;
import mareczek100.musiccontests.domain.instrument_storage_domain.InstrumentCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InstrumentCategoryDtoMapper {

    InstrumentCategory mapFromDtoToDomain(InstrumentCategoryDto instrumentCategoryDto);
    
    InstrumentCategoryDto mapFromDomainToDto(InstrumentCategory instrumentCategory);
}