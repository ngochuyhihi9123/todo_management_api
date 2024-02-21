package com.springboot.todomanagement.service.impl;

import com.springboot.todomanagement.dto.ToDoDto;
import com.springboot.todomanagement.entity.ToDo;
import com.springboot.todomanagement.entity.User;
import com.springboot.todomanagement.exception.ResourceNotFoundException;
import com.springboot.todomanagement.repository.ToDoRepository;
import com.springboot.todomanagement.repository.UserRepository;
import com.springboot.todomanagement.security.CustomUser;
import com.springboot.todomanagement.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ToDoDto createToDo(ToDoDto toDoDto,Long idUser) {
        ToDo toDo = new ToDo();
        toDo.setTitle(toDoDto.getTitle());
        toDo.setDescription(toDoDto.getDescription());
        toDo.setCompleted(toDoDto.isCompleted());

        User user = userRepository.findById(idUser).orElseThrow(
                ()->new ResourceNotFoundException("User","Id",String.valueOf(idUser)));

        toDo.setUser(user);

        ToDo toDoRes = toDoRepository.save(toDo);

        return new ToDoDto(toDoRes.getId(),toDoRes.getTitle(),toDoRes.getDescription(),toDoRes.isCompleted());
    }

    @Override
    public ToDoDto getToDoById(Long id) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("ToDo","Id",String.valueOf(id))
        );
        return new ToDoDto(toDo.getId(),toDo.getTitle(),toDo.getDescription(),toDo.isCompleted());
    }

    @Override
    public List<ToDoDto> getAllToDo(Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow(
                ()->new ResourceNotFoundException("User","Id",String.valueOf(idUser)));
        List<ToDo> toDoList = toDoRepository.findAllByUser(user);
        List<ToDoDto> toDoDtos = toDoList.stream()
                .map(toDo -> new ToDoDto(toDo.getId(),toDo.getTitle(),toDo.getDescription(),toDo.isCompleted()))
                .toList();
        return toDoDtos;
    }

    @Override
    public ToDoDto updateToDo(long id, ToDoDto toDoDto) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("ToDo","Id",String.valueOf(id))
        );
        toDo.setCompleted(toDoDto.isCompleted());
        toDo.setDescription(toDoDto.getDescription());
        toDo.setTitle(toDoDto.getTitle());
        ToDo toDoResult = toDoRepository.save(toDo);
        ToDoDto toDoDtoRes =
                new ToDoDto(toDoResult.getId(),toDoResult.getTitle(),toDoResult.getDescription(),toDoResult.isCompleted());
        return toDoDtoRes;
    }

    @Override
    public void deleteToDo(long id) {
        toDoRepository.deleteById(id);
    }

    @Override
    public ToDoDto completeToDo(Long id) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("ToDo","Id",String.valueOf(id))
        );
        toDo.setCompleted(true);
        ToDo toDoResult = toDoRepository.save(toDo);
        ToDoDto toDoDtoRes =
                new ToDoDto(toDoResult.getId(),toDoResult.getTitle(),toDoResult.getDescription(),toDoResult.isCompleted());
        return toDoDtoRes;
    }

    @Override
    public ToDoDto incompleteToDo(Long id) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("ToDo","Id",String.valueOf(id))
        );
        toDo.setCompleted(false);
        ToDo toDoResult = toDoRepository.save(toDo);
        ToDoDto toDoDtoRes =
                new ToDoDto(toDoResult.getId(),toDoResult.getTitle(),toDoResult.getDescription(),toDoResult.isCompleted());
        return toDoDtoRes;
    }
}
