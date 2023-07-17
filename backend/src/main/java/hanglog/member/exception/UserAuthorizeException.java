package hanglog.member.exception;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public abstract class UserAuthorizeException extends RuntimeException{

    public UserAuthorizeException(String message) {
        super(message);
    }
}
