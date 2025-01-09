package toy.board.basic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@SpringBootTest
public class BeanTest {

    @Autowired
    ApplicationContext ac;

    @Test
    void bean() {
        Map<String, ResponseBodyAdvice> beansOfType = ac.getBeansOfType(ResponseBodyAdvice.class);
        for (String s : beansOfType.keySet()) {
            System.out.println("s = " + s);
            System.out.println("v = " + beansOfType.get(s));
        }
    }
}
