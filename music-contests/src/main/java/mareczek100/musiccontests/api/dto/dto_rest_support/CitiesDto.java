package mareczek100.musiccontests.api.dto.dto_rest_support;

import lombok.Builder;

import java.util.List;

@Builder
public record CitiesDto(List<String> competitionCitiesDtoList) {
}
