package edu.demo.interceptor;

import edu.demo.util.MemoryUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MemoryCheckInterceptor implements HandlerInterceptor {

    private final Logger log = LoggerFactory.getLogger(MemoryCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        MemoryUtil.printMemory();
        return true;
    }
}
