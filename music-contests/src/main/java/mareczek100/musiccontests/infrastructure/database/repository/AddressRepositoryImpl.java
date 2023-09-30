package mareczek100.musiccontests.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import mareczek100.musiccontests.business.dao.AddressRepositoryDAO;
import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import mareczek100.musiccontests.infrastructure.database.mapper.AddressEntityMapper;
import mareczek100.musiccontests.infrastructure.database.repository.jpaRepository.AddressJpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@AllArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryDAO {

    private final AddressEntityMapper addressEntityMapper;
    private final AddressJpaRepository addressJpaRepository;


    @Override
    public Address insertAddress(Address address) {
        AddressEntity addressEntity = addressEntityMapper.mapFromDomainToEntity(address);
        AddressEntity savedAddressEntity = addressJpaRepository.saveAndFlush(addressEntity);
        return addressEntityMapper.mapFromEntityToDomain(savedAddressEntity);
    }

    @Override
    public List<Address> findAllAddresses()
    {
        Sort sort = Sort.by("city").ascending();

        List<AddressEntity> addressEntityList = addressJpaRepository.findAll(sort);
        return addressEntityList.stream()
                .map(addressEntityMapper::mapFromEntityToDomain)
                .toList();
    }

}
