package com.rodolfoalves.todosimple.models.user;

import com.rodolfoalves.todosimple.models.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser {}
    public interface UpdateUser {}

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = com.rodolfoalves.todosimple.models.user.CreateUser.class)
    @NotEmpty(groups = com.rodolfoalves.todosimple.models.user.CreateUser.class)
    @Size(min = 2, max = 100)
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    @NotEmpty(groups = {com.rodolfoalves.todosimple.models.user.CreateUser.class, com.rodolfoalves.todosimple.models.user.UpdateUser.class})
    @NotNull(groups = {com.rodolfoalves.todosimple.models.user.CreateUser.class, com.rodolfoalves.todosimple.models.user.UpdateUser.class})
    @Size(groups = {com.rodolfoalves.todosimple.models.user.CreateUser.class, com.rodolfoalves.todosimple.models.user.UpdateUser.class}, min = 6, max = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<Task>();

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User(){}

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return false;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;

        User other = (User) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        }

        return Objects.equals(id, other.id) && Objects.equals(username, other.username)
                && Objects.equals(password, other.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }
}
