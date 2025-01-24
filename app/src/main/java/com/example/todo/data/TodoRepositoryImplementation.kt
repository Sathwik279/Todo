package com.example.todo.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImplementation(
    private val dao: TodoDao
): TodoRepository {

    override suspend fun insertTodo(todo: Todo) {
        return dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        return dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override fun getTodos(): Flow<List<Todo>> {
        return dao.getTodos()
    }

}