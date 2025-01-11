package toy.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ApiResponse {

    protected int status;
    protected String message;
    protected String path;
    protected String httpMethod;
    protected LocalDateTime timestamp;

    public static <T> SuccessResponseBuilder<T> success() {
        return new SuccessResponseBuilder<>();
    }

    public static <T> ErrorResponseBuilder<T> error() {
        return new ErrorResponseBuilder<>();
    }

    @Setter
    public static class SuccessResponseBuilder<T> {
        private int status;
        private String message;
        private T data;
        private String path;
        private String httpMethod;

        private SuccessResponseBuilder() {
        }

        public SuccessResponseBuilder<T> status(int status) {
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

        public SuccessResponseBuilder<T> httpMethod(String httpMethod) {
            this.setHttpMethod(httpMethod);
            return this;
        }

        public SuccessResponseBuilder<T> data(T data) {
            this.setData(data);
            return this;
        }

        public ApiSuccessResponse<T> build() {
            return new ApiSuccessResponse<>(status, message, data, path, httpMethod);
        }
    }

    @Getter
    public static class ApiSuccessResponse<T> extends ApiResponse {
        private final T data;

        public ApiSuccessResponse(int status,
                                  String message,
                                  T data,
                                  String path,
                                  String httpMethod) {
            super.status = status;
            super.message = message;
            this.data = data;
            super.path = path;
            super.httpMethod = httpMethod;
            super.timestamp = LocalDateTime.now();
        }
    }

    @Setter
    public static class ErrorResponseBuilder<T> {
        private int status;
        private String message;
        private T error;
        private String path;
        private String httpMethod;

        private ErrorResponseBuilder() {
        }

        public ErrorResponseBuilder<T> status(int status) {
            this.setStatus(status);
            return this;
        }

        public ErrorResponseBuilder<T> message(String message) {
            this.setMessage(message);
            return this;
        }

        public ErrorResponseBuilder<T> path(String path) {
            this.setPath(path);
            return this;
        }

        public ErrorResponseBuilder<T> httpMethod(String httpMethod) {
            this.setHttpMethod(httpMethod);
            return this;
        }

        public ErrorResponseBuilder<T> error(T error) {
            this.setError(error);
            return this;
        }

        public ApiErrorResponse<T> build() {
            return new ApiErrorResponse<>(status, message, error, path, httpMethod);
        }
    }

    @Getter
    public static class ApiErrorResponse<T> extends ApiResponse {
        private final T error;

        public ApiErrorResponse(int status,
                                String message,
                                T error,
                                String path,
                                String httpMethod) {
            super.status = status;
            super.message = message;
            this.error = error;
            super.path = path;
            super.httpMethod = httpMethod;
            super.timestamp = LocalDateTime.now();
        }

    }
}
