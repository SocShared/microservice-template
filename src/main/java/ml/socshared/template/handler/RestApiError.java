package ml.socshared.template.handler;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import ml.socshared.template.exception.AswErrors;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(
        value = "RestApiError",
        description = "An object that stores error information"
)
public class RestApiError {
    private Integer status;
    private HttpStatus error;
    private String path;
    private String message;
    private LocalDateTime timestamp;
    private AswErrors errorCode;

    public RestApiError(Exception exc, HttpStatus status, ServletWebRequest webRequest, AswErrors errorCode) {
        this.status = status.value();
        this.error = status;
        Throwable rootCause = NestedExceptionUtils.getRootCause(exc);
        if (rootCause != null)
            this.message = rootCause.getMessage();
        this.timestamp = LocalDateTime.now();
        this.path = webRequest.getRequest().getRequestURI();
        this.errorCode = errorCode;
    }
}
