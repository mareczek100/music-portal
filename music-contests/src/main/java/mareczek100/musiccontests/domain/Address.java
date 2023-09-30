package mareczek100.musiccontests.domain;

import lombok.Builder;
import lombok.With;

import java.util.Objects;

@With
@Builder
public record Address (String addressId,
                       String country,
                       String city,
                       String postalCode,
                       String street,
                       String buildingNumber,
                       String additionalInfo){
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!Objects.equals(country, address.country)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(postalCode, address.postalCode)) return false;
        if (!Objects.equals(street, address.street)) return false;
        if (!Objects.equals(buildingNumber, address.buildingNumber)) {
            return false;
        }
        return Objects.equals(additionalInfo, address.additionalInfo);
    }

    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (buildingNumber != null ? buildingNumber.hashCode() : 0);
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        return result;
    }
}
