package database;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by seven-teen on 19.04.16.
 */
@SuppressWarnings("unused")
@Entity
@Table(name="Music")
public class MusicDataSet implements Serializable {

    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long trackID;

    @Column(name="Path")
    @NotNull
    private String path;

    @Column(name="Author")
    @NotNull
    private String author;

    @SuppressWarnings("unused")
    public MusicDataSet(){
        this.trackID = -1;
        this.path = "";
        this.author = "";
    }

    public long getID() {
        return this.trackID;
    }

    @NotNull
    public String getPath() { return this.path; }

    @NotNull
    public String getAuthor() { return this.author; }

    public void setPath(@NotNull String path) {this.path = path; }

    public void setAuthor(@NotNull String author) {this.author = author;}

}
