package toy.board.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import toy.board.domain.dto.ApiResponse;

@RequiredArgsConstructor
@ControllerAdvice(annotations = RestController.class)
public class ApiSuccessResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final MessageSource ms;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return converterType.isAssignableFrom(MappingJackson2HttpMessageConverter.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String requestURI = servletRequest.getRequestURI();
        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int status = servletResponse.getStatus();

        return ApiResponse.success()
                .status(status)
                .message(getMessage(returnType))
                .data(body)
                .path(requestURI)
                .httpMethod(servletRequest.getMethod())
                .build();
    }

    private String getMessage(MethodParameter returnType) {
        String className = returnType.getExecutable().getDeclaringClass().getSimpleName();
        String methodName = returnType.getExecutable().getName();
        String messageKey = className + "." + methodName;

        String message = null;
        try {
            message = ms.getMessage(messageKey, null, null);
        } catch (NoSuchMessageException e) {
            message = "Default success message";
        }
        return message;
    }

}

