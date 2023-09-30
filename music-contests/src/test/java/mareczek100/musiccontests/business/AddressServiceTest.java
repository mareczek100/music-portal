package mareczek100.musiccontests.business;

import mareczek100.musiccontests.business.dao.AddressRepositoryDAO;
import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.test_data_storage.music_school.MusicSchoolDomainTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepositoryDAO addressRepositoryDAO;
    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(addressRepositoryDAO);
        Assertions.assertNotNull(addressService);
    }

    @Test
    void insertAddress() {
        //given
        Address addressToSave = MusicSchoolDomainTestData.musicSchoolToSave1().address();
        Address addressSaved = MusicSchoolDomainTestData.musicSchoolSaved1().address();

        //when
        Mockito.when(addressRepositoryDAO.insertAddress(addressToSave)).thenReturn(addressSaved);
        Address insertedAddress = addressService.insertAddress(addressToSave);

        //then
        Assertions.assertEquals(insertedAddress, addressSaved);

    }

    @Test
    void findAllAddresses() {
        //given
        Address addressSaved1 = MusicSchoolDomainTestData.musicSchoolSaved1().address();
        Address addressSaved2 = MusicSchoolDomainTestData.musicSchoolSaved2().address();
        Address addressSaved3 = MusicSchoolDomainTestData.musicSchoolSaved3().address();
        List<Address> addressesSaved
                = List.of(addressSaved1, addressSaved2, addressSaved3);

        //when
        Mockito.when(addressRepositoryDAO.findAllAddresses()).thenReturn(addressesSaved);
        List<Address> addressList = addressService.findAllAddresses();

        //then
        Assertions.assertEquals(addressList, addressesSaved);
    }
}