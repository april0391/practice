package toy.board.domain.dto;

import lombok.Data;

@Data
public class ParentClass {
    protected String status;
    protected String message;

    public ParentClass() {
        System.out.println("ParentClass.ParentClass");
    }
}
