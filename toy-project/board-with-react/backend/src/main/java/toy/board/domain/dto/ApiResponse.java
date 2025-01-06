package toy.board.domain.dto;

import lombok.Data;
import lombok.Getter;

@Data
public abstract class ApiResponse {
    protected String status;
    protected String message;

    public static <T> ApiSuccessResponse<T> success() {
        return new ApiSuccessResponse<>();
    }

    public static <T> ApiFailResponse<T> fail() {
        return new ApiFailResponse<>();
    }

    @Getter
    public static class ApiSuccessResponse<T> extends ApiResponse {
        private T data;

        private ApiSuccessResponse() {
        }

        public ApiSuccessResponse(String status, String message, T data) {
            super.setStatus(status);
            super.setMessage(message);
            this.data = data;
        }

        public ApiSuccessResponse<T> status(String status) {
            super.setStatus(status);
            return this;
        }

        public ApiSuccessResponse<T> message(String message) {
            super.setMessage(message);
            return this;
        }

        public ApiSuccessResponse<T> data(T data) {
            this.data = data;
            return this;
        }
    }

    @Getter
    public static class ApiFailResponse<T> extends ApiResponse {
        private T error;

        private ApiFailResponse() {
        }

        public ApiFailResponse(String status, String message, T error) {
            super.setStatus(status);
            super.setMessage(message);
            this.error = error;
        }

        public ApiFailResponse<T> status(String status) {
            super.setStatus(status);
            return this;
        }

        public ApiFailResponse<T> message(String message) {
            super.setMessage(message);
            return this;
        }

        public ApiFailResponse<T> error(T error) {
            this.error = error;
            return this;
        }
    }
}
