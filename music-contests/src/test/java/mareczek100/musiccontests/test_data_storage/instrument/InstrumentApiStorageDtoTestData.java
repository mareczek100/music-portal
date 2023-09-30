package mareczek100.musiccontests.test_data_storage.instrument;




import mareczek100.infrastructure.model.InstrumentCategoryDto;
import mareczek100.infrastructure.model.InstrumentDto;

import java.util.List;

public class InstrumentApiStorageDtoTestData {

    public static InstrumentDto instrumentApiStorageToSave1() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved1().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved1().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved1().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryStrunowe());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageToSave2() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved2().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved2().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved2().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryStrunowe());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageToSave3() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved3().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved3().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved3().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryDęte());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageToSave4() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved4().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved4().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved4().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryPerkusyjne());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageToSave5() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved5().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved5().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved5().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryPerkusyjne());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageSaved1() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved1().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved1().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved1().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryStrunowe());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageSaved2() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved2().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved2().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved2().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryStrunowe());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageSaved3() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved3().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved3().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved3().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryDęte());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageSaved4() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved4().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved4().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved4().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryPerkusyjne());
        return instrumentDto;
    }
    public static InstrumentDto instrumentApiStorageSaved5() {
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName(InstrumentDomainTestData.instrumentSaved5().name());
        instrumentDto.setPrimarySchoolDegree(InstrumentDomainTestData.instrumentSaved5().primarySchoolDegree());
        instrumentDto.setSecondarySchoolDegree(InstrumentDomainTestData.instrumentSaved5().secondarySchoolDegree());
        instrumentDto.setCategory(buildCategoryPerkusyjne());
        return instrumentDto;
    }

    public static List<InstrumentDto> instrumentApiStorageList(){
        return List.of(
                instrumentApiStorageSaved1(),
                instrumentApiStorageSaved2(),
                instrumentApiStorageSaved3(),
                instrumentApiStorageSaved4(),
                instrumentApiStorageSaved5()
        );
    }

    public static InstrumentCategoryDto buildCategoryStrunowe() {
        InstrumentCategoryDto instrumentCategoryDto = new InstrumentCategoryDto();
        instrumentCategoryDto.setCategory(InstrumentCategoryDto.CategoryEnum.STRUNOWE);
        return instrumentCategoryDto;
    }
    public static InstrumentCategoryDto buildCategoryDęte() {
        InstrumentCategoryDto instrumentCategoryDto = new InstrumentCategoryDto();
        instrumentCategoryDto.setCategory(InstrumentCategoryDto.CategoryEnum.D_TE);
        return instrumentCategoryDto;
    }
    public static InstrumentCategoryDto buildCategoryPerkusyjne() {
        InstrumentCategoryDto instrumentCategoryDto = new InstrumentCategoryDto();
        instrumentCategoryDto.setCategory(InstrumentCategoryDto.CategoryEnum.PERKUSYJNE);
        return instrumentCategoryDto;
    }

}