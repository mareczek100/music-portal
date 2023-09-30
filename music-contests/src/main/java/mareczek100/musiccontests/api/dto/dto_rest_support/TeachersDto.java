package mareczek100.musiccontests.api.dto.dto_rest_support;

import lombok.Builder;
import mareczek100.musiccontests.api.dto.TeacherDto;

import java.util.List;

@Builder
public record TeachersDto(List<TeacherDto> TeacherDtoList) {
}
