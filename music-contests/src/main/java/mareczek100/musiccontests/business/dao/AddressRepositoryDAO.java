package mareczek100.musiccontests.business.dao;

import mareczek100.musiccontests.domain.Address;

import java.util.List;
public interface AddressRepositoryDAO{
    Address insertAddress(Address address);
    List<Address> findAllAddresses();

}
