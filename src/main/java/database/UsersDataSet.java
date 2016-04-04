package database;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by seven-teen on 03.04.16.
 */
@Entity
@Table(name="User")
public class UsersDataSet {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="Login")
    @NotNull
    private String login;

    @Column(name="Password")
    @NotNull
    private String password;

    @Column(name="Email")
    @NotNull
    private String email;

    @SuppressWarnings("unused")
    public UsersDataSet() {
        id = -1;
        login = "l";
        password = "p";
        email = "e";
    }

    public UsersDataSet(UsersDataSet user) {
        this(user.login, user.password, user.email);
    }

    public UsersDataSet(@NotNull String login, @NotNull String password, @NotNull String email){
        this.id = ID_GENERATOR.getAndIncrement();
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;

        if(obj == null)
            return false;

        if(!(getClass() == obj.getClass()))
            return false;

        else{
            UsersDataSet tmp = (UsersDataSet) obj;
            return tmp.id==this.id;
        }
    }

    @Override
    public int hashCode(){
        Long identificator = this.id;
        return identificator.hashCode();
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
