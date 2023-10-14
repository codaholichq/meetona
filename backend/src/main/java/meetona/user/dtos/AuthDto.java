package meetona.user.dtos;

import java.io.Serializable;

public record AuthDto(String username, String password) implements Serializable {}
