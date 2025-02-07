package edu.jwtyummi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.jwtyummi.model.dto.JoinDto;
import edu.jwtyummi.model.entity.User;
import edu.jwtyummi.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;
    private final ObjectMapper objectMapper;

    @PostMapping("/join")
    public User join(JoinDto joinDto) throws JsonProcessingException {
        System.out.println("joinDto = " + joinDto);
        User user = joinService.join(joinDto);
//        return objectMapper.writeValueAsString(user);
        return user;
    }
}
