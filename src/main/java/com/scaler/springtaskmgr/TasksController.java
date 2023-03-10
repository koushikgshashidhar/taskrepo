package com.scaler.springtaskmgr;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TasksController {
    private final List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(0);

    public TasksController() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "Task 1", "Description 1", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 2", "Description 2", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 3", "Description 3", new Date()));
    }

    /*
    ASSIGNMENT: Finish all the TODOS below
    You do not need to persist the tasks in a database. Just keep them in memory.
     */

    /**
     * Show all existing tasks
     * GET /tasks
     * @return List of tasks
     */
    @GetMapping("/tasks")
    List<Task> getTasks() {
        return taskList;
    }

    /**
     * Create a new task
     * POST /tasks
     * Body:
     *  <pre>
     *      {
     *          "title": "Task 4",
     *          "description": "Description 4",
     *          "dueDate": "2021-01-01"
     *      }
     *  </pre>
     * @param task Task object sent by client
     * @return Task object created
     */
    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task) {
        var newTask = new Task(taskId.incrementAndGet(), task.getTitle(), task.getDescription(), task.getDueDate());
        taskList.add(newTask);
        return newTask;
    }

    /**
     * Get a task by id
     * @param id
     * @return Task object
     */
    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable("id") Integer id) {
        // TODO: Implement this method
        // TODO: BONUS: Return 404 if task not found
        Task task= taskList.stream().filter(t->t.id==id).findAny().orElse(null);
        if(task!=null)
        return task;
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "task with "+id+" not found"
            );
       // return null;
    }

    /**
     * Delete a task by given id
     * @param id Task id to delete
     * @return the deleted task
     */
    @DeleteMapping("/tasks/{id}")
    Task deleteTask(@PathVariable("id") Integer id) {
        // TODO: Implement this method
        // TODO: BONUS: Return 404 if task not found
        Task task= taskList.stream().filter(t->t.id==id).findAny().orElse(null);
        taskList.remove(task);
        if(task!=null)
        return task;
        else
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "task with "+id+" not found"
        );
    }

    /**
     * Update a task by given id
     * @param id Task id to update
     * @param task Task object sent by client
     * @return the updated task
     */
    @PatchMapping("/tasks/{id}")
    Task updateTask(@PathVariable("id") Integer id, @RequestBody Task task) {
        // TODO: BONUS: Update the task with given id
        // Request body might have only title, description or dueDate (not necessarily all fields)
        Task uptask= taskList.stream().filter(t->t.id==id).findAny().orElse(null);
        if(task.getDescription()!=null)
        {
            uptask.setDescription(task.getDescription());
        }
        if(task.getDueDate()!=null)
        {
            uptask.setDueDate(task.getDueDate());
        }
        if(task.getTitle()!=null)
        {
            uptask.setTitle(task.getTitle());
        }
        taskList.set(id,uptask);
        return uptask;
    }
}
