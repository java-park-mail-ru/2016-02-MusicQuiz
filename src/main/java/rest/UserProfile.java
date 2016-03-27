package rest;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by IlyaRogov on 29.02.16.
 */
public class UserProfile {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private long id;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String email;

    public UserProfile() {
        id = -1;
        login = "l";
        password = "p";
        email = "e";
    }

    public UserProfile(UserProfile user) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.email = user.getEmail();
    }

    public long getID() {
        return this.id;
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

    @NotNull
    public String getEmail() { return email; }

    public void setEmail(@NotNull String email) { this.email = email; }
}