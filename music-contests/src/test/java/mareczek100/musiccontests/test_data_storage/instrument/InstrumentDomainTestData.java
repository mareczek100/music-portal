package mareczek100.musiccontests.test_data_storage.instrument;



import mareczek100.musiccontests.domain.instrument_storage_domain.Instrument;
import mareczek100.musiccontests.domain.instrument_storage_domain.InstrumentCategory;

import java.util.List;

public class InstrumentDomainTestData {

    public static Instrument instrumentSaved1(){
        return Instrument.builder()
                .instrumentId("a88e00d8-ef55-4fb0-80cf-d7b454230b0b")
                .name("testInstrument1")
                .category(buildCategoryStrunowe())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentSaved2(){
        return Instrument.builder()
                .instrumentId("6cd043a4-0495-4b0e-80da-e0b8c7fdacd3")
                .name("testInstrument2")
                .category(buildCategoryStrunowe())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentSaved3(){
        return Instrument.builder()
                .instrumentId("f8ec2ded-c4ef-428e-83f8-c5f83bcdacd8")
                .name("testInstrument3")
                .category(buildCategoryDęte())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentSaved4(){
        return Instrument.builder()
                .instrumentId("53c60873-330f-4cb2-8979-0779519db27d")
                .name("testInstrument4")
                .category(buildCategoryPerkusyjne())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentSaved5() {
        return Instrument.builder()
                .instrumentId("9c4f2de1-a848-4bf6-bf11-a40715507bb0")
                .name("testInstrument5")
                .category(buildCategoryPerkusyjne())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentToSave1(){
        return Instrument.builder()
                .instrumentId(null)
                .name("testInstrument1")
                .category(buildCategoryStrunowe())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentToSave2(){
        return Instrument.builder()
                .instrumentId(null)
                .name("testInstrument2")
                .category(buildCategoryStrunowe())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentToSave3(){
        return Instrument.builder()
                .instrumentId(null)
                .name("testInstrument3")
                .category(buildCategoryDęte())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentToSave4(){
        return Instrument.builder()
                .instrumentId(null)
                .name("testInstrument4")
                .category(buildCategoryPerkusyjne())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }
    public static Instrument instrumentToSave5() {
        return Instrument.builder()
                .instrumentId(null)
                .name("testInstrument5")
                .category(buildCategoryPerkusyjne())
                .primarySchoolDegree(true)
                .secondarySchoolDegree(true)
                .build();
    }

    public static List<Instrument> instrumentList(){
        return List.of(
                instrumentSaved1(),
                instrumentSaved2(),
                instrumentSaved3(),
                instrumentSaved4(),
                instrumentSaved5()
        );
    }

    public static InstrumentCategory buildCategoryStrunowe() {
        return InstrumentCategory.builder()
                .instrumentCategoryId("494f547b-6e71-4719-9315-2b00f96fe472")
                .instrumentCategory("STRUNOWE")
                .build();
    }
    public static InstrumentCategory buildCategoryDęte() {
        return InstrumentCategory.builder()
                .instrumentCategoryId("ad37f0ab-9de9-4a35-9e8e-06d057ed37a4")
                .instrumentCategory("DĘTE")
                .build();
    }
    public static InstrumentCategory buildCategoryPerkusyjne() {
        return InstrumentCategory.builder()
                .instrumentCategoryId("996283fc-944e-45fe-8613-6c6b872c40c4")
                .instrumentCategory("PERKUSYJNE")
                .build();
    }


}