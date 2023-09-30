package mareczek100.instrumentstorage.infrastructure.database.repository;

import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface InstrumentCategoryRepository {
    List<InstrumentCategoryEntity> findAllCategories();
    public Optional<InstrumentCategoryEntity> findCategoryById(Short instrumentCategoryEntityId);
    Optional<InstrumentCategoryEntity> findCategoryByName(String instrumentCategoryEntityName);

}