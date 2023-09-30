package mareczek100.musiccontests.api.dto.dto_rest_support;

import lombok.Builder;
import mareczek100.musiccontests.api.dto.StudentDto;

import java.util.List;

@Builder
public record StudentsDto(List<StudentDto> StudentDtoList) {
}
