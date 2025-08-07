package com.rodolfoalves.todosimple.services.task;

import com.rodolfoalves.todosimple.models.enums.ProfileEnum;
import com.rodolfoalves.todosimple.models.task.Task;
import com.rodolfoalves.todosimple.models.user.User;
import com.rodolfoalves.todosimple.repositories.task.TaskRepository;
import com.rodolfoalves.todosimple.security.UserSpringSecurity;
import com.rodolfoalves.todosimple.services.exceptions.AuthorizationException;
import com.rodolfoalves.todosimple.services.exceptions.DataBindingViolationException;
import com.rodolfoalves.todosimple.services.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id){
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new RuntimeException(
                "Não foi possível localizar a task! Id: " + id + "Tipo: " + Task.class.getName()));

        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN)
                && userHasTask(userSpringSecurity, task))
            throw new AuthorizationException("Acesso negado!");
        return task;
    }

    public List<Task> findAllByUser(){
        UserSpringSecurity userSpringSecurity = UserService.authenticated();
        if(Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");

        List<Task> tasks = this.taskRepository.findByUser_Id(userSpringSecurity.getId());
        return tasks;
    }

    @Transactional
    public Task createTask(Task obj){
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task updateTask(Task obj){
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    @Transactional
    public void deleteTask(Long id){
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception error){
            throw new DataBindingViolationException(
                    "Erro ao apagar a task!"
            );
        }
    }

    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task){
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }
}
