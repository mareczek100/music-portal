package mareczek100.instrumentstorage.infrastructure.database.repository;

import lombok.RequiredArgsConstructor;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;
import mareczek100.instrumentstorage.infrastructure.database.repository.jpa.InstrumentJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InstrumentRepositoryImpl implements InstrumentRepository{

    private final InstrumentJpaRepository instrumentJpaRepository;

    public InstrumentEntity insertInstrument(InstrumentEntity instrumentEntity) {
        if (instrumentEntity.getName().length() > 40){
            throw new RuntimeException("Instrument name too long, max length is 40 characters!");
        }
        return instrumentJpaRepository.saveAndFlush(instrumentEntity);
    }

    @Override
    public InstrumentEntity updateInstrument(InstrumentEntity instrumentEntityWithId) {
        return insertInstrument(instrumentEntityWithId);
    }

    public Optional<InstrumentEntity> findInstrumentById(Integer instrumentEntityId) {
        return instrumentJpaRepository.findById(instrumentEntityId);
    }

    public Optional<InstrumentEntity> findInstrumentByName(String instrumentEntityName) {
        return instrumentJpaRepository.findInstrumentByName(instrumentEntityName);
    }

    public List<InstrumentEntity> findInstrumentsByCategoryName(String instrumentEntityCategory) {
        List<String> categoryNames = Arrays.stream(InstrumentCategoryName.values())
                .map(Enum::name)
                .toList();
        boolean anyMatchInstrumentCategory = categoryNames.stream()
                .anyMatch(categoryName -> categoryName.equalsIgnoreCase(instrumentEntityCategory));

        if (!anyMatchInstrumentCategory) {
            throw new RuntimeException(("Category [%s] doesn't exist!%nAvailable categories: %s")
                    .formatted(instrumentEntityCategory, categoryNames));
        }
        return instrumentJpaRepository.findInstrumentsByCategory(
                InstrumentCategoryName.valueOf(instrumentEntityCategory));
    }
    public List<InstrumentEntity> findAllInstruments() {
        return instrumentJpaRepository.findAll();
    }

    public void deleteInstrumentByName(InstrumentEntity instrumentEntity){
        instrumentJpaRepository.delete(instrumentEntity);
    }
}