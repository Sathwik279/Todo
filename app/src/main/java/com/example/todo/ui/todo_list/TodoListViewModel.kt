package com.example.todo.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.Todo
import com.example.todo.data.TodoRepository
import com.example.todo.util.Routes
import com.example.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor( //dagger hilt will know how to send the instance of the repository from the object we created in the di
    private val repository: TodoRepository
): ViewModel(){

    val todos = repository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deleteTodo: Todo? = null // this line is used to cache a recently deleted todo if we want to undo that

    // on  view model for each screen

    fun onEvent(event: TodoListEvent){

        // ToodoListEvent is the event occured when the user interacts with the ui

        when(event){
            is TodoListEvent.OnTodoClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO+"?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnAddTodoClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnUndoDeleteClick ->{
                // this .let signature is used to safely access the name of the variable with smaller reference name multiple times or with local scope right
                deleteTodo?.let{ todo->
                    viewModelScope.launch {
                        repository.insertTodo(todo)
                    }
                }
            }
            is TodoListEvent.OnDeleteTodoClick ->{
                viewModelScope.launch {
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                }
            }
            // as we have kept he onConflict stragey to replace if we update an existing todo it will be replace with new values so called update
            is TodoListEvent.OnDoneChange ->{
                viewModelScope.launch{
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch{
            _uiEvent.send(event)
        }
    }

}