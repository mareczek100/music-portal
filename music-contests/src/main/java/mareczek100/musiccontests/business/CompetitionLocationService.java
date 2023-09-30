package mareczek100.musiccontests.business;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.CompetitionLocationRepositoryDAO;
import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.domain.CompetitionLocation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CompetitionLocationService {

    private final CompetitionLocationRepositoryDAO competitionLocationRepositoryDAO;
    private final AddressService addressService;

    @Transactional
    public CompetitionLocation insertCompetitionLocation(CompetitionLocation competitionLocation) {
        List<CompetitionLocation> allCompetitionLocation = findAllCompetitionLocations();
        List<Address> allAddresses = addressService.findAllAddresses();
        Address addressToSave = competitionLocation.address();
        if (allCompetitionLocation.contains(competitionLocation)){
            return allCompetitionLocation.stream()
                    .filter(competitionLocation::equals)
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("There is problem with competition location!"));
        }
        if (allAddresses.contains(competitionLocation.address())){
            Address existingAddress = allAddresses.stream()
                    .filter(addressToSave::equals)
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("There is problem with competition location!"));
            return competitionLocationRepositoryDAO.insertCompetitionLocation(
                    competitionLocation.withAddress(existingAddress));
        }
        Address insertedAddress = addressService.insertAddress(addressToSave);
        return competitionLocationRepositoryDAO.insertCompetitionLocation(
                competitionLocation.withAddress(insertedAddress));
    }
    @Transactional
    public List<CompetitionLocation> findAllCompetitionLocations() {
        return competitionLocationRepositoryDAO.findAllCompetitionLocations();
    }
}
