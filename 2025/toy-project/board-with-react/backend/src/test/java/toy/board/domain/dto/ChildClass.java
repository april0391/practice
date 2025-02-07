package toy.board.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class ChildClass extends ParentClass {
    private Object data;

    public ChildClass() {
        super.message = "테스트";
    }




}
