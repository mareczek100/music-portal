package mareczek100.instrumentstorage.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import mareczek100.instrumentstorage.InstrumentStorageApplication;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .packagesToScan(InstrumentStorageApplication.class.getPackageName())
                .build();
    }

    @Bean
    public OpenAPI springDocOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Instrument Storage API")
                        .contact(new Contact()
                                .name("Marek Jankowski")
                                .email("mareczek100@wp.pl"))
                        .version("1.0"));
    }
}