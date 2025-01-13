package toy.board.domain.dto.user;

public record LoginRequest(
        String email,
        String password
) {
}
