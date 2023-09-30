package mareczek100.instrumentstorage.service;

import lombok.RequiredArgsConstructor;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryName;
import mareczek100.instrumentstorage.infrastructure.database.repository.InstrumentCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrumentCategoryService {

    private final InstrumentCategoryRepository instrumentCategoryRepository;

    @Transactional
    public List<InstrumentCategoryEntity> findAllInstrumentCategories() {
        List<InstrumentCategoryEntity> allInstrumentCategories = instrumentCategoryRepository.findAllCategories();
        if (allInstrumentCategories.isEmpty()) {
            throw new RuntimeException("We have no instrument categories right now. Sorry!");
        }
        return allInstrumentCategories;
    }

    @Transactional
    public InstrumentCategoryEntity findInstrumentCategoryById(Short instrumentCategoryEntityId) {
        return instrumentCategoryRepository.findCategoryById(instrumentCategoryEntityId).orElseThrow(
                () -> new RuntimeException("Instrument category with id number [%s] doesn't exist! Put 1, 2 or 3."
                        .formatted(instrumentCategoryEntityId))
        );
    }

    @Transactional
    public InstrumentCategoryEntity findInstrumentCategoryByName(String instrumentCategoryEntityName) {
        List<String> categoryNames = Arrays.stream(InstrumentCategoryName.values())
                .map(Enum::name)
                .toList();
        if (!categoryNames.contains(instrumentCategoryEntityName)) {
            throw new RuntimeException("Instrument category [%s] doesn't exist, sorry!%nAvailable categories: %s."
                    .formatted(instrumentCategoryEntityName, categoryNames));
        }
        return instrumentCategoryRepository.findCategoryByName(instrumentCategoryEntityName).get();
    }
}