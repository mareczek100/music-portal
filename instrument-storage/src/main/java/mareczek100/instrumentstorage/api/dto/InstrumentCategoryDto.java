package mareczek100.instrumentstorage.api.dto;

import lombok.Builder;
import lombok.With;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
@With
@Builder
public record InstrumentCategoryDto(InstrumentCategoryName category) {
}