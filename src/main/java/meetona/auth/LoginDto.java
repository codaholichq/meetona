package meetona.auth;

import java.io.Serializable;

public record LoginDto(String username, String password) implements Serializable {}
