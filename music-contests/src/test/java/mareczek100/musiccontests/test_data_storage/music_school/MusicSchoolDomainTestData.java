package mareczek100.musiccontests.test_data_storage.music_school;


import mareczek100.musiccontests.domain.Address;
import mareczek100.musiccontests.domain.MusicSchool;

import java.util.List;

public class MusicSchoolDomainTestData {
    public static MusicSchool musicSchoolToSave1() {
        return MusicSchool.builder()
                .musicSchoolId(null)
                .name("musicSchoolName1")
                .patron("musicSchoolPatron1")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressToSave1())
                .build();
    }
    public static MusicSchool musicSchoolSaved1() {
        return MusicSchool.builder()
                .musicSchoolId("1ae52aaa-847c-4cb7-b7dd-869e94c04935")
                .name("musicSchoolName1")
                .patron("musicSchoolPatron1")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressSaved1())
                .build();
    }

    public static MusicSchool musicSchoolToSave2() {
        return MusicSchool.builder()
                .musicSchoolId(null)
                .name("musicSchoolName2")
                .patron("musicSchoolPatron2")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressToSave2())
                .build();
    }
    public static MusicSchool musicSchoolSaved2() {
        return MusicSchool.builder()
                .musicSchoolId("29db7721-03e7-40a2-a98d-f466ebc620c2")
                .name("musicSchoolName2")
                .patron("musicSchoolPatron2")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressSaved2())
                .build();
    }
    public static MusicSchool musicSchoolToSave3() {
        return MusicSchool.builder()
                .musicSchoolId(null)
                .name("musicSchoolName3")
                .patron("musicSchoolPatron3")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressToSave3())
                .build();
    }
    public static MusicSchool musicSchoolSaved3() {
        return MusicSchool.builder()
                .musicSchoolId("fcefef81-fa54-4d1b-bf5e-971efa113d64")
                .name("musicSchoolName3")
                .patron("musicSchoolPatron3")
                .primaryDegree(true)
                .secondaryDegree(false)
                .address(addressSaved3())
                .build();
    }

    public static List<MusicSchool> musicSchoolList() {
        return List.of(
                musicSchoolSaved1(),
                musicSchoolSaved2(),
                musicSchoolSaved3()
        );
    }

    private static Address addressToSave1(){
        return Address.builder()
                .addressId(null)
                .country("addressCountry1")
                .city("addressCity1")
                .postalCode("78-101")
                .street("addressStreet1")
                .buildingNumber("201")
                .additionalInfo("addressAdditionalInfo1")
                .build();
    }
    private static Address addressSaved1(){
        return Address.builder()
                .addressId("0e524b25-96ca-4560-af99-86bbeedf5dfe")
                .country("addressCountry1")
                .city("addressCity1")
                .postalCode("78-101")
                .street("addressStreet1")
                .buildingNumber("101")
                .additionalInfo("addressAdditionalInfo1")
                .build();
    }
    private static Address addressToSave2(){
        return Address.builder()
                .addressId(null)
                .country("addressCountry2")
                .city("addressCity2")
                .postalCode("78-202")
                .street("addressStreet2")
                .buildingNumber("202")
                .additionalInfo("addressAdditionalInfo2")
                .build();
    }
    private static Address addressSaved2(){
        return Address.builder()
                .addressId("87fd7fde-0dc1-485a-a7d9-02899b101d78")
                .country("addressCountry2")
                .city("addressCity2")
                .postalCode("78-202")
                .street("addressStreet2")
                .buildingNumber("102")
                .additionalInfo("addressAdditionalInfo2")
                .build();
    }  private static Address addressToSave3(){
        return Address.builder()
                .addressId(null)
                .country("addressCountry3")
                .city("addressCity3")
                .postalCode("78-303")
                .street("addressStreet3")
                .buildingNumber("103")
                .additionalInfo("addressAdditionalInfo3")
                .build();
    }
    private static Address addressSaved3(){
        return Address.builder()
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
