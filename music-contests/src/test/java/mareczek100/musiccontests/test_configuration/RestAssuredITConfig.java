package mareczek100.musiccontests.test_configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;

@TestConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestAssuredITConfig extends MusicContestsApplicationTests {
    protected static WireMockServer wireMockServer;
    @Autowired
    protected ObjectMapper objectMapper;
    @LocalServerPort
    private int serverPort;
    @Autowired
    private Flyway flyway;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig()
                        .port(9999)
                        .extensions(new ResponseTemplateTransformer(false))
        );
        wireMockServer.start();
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp() {
        Assertions.assertNotNull(objectMapper);
        Assertions.assertNotNull(flyway);
        flyway.clean();
        flyway.migrate();
        wireMockServer.resetAll();
    }

    public RequestSpecification requestSpecification() {
        return RestAssured
                .given()
                .config(getConfig())
                .log().all()
                .basePath(MUSIC_CONTESTS_HOME)
                .port(serverPort)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header("Content-Type", "application/json");
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig
                .config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}