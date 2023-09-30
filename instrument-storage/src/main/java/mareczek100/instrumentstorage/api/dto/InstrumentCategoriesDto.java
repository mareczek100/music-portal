package mareczek100.instrumentstorage.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record InstrumentCategoriesDto(List<InstrumentCategoryDto> instrumentCategoryDtoList) {
}