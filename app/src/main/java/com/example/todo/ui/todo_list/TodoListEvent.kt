package com.example.todo.ui.todo_list

import com.example.todo.TodoApp
import com.example.todo.data.Todo

sealed class TodoListEvent {
    // These are the possible interaction user can do with the interface of the application
    // what kind of user interaction can we possibly have on the todolist screen
    //   1. click on delete btn,toggle the done state,click on todo item,click on add todo , we can click on undo button on a snack bar

    data class OnDeleteTodoClick(val todo: Todo): TodoListEvent()

    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvent()

    object OnUndoDeleteClick: TodoListEvent()

    data class OnTodoClick(val todo: Todo): TodoListEvent()

    object OnAddTodoClick: TodoListEvent()

}