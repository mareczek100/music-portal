package mareczek100.musiccontests.api.dto;

import lombok.Builder;


@Builder
public record InstrumentCategoryDto(String instrumentCategoryId,
                                    String instrumentCategory) {
}