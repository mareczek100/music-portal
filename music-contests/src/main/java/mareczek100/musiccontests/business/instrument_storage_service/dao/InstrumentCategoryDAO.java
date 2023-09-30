package mareczek100.musiccontests.business.instrument_storage_service.dao;

import mareczek100.musiccontests.domain.instrument_storage_domain.InstrumentCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentCategoryDAO {
    List<InstrumentCategory> findAllCategories();

    InstrumentCategory findCategoryById(Integer instrumentCategoryId);

    InstrumentCategory findCategoryByName(String instrumentCategoryName);
}
