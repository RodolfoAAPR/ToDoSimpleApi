package com.rodolfoalves.todosimple.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rodolfoalves.todosimple.models.enums.ProfileEnum;
import com.rodolfoalves.todosimple.models.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    public interface CreateUser {
    }

    public interface UpdateUser {
    }

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", length = 60, nullable = false)
    @NotEmpty(groups = {com.rodolfoalves.todosimple.models.user.CreateUser.class, com.rodolfoalves.todosimple.models.user.UpdateUser.class})
    @NotNull(groups = {com.rodolfoalves.todosimple.models.user.CreateUser.class, com.rodolfoalves.todosimple.models.user.UpdateUser.class})
    @Size(groups = {com.rodolfoalves.todosimple.models.user.CreateUser.class, com.rodolfoalves.todosimple.models.user.UpdateUser.class}, min = 6, max = 60)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> tasks = new ArrayList<Task>();

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles(){
        return this.profiles.stream().map(ProfileEnum::toEnum).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum) {
        this.profiles.add(profileEnum.getCode());
    }


}
