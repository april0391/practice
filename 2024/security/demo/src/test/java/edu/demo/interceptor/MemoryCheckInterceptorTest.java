package edu.demo.interceptor;

import edu.demo.util.MemoryUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class MemoryCheckInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    // MemoryUtil 클래스의 메서드를 스파이 처리하여 호출 여부 확인
    @SpyBean
    private MemoryUtil memoryUtil;

    @BeforeEach
    public void setUp() {
        Mockito.reset(memoryUtil); // 테스트 전마다 MemoryUtil의 메서드 호출 기록 초기화
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testMemoryCheckInterceptor() throws Exception {
        // 특정 경로에 대한 요청을 보냄
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());  // 상태 코드 200 확인

        // MemoryUtil의 printMemory 메서드가 호출되었는지 확인
        verify(memoryUtil).printMemory();
    }

}
