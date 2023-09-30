package mareczek100.musiccontests.domain.instrument_storage_domain;

import lombok.Builder;


@Builder
public record InstrumentCategory(String instrumentCategoryId,
                                 String instrumentCategory) {
}