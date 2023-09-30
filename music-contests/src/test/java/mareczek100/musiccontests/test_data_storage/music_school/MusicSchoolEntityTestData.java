package mareczek100.musiccontests.test_data_storage.music_school;



import mareczek100.musiccontests.infrastructure.database.entity.AddressEntity;
import mareczek100.musiccontests.infrastructure.database.entity.MusicSchoolEntity;

import java.util.List;

public class MusicSchoolEntityTestData {
    public static MusicSchoolEntity musicSchoolEntityToSave1() {
        return MusicSchoolEntity.builder()
                .musicSchoolId(null)
                .name("musicSchoolName1")
                .patron("musicSchoolPatron1")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressEntityToSave1())
                .build();
    }
    public static MusicSchoolEntity musicSchoolEntitySaved1() {
        return MusicSchoolEntity.builder()
                .musicSchoolId("1ae52aaa-847c-4cb7-b7dd-869e94c04935")
                .name("musicSchoolName1")
                .patron("musicSchoolPatron1")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressEntitySaved1())
                .build();
    }

    public static MusicSchoolEntity musicSchoolEntityToSave2() {
        return MusicSchoolEntity.builder()
                .musicSchoolId(null)
                .name("musicSchoolName2")
                .patron("musicSchoolPatron2")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressEntityToSave2())
                .build();
    }
    public static MusicSchoolEntity musicSchoolEntitySaved2() {
        return MusicSchoolEntity.builder()
                .musicSchoolId("29db7721-03e7-40a2-a98d-f466ebc620c2")
                .name("musicSchoolName2")
                .patron("musicSchoolPatron2")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressEntitySaved2())
                .build();
    }
    public static MusicSchoolEntity musicSchoolEntityToSave3() {
        return MusicSchoolEntity.builder()
                .musicSchoolId(null)
                .name("musicSchoolName3")
                .patron("musicSchoolPatron3")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressEntityToSave3())
                .build();
    }
    public static MusicSchoolEntity musicSchoolEntitySaved3() {
        return MusicSchoolEntity.builder()
                .musicSchoolId("fcefef81-fa54-4d1b-bf5e-971efa113d64")
                .name("musicSchoolName3")
                .patron("musicSchoolPatron3")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressEntitySaved3())
                .build();
    }

    public static List<MusicSchoolEntity> musicSchoolEntityList() {
        return List.of(
                musicSchoolEntitySaved1(),
                musicSchoolEntitySaved2(),
                musicSchoolEntitySaved3()
        );
    }

    private static AddressEntity addressEntityToSave1(){
        return AddressEntity.builder()
                .addressId(null)
                .country("addressCountry1")
                .city("addressCity1")
                .postalCode("78-101")
                .street("addressStreet1")
                .buildingNumber("101")
                .additionalInfo("addressAdditionalInfo1")
                .build();
    }
    private static AddressEntity addressEntitySaved1(){
        return AddressEntity.builder()
                .addressId("0e524b25-96ca-4560-af99-86bbeedf5dfe")
                .country("addressCountry1")
                .city("addressCity1")
                .postalCode("78-101")
                .street("addressStreet1")
                .buildingNumber("101")
                .additionalInfo("addressAdditionalInfo1")
                .build();
    }
    private static AddressEntity addressEntityToSave2(){
        return AddressEntity.builder()
                .addressId(null)
                .country("addressCountry2")
                .city("addressCity2")
                .postalCode("78-202")
                .street("addressStreet2")
                .buildingNumber("102")
                .additionalInfo("addressAdditionalInfo2")
                .build();
    }
    private static AddressEntity addressEntitySaved2(){
        return AddressEntity.builder()
                .addressId("87fd7fde-0dc1-485a-a7d9-02899b101d78")
                .country("addressCountry2")
                .city("addressCity2")
                .postalCode("78-202")
                .street("addressStreet2")
                .buildingNumber("102")
                .additionalInfo("addressAdditionalInfo2")
                .build();
    }  private static AddressEntity addressEntityToSave3(){
        return AddressEntity.builder()
                .addressId(null)
                .country("addressCountry3")
                .city("addressCity3")
                .postalCode("78-303")
                .street("addressStreet3")
                .buildingNumber("103")
                .additionalInfo("addressAdditionalInfo3")
                .build();
    }
    private static AddressEntity addressEntitySaved3(){
        return AddressEntity.builder()
                .addressId("8326c333-b66c-40de-810f-c93723ad0851")
                .country("addressCountry3")
                .city("addressCity3")
                .postalCode("78-303")
                .street("addressStreet3")
                .buildingNumber("103")
                .additionalInfo("addressAdditionalInfo3")
                .build();
    }

}
