package com.springboot.todomanagement.controller;

import com.springboot.todomanagement.dto.ToDoDto;
import com.springboot.todomanagement.security.CustomUser;
import com.springboot.todomanagement.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Controller
@RequestMapping("/api/todo")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @PostMapping
    public ResponseEntity<ToDoDto> createToDo(@RequestBody ToDoDto toDoDto, @AuthenticationPrincipal CustomUser principal){
        ToDoDto toDoDtoRes = toDoService.createToDo(toDoDto,principal.getUser().getId());
        return new ResponseEntity<>(toDoDtoRes, HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<ToDoDto> getToDoById(@PathVariable Long id){
        ToDoDto toDoDto = toDoService.getToDoById(id);
        return new ResponseEntity<>(toDoDto,HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<ToDoDto>> getAllToDo(@AuthenticationPrincipal CustomUser principal){
        List<ToDoDto> toDoDtos = toDoService.getAllToDo(principal.getUser().getId());
        return new ResponseEntity<>(toDoDtos,HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity<ToDoDto> updateToDo(@PathVariable Long id,@RequestBody ToDoDto toDoDto){
        ToDoDto toDoDtoRes = toDoService.updateToDo(id,toDoDto);
        return new ResponseEntity<>(toDoDtoRes,HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteToDo(@PathVariable Long id){
        toDoService.deleteToDo(id);
        return new ResponseEntity<>("Delete successfull",HttpStatus.OK);
    }
    @PatchMapping("{id}/complete")
    public ResponseEntity<ToDoDto> completeToDo(@PathVariable Long id){
        ToDoDto toDoDto = toDoService.completeToDo(id);
        return new ResponseEntity<>(toDoDto,HttpStatus.OK);
    }

    @PatchMapping("{id}/incomplete")
    public ResponseEntity<ToDoDto> inCompleteToDo(@PathVariable Long id){
        ToDoDto toDoDto = toDoService.incompleteToDo(id);
        return new ResponseEntity<>(toDoDto,HttpStatus.OK);
    }

}
