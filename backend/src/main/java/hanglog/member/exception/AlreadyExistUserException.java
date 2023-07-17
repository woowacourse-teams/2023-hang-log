package hanglog.member.exception;

public class AlreadyExistUserException extends UserAuthorizeException {

    public AlreadyExistUserException(String message) {
        super(message);
    }
}
