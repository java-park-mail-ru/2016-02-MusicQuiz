package database;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

/**
 * Created by seven-teen on 03.04.16.
 */
@Entity
@Table(name="User")
public class UsersDataSet {
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


    public UsersDataSet() {
        id = -1;
        login = "";
        password = "";
        email = "";
    }

    public UsersDataSet(UsersDataSet user) {
        this(user.login, user.password, user.email);
    }

    public UsersDataSet(@NotNull String login, @NotNull String password, @NotNull String email){
        this.id = 0;
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
