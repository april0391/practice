package toy.board.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import toy.board.domain.dto.ApiResponse;

import java.lang.annotation.Annotation;

@RequiredArgsConstructor
@ControllerAdvice
public class ApiSuccessResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final MessageSource ms;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Annotation[] annotations = returnType.getAnnotatedElement().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof ExceptionHandler) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String requestURI = servletRequest.getRequestURI();
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();

        return ApiResponse.success()
                .status(status)
                .message(ms.getMessage("user.signup.success", null, null))
                .data(body)
                .path(requestURI)
                .build();
    }

}

