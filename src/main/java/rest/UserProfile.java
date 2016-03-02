package rest;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IlyaRogov on 29.02.16.
 */
public class UserProfile {
    private static final AtomicLong ID_GENETATOR = new AtomicLong(0);
    @NotNull
    private Long id;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String email;

    public UserProfile(@NotNull String login, @NotNull String password, @NotNull String email) {
        this.id = ID_GENETATOR.getAndIncrement();
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @NotNull
    public Long getID() {
        return id;
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NotNull String login) {
        this.login = login;
    }

    @NotNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }
}