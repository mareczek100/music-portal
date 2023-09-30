package mareczek100.instrumentstorage.infrastructure.database.entityDtoMapper;

import mareczek100.instrumentstorage.api.dto.InstrumentCategoryDto;
import mareczek100.instrumentstorage.api.dto.InstrumentDto;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;
import org.springframework.stereotype.Component;

@Component
public class InstrumentEntityDtoMapper {
    public InstrumentEntity mapToEntityFromDto(InstrumentDto instrumentDto) {
        return InstrumentEntity.builder()
                .name(instrumentDto.name())
                .primarySchoolDegree(instrumentDto.primarySchoolDegree())
                .secondarySchoolDegree(instrumentDto.secondarySchoolDegree())
                .category(buildInstrumentCategoryEntity(instrumentDto.category()))
                .build();
    }

    private InstrumentCategoryEntity buildInstrumentCategoryEntity(InstrumentCategoryDto categoryDto) {
        return InstrumentCategoryEntity.builder()
                .categoryName(InstrumentCategoryName.valueOf(categoryDto.category().name()))
                .build();
    }

    public InstrumentDto mapToDtoFromEntity(InstrumentEntity instrumentEntity) {
        return InstrumentDto.builder()
                .name(instrumentEntity.getName())
                .primarySchoolDegree(instrumentEntity.getPrimarySchoolDegree())
                .secondarySchoolDegree(instrumentEntity.getSecondarySchoolDegree())
                .category(buildInstrumentCategoryDto(instrumentEntity.getCategory()))
                .build();
    }

    private InstrumentCategoryDto buildInstrumentCategoryDto(InstrumentCategoryEntity instrumentCategoryEntity) {
        return InstrumentCategoryDto.builder()
                .category(InstrumentCategoryName.valueOf(instrumentCategoryEntity.getCategoryName().name()))
                .build();
    }
}