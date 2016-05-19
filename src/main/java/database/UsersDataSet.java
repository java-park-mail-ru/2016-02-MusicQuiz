package database;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

/**
 * Created by seven-teen on 03.04.16.
 */
@Entity
@Table(name="User") //индексов пока нет
public class UsersDataSet implements Comparable{
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long userID;

    @Column(name="Login", unique = true)
    @NotNull
    private String login;

    @Column(name="Password")
    @NotNull
    private String password;

    @Column(name="Email", unique = true)
    @NotNull
    private String email;

    @Column(name="Points")
    private int points;
    @SuppressWarnings("unused")
    public UsersDataSet() {
        userID = -1;
        login = "";
        password = "";
        email = "";
        points = 0;
    }

    public UsersDataSet(UsersDataSet user) {
        this(user.login, user.password, user.email);
    }

    public UsersDataSet(@NotNull String login, @NotNull String password, @NotNull String email){
        this.userID = 0;
        this.login = login;
        this.password = password;
        this.email = email;
        this.points = 0;
    }

    public long getID() {
        return this.userID;
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

    public int getPoints() { return this.points; }

    public void setPoints(int points) { this.points = points; }

    @Override
    public int compareTo(Object o) {
        UsersDataSet tmp = (UsersDataSet)o;
        if(this.points < tmp.points)
            return -1;
        else if(this.points > tmp.points)
            return 1;
        return 0;
    }
}
