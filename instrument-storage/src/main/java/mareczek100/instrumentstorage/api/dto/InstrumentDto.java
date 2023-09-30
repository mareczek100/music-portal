package mareczek100.instrumentstorage.api.dto;

import lombok.Builder;
import lombok.With;

@With
@Builder
public record InstrumentDto(String name,
                            Boolean primarySchoolDegree,
                            Boolean secondarySchoolDegree,
                            InstrumentCategoryDto category) {
}