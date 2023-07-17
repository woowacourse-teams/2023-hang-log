package hanglog.member.exception;

public abstract class UserAuthorizeException extends RuntimeException {

    public UserAuthorizeException(String message) {
        super(message);
    }
}
