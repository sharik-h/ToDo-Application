package com.example.todo.ViewModel

import androidx.lifecycle.*
import com.example.todo.data.Todo
import com.example.todo.data.TodoDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TodoViewModel(private val todoDao: TodoDao): ViewModel() {

    val allItems: LiveData<List<Todo>> = todoDao.getAllData().asLiveData()

     var color = "red"

    fun setcolor(color: String){
          this.color = color
    }


    fun isEntryValid(
        Title: String,
        Notes: String): Boolean {
        if (Title.isBlank() || Notes.isBlank()) return false
        return true
    }

    fun addNewTodoData(
        Title: String,
        Notes: String
    ) {
               val newData = getNewData(Title, Notes, color)
        InsertNewTodo(newData)
    }

    private fun getNewData(
        title: String,
        notes: String,
        color: String
    ): Todo  {
        return Todo(
            Title = title,
            Notes = notes,
            color = color)
    }

    private fun InsertNewTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.insert(todo)
        }
    }

    fun retriveData(id: Int): LiveData<Todo>{
        return todoDao.getData(id).asLiveData()
    }

    fun updateTodo(
        id: Int,
        Title: String,
        notes: String ) {
        val updatedtodo = getupdatedtodoEntry(id, Title, notes )
        UpdateTodo(updatedtodo)
    }

    private fun UpdateTodo(updatedtodo: Todo) {
        viewModelScope.launch {
            todoDao.update(updatedtodo)
        }
    }

    private fun getupdatedtodoEntry(
        id: Int,
        title: String,
        notes: String,

    ): Todo {
        return Todo(
            id = id,
            Title = title,
            Notes = notes,
            color = "red"
        )
    }

    fun deleteTodoData(todo: Todo) {
        viewModelScope.launch {
            todoDao.delete(todo)
        }
    }

}

class TodoViewModelFactory(private val todoDao: TodoDao): ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelclass: Class<T>): T {
        if (modelclass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(todoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}