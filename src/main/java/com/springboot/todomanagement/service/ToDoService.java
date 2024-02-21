package com.springboot.todomanagement.service;

import com.springboot.todomanagement.dto.ToDoDto;
import com.springboot.todomanagement.entity.ToDo;
import com.springboot.todomanagement.security.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface ToDoService {
    ToDoDto createToDo(ToDoDto toDoDto,Long idUser);


    ToDoDto getToDoById(Long id);
    List<ToDoDto> getAllToDo(Long idUser);
    ToDoDto updateToDo(long id,ToDoDto toDoDto);
    void deleteToDo(long id);
    ToDoDto completeToDo(Long id);
    ToDoDto incompleteToDo(Long id);
}
