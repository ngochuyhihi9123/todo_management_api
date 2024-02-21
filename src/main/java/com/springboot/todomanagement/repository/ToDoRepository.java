package com.springboot.todomanagement.repository;

import com.springboot.todomanagement.entity.ToDo;
import com.springboot.todomanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Long> {
    List<ToDo> findAllByUser(User user);
}
