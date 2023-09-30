package mareczek100.instrumentstorage.test_data_storage;


import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;

import java.util.List;

public class InputEntityTestData {

    public static InstrumentEntity instrumentEntityToSave(){
        return InstrumentEntity.builder()
                .name("testInstrument1")
                .category(instrumentCategoryEntity1())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentEntity instrumentEntity1(){
        return InstrumentEntity.builder()
                .instrumentId(1)
                .name("testInstrument1")
                .category(instrumentCategoryEntity1())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentEntity instrumentEntity2(){
        return InstrumentEntity.builder()
                .instrumentId(2)
                .name("testInstrument2")
                .category(instrumentCategoryEntity1())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentEntity instrumentEntity3(){
        return InstrumentEntity.builder()
                .instrumentId(3)
                .name("testInstrument3")
                .category(instrumentCategoryEntity2())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentEntity instrumentEntity4(){
        return InstrumentEntity.builder()
                .instrumentId(4)
                .name("testInstrument4")
                .category(instrumentCategoryEntity2())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static InstrumentEntity instrumentEntity5() {
        return InstrumentEntity.builder()
                .instrumentId(5)
                .name("testInstrument5")
                .category(instrumentCategoryEntity3())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }

    public static List<InstrumentEntity> instrumentEntityList(){
        return List.of(
                instrumentEntity1(),
                instrumentEntity2(),
                instrumentEntity3(),
                instrumentEntity4(),
                instrumentEntity5()
        );
    }
    public static List<InstrumentEntity> instrumentEntityListByCategoryStrunowe(){
        return List.of(
                instrumentEntity1().withCategory(buildCategoryEntityStrunowe()),
                instrumentEntity2().withCategory(buildCategoryEntityStrunowe()),
                instrumentEntity3().withCategory(buildCategoryEntityStrunowe()),
                instrumentEntity4().withCategory(buildCategoryEntityStrunowe()),
                instrumentEntity5().withCategory(buildCategoryEntityStrunowe())
        );
    }

    private static InstrumentCategoryEntity buildCategoryEntityStrunowe() {
        return InstrumentCategoryEntity.builder()
                .categoryName(InstrumentCategoryName.strunowe)
                .build();
    }

    //INSTRUMENT CATEGORY

    public static InstrumentCategoryEntity instrumentCategoryEntity1(){
        return InstrumentCategoryEntity.builder()
                .instrumentCategoryId(Short.valueOf("1"))
                .categoryName(InstrumentCategoryName.strunowe)
                .build();
    }
    public static InstrumentCategoryEntity instrumentCategoryEntity2(){
        return InstrumentCategoryEntity.builder()
                .instrumentCategoryId(Short.valueOf("2"))
                .categoryName(InstrumentCategoryName.dęte)
                .build();
    }
    public static InstrumentCategoryEntity instrumentCategoryEntity3(){
        return InstrumentCategoryEntity.builder()
                .instrumentCategoryId(Short.valueOf("3"))
                .categoryName(InstrumentCategoryName.perkusyjne)
                .build();
    }

    public static List<InstrumentCategoryEntity> instrumentCategoryEntityList(){
        return List.of(
                instrumentCategoryEntity1(),
                instrumentCategoryEntity2(),
                instrumentCategoryEntity3()
        );
    }


}