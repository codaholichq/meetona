package meetona.core.Dto.request;

import java.io.Serializable;

public record LoginDto(String username, String password) implements Serializable {}
