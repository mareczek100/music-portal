package mareczek100.instrumentstorage.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ErrorRestController.HOME)
public class ErrorRestController implements ErrorController {
    public static final String ERROR = "/error";
    public static final String HOME = "/";

    @Operation(summary = "Error message in bad endpoint case.")
    @GetMapping
    public ResponseEntity<String> error() {
        return new ResponseEntity<>
                ("This endpoint is empty. Go to: server:port/instrument-storage/swagger-ui/index.html",
                        HttpStatus.BAD_REQUEST);
    }
    @Operation(summary = "Error message in bad endpoint case.")
    @GetMapping(ERROR)
    public ResponseEntity<String> errorEndpoint() {
        return new ResponseEntity<>
                ("This endpoint doesn't exist. To find api contract go to: server:port/instrument-storage/swagger-ui/index.html",
                        HttpStatus.BAD_REQUEST);
    }
}