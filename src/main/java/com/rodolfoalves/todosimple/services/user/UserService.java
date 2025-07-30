package com.rodolfoalves.todosimple.services.user;

import com.rodolfoalves.todosimple.models.user.User;
import com.rodolfoalves.todosimple.repositories.user.UserRepository;
import com.rodolfoalves.todosimple.services.exceptions.DataBindingViolationException;
import com.rodolfoalves.todosimple.services.exceptions.ObjectNotFoundException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
                "Usuário não encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User createUser(User obj) {
        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User updateUser(User obj){
        User newObj = findById(obj.getId());
        newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        return this.userRepository.save(newObj);
    }

    @Transactional
    public void deleteUser(Long id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch(Exception error) {
            throw new DataBindingViolationException(
                    "Não é possível excluir o usuário porquê não há entidades relacionadas."
            );
        }
    }
}
