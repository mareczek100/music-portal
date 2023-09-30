package mareczek100.musiccontests.infrastructure.configuration;
import com.fasterxml.jackson.databind.ObjectMapper;
import mareczek100.infrastructure.ApiClient;
import mareczek100.infrastructure.api.InstrumentCategoryRestControllerApi;
import mareczek100.infrastructure.api.InstrumentRestControllerApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${api.instrument-storage.url}")
    private String INSTRUMENT_STORAGE_URL;

    @Bean
    public WebClient webClient(ObjectMapper objectMapper) {
        final var strategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> {
                    clientCodecConfigurer
                            .defaultCodecs()
                            .jackson2JsonEncoder(
                                    new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON)
                            );
                    clientCodecConfigurer
                            .defaultCodecs()
                            .jackson2JsonDecoder(
                                    new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON)
                            );
                }).build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public ApiClient instrumentStorageApiClient(final WebClient webClient) {
        ApiClient apiClient = new ApiClient(webClient);
        apiClient.setBasePath(INSTRUMENT_STORAGE_URL);
        return apiClient;
    }

    @Bean
    public InstrumentRestControllerApi instrumentApi(final ApiClient instrumentStorageApiClient) {
        return new InstrumentRestControllerApi(instrumentStorageApiClient);
    }

    @Bean
    public InstrumentCategoryRestControllerApi instrumentCategoryApi(final ApiClient instrumentStorageApiClient) {
        return new InstrumentCategoryRestControllerApi(instrumentStorageApiClient);
    }

}
