package mareczek100.instrumentstorage.service;

import lombok.RequiredArgsConstructor;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentCategoryEntity;
import mareczek100.instrumentstorage.infrastructure.database.entity.InstrumentEntity;
import mareczek100.instrumentstorage.infrastructure.database.repository.InstrumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final InstrumentCategoryService instrumentCategoryService;
    @Transactional
    public InstrumentEntity insertNewInstrument(InstrumentEntity instrumentEntity) {
        if (findAllInstruments().contains(instrumentEntity)){
            throw new RuntimeException("Instrument [%s] already exist in our storage! Add instrument with other name."
                    .formatted(instrumentEntity.getName()));
        }
        InstrumentCategoryEntity instrumentCategory =
                instrumentCategoryService.findInstrumentCategoryByName(instrumentEntity.getCategory().getCategoryName().name());

        return instrumentRepository.insertInstrument(instrumentEntity.withCategory(instrumentCategory));
    }
    @Transactional
    public InstrumentEntity updateInstrument(InstrumentEntity instrumentEntityWithId) {
        return instrumentRepository.updateInstrument(instrumentEntityWithId);
    }
    @Transactional
    public InstrumentEntity findInstrumentById(Integer instrumentEntityId) {
        return instrumentRepository.findInstrumentById(instrumentEntityId).orElseThrow(
                () -> new RuntimeException("Instrument with id number [%s] doesn't exist!"
                        .formatted(instrumentEntityId))
        );
    }
    @Transactional
    public InstrumentEntity findInstrumentByName(String instrumentEntityName) {
        return instrumentRepository.findInstrumentByName(instrumentEntityName).orElseThrow(
                () -> new RuntimeException("Instrument [%s] doesn't exist in our storage, sorry!"
                        .formatted(instrumentEntityName)));
    }
    @Transactional
    public List<InstrumentEntity> findInstrumentsByCategory(String instrumentEntityCategory) {
        List<InstrumentEntity> instrumentByCategory = instrumentRepository.findInstrumentsByCategoryName(instrumentEntityCategory);
        if (instrumentByCategory.isEmpty()){
            throw new RuntimeException("We have no instruments in category [%s], sorry!"
                    .formatted(instrumentEntityCategory));
        }
        return instrumentByCategory;
    }
    @Transactional
    public List<InstrumentEntity> findAllInstruments() {
        List<InstrumentEntity> allInstruments = instrumentRepository.findAllInstruments();
        if (allInstruments.isEmpty()){
            throw new RuntimeException("We have no instruments at all - our storage is empty. Sorry!");
        }
        return allInstruments;
    }
    @Transactional
    public void deleteInstrumentByName(String instrumentEntityName){
        instrumentRepository.deleteInstrumentByName(findInstrumentByName(instrumentEntityName));
    }
}