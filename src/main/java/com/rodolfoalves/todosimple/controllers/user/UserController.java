package com.rodolfoalves.todosimple.controllers.user;

import com.rodolfoalves.todosimple.models.user.User;
import com.rodolfoalves.todosimple.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Validated(User.CreateUser.class)
    public ResponseEntity<Void> createUser(@Valid @RequestBody User obj){

        this.userService.createUser(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(User.UpdateUser.class)
    public ResponseEntity<User> updateUser(@Valid @RequestBody User obj, @PathVariable Long id){
        obj.setId(id);
        this.userService.updateUser(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
