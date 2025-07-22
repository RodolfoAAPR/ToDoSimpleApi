package com.rodolfoalves.todosimple.models.task;

import com.rodolfoalves.todosimple.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = Task.TABLE_NAME)
public class Task {
    public static final String TABLE_NAME = "task";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "description", nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 255)
    private String description;

    public Task(){}

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return false;
        if (obj == null)
            return false;
        if (!(obj instanceof Task))
            return false;

        Task other = (Task) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        }

        return Objects.equals(id, other.id) && Objects.equals(user, other.user)
                && Objects.equals(description, other.description);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }

    public Task(Long id, User user, String description) {
        this.id = id;
        this.user = user;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
