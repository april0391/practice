package toy.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public abstract class ApiResponse {

    protected int status;
    protected String message;
    protected LocalDateTime timestamp;
    protected String path;

    public static <T> SuccessResponseBuilder<T> success() {
        return new SuccessResponseBuilder<>();
    }

    public static <T> FailResponse<T> fail() {
        return new FailResponse<>();
    }

    @Getter
    @Setter
    public static class SuccessResponseBuilder<T> {
        private HttpStatus status;
        private String message;
        private T data;
        private String path;

        private SuccessResponseBuilder() {
        }

        public SuccessResponseBuilder<T> status(HttpStatus status) {
            this.setStatus(status);
            return this;
        }

        public SuccessResponseBuilder<T> message(String message) {
            this.setMessage(message);
            return this;
        }

        public SuccessResponseBuilder<T> path(String path) {
            this.setPath(path);
            return this;
        }

        public SuccessResponseBuilder<T> data(T data) {
            this.setData(data);
            return this;
        }

        public ApiSuccessResponse<T> build() {
            return new ApiSuccessResponse<>(status, message, data, path);
        }
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ApiSuccessResponse<T> extends ApiResponse {
        private final T data;

        public ApiSuccessResponse(HttpStatus status,
                                  String message,
                                  T data,
                                  String path) {
            super.status = status.value();
            super.message = message;
            this.data = data;
            super.path = path;
            super.timestamp = LocalDateTime.now();
        }
    }


    @Getter
    public static class FailResponse<T> extends ApiResponse {
        private T error;

        private FailResponse() {
        }

        public FailResponse(HttpStatus status, String message, T error) {
//            super.setStatus(status);
            super.setMessage(message);
            this.error = error;
        }

        public FailResponse<T> status(HttpStatus status) {
//            super.setStatus(status);
            return this;
        }

        public FailResponse<T> message(String message) {
            super.setMessage(message);
            return this;
        }

        public FailResponse<T> error(T error) {
            this.error = error;
            return this;
        }
    }
}
