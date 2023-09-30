package mareczek100.instrumentstorage.test_configuration;

import mareczek100.instrumentstorage.InstrumentStorageApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(PersistenceTestConfig.class)
@SpringBootTest(
        classes = {InstrumentStorageApplication.class},
        properties = "spring.flyway.clean-disabled=false",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AppTestConfig {
    @Test
    void contextLoads() {
    }
}