package mareczek100.musiccontests.api.dto.dto_rest_support;

import lombok.Builder;
import mareczek100.musiccontests.api.dto.MusicSchoolWithAddressDto;

import java.util.List;

@Builder
public record MusicSchoolsDto(List<MusicSchoolWithAddressDto> musicSchoolDtoList) {
}
