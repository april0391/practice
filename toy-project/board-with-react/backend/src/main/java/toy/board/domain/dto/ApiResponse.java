package toy.board.domain.dto;

import lombok.Data;
import lombok.Getter;

@Data
public abstract class ApiResponse {
    protected String status;
    protected String message;

    public static <T> SuccessResponse<T> success() {
        return new SuccessResponse<>();
    }

    public static <T> FailResponse<T> fail() {
        return new FailResponse<>();
    }

    @Getter
    public static class SuccessResponse<T> extends ApiResponse {
        private T data;

        private SuccessResponse() {
        }

        public SuccessResponse(String status, String message, T data) {
            super.setStatus(status);
            super.setMessage(message);
            this.data = data;
        }

        public SuccessResponse<T> status(String status) {
            super.setStatus(status);
            return this;
        }

        public SuccessResponse<T> message(String message) {
            super.setMessage(message);
            return this;
        }

        public SuccessResponse<T> data(T data) {
            this.data = data;
            return this;
        }
    }

    @Getter
    public static class FailResponse<T> extends ApiResponse {
        private T error;

        private FailResponse() {
        }

        public FailResponse(String status, String message, T error) {
            super.setStatus(status);
            super.setMessage(message);
            this.error = error;
        }

        public FailResponse<T> status(String status) {
            super.setStatus(status);
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
