package mareczek100.instrumentstorage.test_data_storage;

import mareczek100.instrumentstorage.api.dto.InstrumentCategoriesDto;
import mareczek100.instrumentstorage.api.dto.InstrumentCategoryDto;
import mareczek100.instrumentstorage.api.dto.InstrumentDto;
import mareczek100.instrumentstorage.api.dto.InstrumentsDto;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;

import java.util.Arrays;
import java.util.List;

public class InputDtoTestData {

    public static InstrumentDto instrumentDto1(){
        return InstrumentDto.builder()
                .name("testInstrument1")
                .category(buildCategoryDto())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentDto instrumentDto2(){
        return InstrumentDto.builder()
                .name("testInstrument2")
                .category(buildCategoryDto())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentDto instrumentDto3(){
        return InstrumentDto.builder()
                .name("testInstrument3")
                .category(buildCategoryDto())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentDto instrumentDto4(){
        return InstrumentDto.builder()
                .name("testInstrument4")
                .category(buildCategoryDto())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentDto instrumentDto5() {
        return InstrumentDto.builder()
                .name("testInstrument5")
                .category(buildCategoryDto())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }

    public static List<InstrumentDto> instrumentDtoList(){
        return List.of(
                instrumentDto1(),
                instrumentDto2(),
                instrumentDto3(),
                instrumentDto4(),
                instrumentDto5()
        );
    }
    public static List<InstrumentDto> instrumentDtoListByCategoryStrunowe(){
        return List.of(
                instrumentDto1().withCategory(buildCategoryDtoStrunowe()),
                instrumentDto2().withCategory(buildCategoryDtoStrunowe()),
                instrumentDto3().withCategory(buildCategoryDtoStrunowe()),
                instrumentDto4().withCategory(buildCategoryDtoStrunowe()),
                instrumentDto5().withCategory(buildCategoryDtoStrunowe())
        );
    }

    public static InstrumentsDto instrumentsDto(){
        return InstrumentsDto.builder()
                .instrumentDtoList(instrumentDtoList())
                .build();
    }

    private static InstrumentCategoryDto buildCategoryDto() {
        return InstrumentCategoryDto.builder()
                .category(Arrays.stream(
                        InstrumentCategoryName.values()).findAny().orElse(InstrumentCategoryName.strunowe))
                .build();
    }
    private static InstrumentCategoryDto buildCategoryDtoStrunowe() {
        return InstrumentCategoryDto.builder()
                .category(InstrumentCategoryName.strunowe)
                .build();
    }

    //INSTRUMENT CATEGORY

    public static InstrumentCategoryDto instrumentCategoryDto1(){
        return InstrumentCategoryDto.builder()
                .category(InstrumentCategoryName.strunowe)
                .build();
    }
    public static InstrumentCategoryDto instrumentCategoryDto2(){
        return InstrumentCategoryDto.builder()
                .category(InstrumentCategoryName.dęte)
                .build();
    }
    public static InstrumentCategoryDto instrumentCategoryDto3(){
        return InstrumentCategoryDto.builder()
                .category(InstrumentCategoryName.perkusyjne)
                .build();
    }

    public static List<InstrumentCategoryDto> instrumentCategoryDtoList(){
        return List.of(
                instrumentCategoryDto1(),
                instrumentCategoryDto2(),
                instrumentCategoryDto3()
        );
    }
    public static InstrumentCategoriesDto instrumentCategoriesDto(){
        return InstrumentCategoriesDto.builder()
                .instrumentCategoryDtoList(instrumentCategoryDtoList())
                .build();
    }
}