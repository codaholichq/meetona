package meetona.core.event;


import meetona.core.entity.User;
import org.springframework.lang.NonNull;

public record SignedUpEvent(@NonNull User user) {
    public User getUser(User user) {
        return user;
    }
}
