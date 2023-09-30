package mareczek100.musiccontests.test_data_storage.music_school;

import mareczek100.musiccontests.api.dto.MusicSchoolWithAddressDto;
import mareczek100.musiccontests.api.dto.dto_rest_support.MusicSchoolsDto;

import java.util.List;

public class MusicSchoolDtoTestData {
    public static MusicSchoolWithAddressDto musicSchoolDtoToSave1() {
        return MusicSchoolWithAddressDto.builder()
                .musicSchoolId(null)
                .musicSchoolName("musicSchoolName1")
                .musicSchoolPatron("musicSchoolPatron1")
                .musicSchoolPrimaryDegree(true)
                .musicSchoolSecondaryDegree(false)
                .addressCountry("addressCountry1")
                .addressCity("addressCity1")
                .addressPostalCode("78-101")
                .addressStreet("addressStreet1")
                .addressBuildingNumber("201")
                .addressAdditionalInfo("addressAdditionalInfo1")
                .build();
    }
    public static MusicSchoolWithAddressDto musicSchoolDtoSaved1() {
        return MusicSchoolWithAddressDto.builder()
                .musicSchoolId("1ae52aaa-847c-4cb7-b7dd-869e94c04935")
                .musicSchoolName("musicSchoolName1")
                .musicSchoolPatron("musicSchoolPatron1")
                .musicSchoolPrimaryDegree(true)
                .musicSchoolSecondaryDegree(false)
                .addressCountry("addressCountry1")
                .addressCity("addressCity1")
                .addressPostalCode("78-101")
                .addressStreet("addressStreet1")
                .addressBuildingNumber("201")
                .addressAdditionalInfo("addressAdditionalInfo1")
                .build();
    }
    public static MusicSchoolWithAddressDto musicSchoolDtoToSave2() {
        return MusicSchoolWithAddressDto.builder()
                .musicSchoolId(null)
                .musicSchoolName("musicSchoolName2")
                .musicSchoolPatron("musicSchoolPatron2")
                .musicSchoolPrimaryDegree(true)
                .musicSchoolSecondaryDegree(false)
                .addressCountry("addressCountry2")
                .addressCity("addressCity2")
                .addressPostalCode("78-102")
                .addressStreet("addressStreet2")
                .addressBuildingNumber("202")
                .addressAdditionalInfo("addressAdditionalInfo2")
                .build();
    }
    public static MusicSchoolWithAddressDto musicSchoolDtoSaved2() {
        return MusicSchoolWithAddressDto.builder()
                .musicSchoolId("29db7721-03e7-40a2-a98d-f466ebc620c2")
                .musicSchoolName("musicSchoolName2")
                .musicSchoolPatron("musicSchoolPatron2")
                .musicSchoolPrimaryDegree(true)
                .musicSchoolSecondaryDegree(false)
                .addressCountry("addressCountry2")
                .addressCity("addressCity2")
                .addressPostalCode("78-102")
                .addressStreet("addressStreet2")
                .addressBuildingNumber("202")
                .addressAdditionalInfo("addressAdditionalInfo2")
                .build();
    }
    public static MusicSchoolWithAddressDto musicSchoolDtoToSave3() {
        return MusicSchoolWithAddressDto.builder()
                .musicSchoolId(null)
                .musicSchoolName("musicSchoolName3")
                .musicSchoolPatron("musicSchoolPatron3")
                .musicSchoolPrimaryDegree(true)
                .musicSchoolSecondaryDegree(false)
                .addressCountry("addressCountry3")
                .addressCity("addressCity3")
                .addressPostalCode("78-103")
                .addressStreet("addressStreet3")
                .addressBuildingNumber("203")
                .addressAdditionalInfo("addressAdditionalInfo3")
                .build();
    }
    public static MusicSchoolWithAddressDto musicSchoolDtoSaved3() {
        return MusicSchoolWithAddressDto.builder()
                .musicSchoolId("fcefef81-fa54-4d1b-bf5e-971efa113d64")
                .musicSchoolName("musicSchoolName3")
                .musicSchoolPatron("musicSchoolPatron3")
                .musicSchoolPrimaryDegree(true)
                .musicSchoolSecondaryDegree(false)
                .addressCountry("addressCountry3")
                .addressCity("addressCity3")
                .addressPostalCode("78-103")
                .addressStreet("addressStreet3")
                .addressBuildingNumber("203")
                .addressAdditionalInfo("addressAdditionalInfo3")
                .build();
    }

    public static List<MusicSchoolWithAddressDto> musicSchoolDtoList() {
        return List.of(
                musicSchoolDtoSaved1(),
                musicSchoolDtoSaved2(),
                musicSchoolDtoSaved3()
        );
    }

    public static MusicSchoolsDto musicSchoolsDto() {
        return MusicSchoolsDto.builder().musicSchoolDtoList(musicSchoolDtoList()).build();
    }

}
