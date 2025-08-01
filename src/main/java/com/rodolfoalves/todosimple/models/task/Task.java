package com.rodolfoalves.todosimple.models.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rodolfoalves.todosimple.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = Task.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
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

    @OneToMany
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<Task>();
}
