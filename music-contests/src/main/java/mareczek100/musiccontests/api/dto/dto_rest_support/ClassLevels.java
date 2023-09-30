package mareczek100.musiccontests.api.dto.dto_rest_support;

import lombok.Builder;
import mareczek100.musiccontests.domain.enums.ClassLevel;

import java.util.List;

@Builder
public record ClassLevels(List<ClassLevel> classLevelList) {
}
