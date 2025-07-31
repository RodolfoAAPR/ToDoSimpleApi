package com.rodolfoalves.todosimple.repositories.user;

import com.rodolfoalves.todosimple.models.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    User findByUsername(String username);
}
