package com.rodolfoalves.todosimple.repositories.task;

import com.rodolfoalves.todosimple.models.projection.TaskProjection;
import com.rodolfoalves.todosimple.models.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
    List<TaskProjection> findByUser_Id(@Param("id") Long id);
}
