package hanglog.admin;

public interface PasswordEncoder {
    String encode(String password);

    boolean matches(String password, String hashed);
}
