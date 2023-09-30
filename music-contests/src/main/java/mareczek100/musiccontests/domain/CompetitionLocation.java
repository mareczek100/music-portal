package mareczek100.musiccontests.domain;

import lombok.Builder;
import lombok.With;

import java.util.Objects;
import java.util.Set;
@With
@Builder
public record CompetitionLocation(String competitionLocationId,
                                  String name,
                                  Address address,
                                  Set<Competition> competitions) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionLocation that = (CompetitionLocation) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}

