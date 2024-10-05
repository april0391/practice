package edu.restservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class GreetingController {

    public static final AtomicInteger id = new AtomicInteger();
    public static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
        return new Greeting(id.incrementAndGet(), template.formatted(name));
    }

    @GetMapping("/api/random")
    public Object quote() throws JsonProcessingException {
        // 1. Map으로 데이터를 구성
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("id", 123);
        valueMap.put("quote", "This is a quote.");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("type", "success");
        responseMap.put("value", valueMap);

        // 2. ObjectMapper를 사용해 Map을 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseMap);

        // 3. JSON 문자열을 반환 (API 응답)
        return jsonResponse;
    }

}
