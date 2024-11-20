package toy.board.util;

public final class JwtConst {

    public static final String ACCESS_TOKEN_NAME = "access";
    public static final String REFRESH_TOKEN_NAME = "refresh";

    public static final int EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN = 60;
    public static final int EXPIRATION_TIME_SECOND_OF_REFRESH_TOKEN = 300;

    private JwtConst() {
    }
}
