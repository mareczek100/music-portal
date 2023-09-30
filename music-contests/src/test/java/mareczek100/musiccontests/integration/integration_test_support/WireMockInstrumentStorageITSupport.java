package mareczek100.musiccontests.integration.integration_test_support;

import com.github.tomakehurst.wiremock.WireMockServer;
import mareczek100.musiccontests.api.controller.rest_controller.controller_rest_support.ControllerRestSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface WireMockInstrumentStorageITSupport extends ControllerRestSupport {

    default void stubForAllInstrumentList(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathMatching("/api/instrument"))
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/instruments.json")));
    }


}
