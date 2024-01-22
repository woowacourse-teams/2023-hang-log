package hanglog.admin;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean matches(final String password, final String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
