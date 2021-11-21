package com.example.todo.ViewModel

import android.content.ClipData
import androidx.lifecycle.*
import com.example.todo.data.Todo
import com.example.todo.data.TodoDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TodoViewModel(private val todoDao: TodoDao): ViewModel() {

    val allItems: LiveData<List<Todo>> = todoDao.getAllData().asLiveData()

    fun isEntryValid(
        Title: String,
        Notes: String): Boolean {
        if (Title.isBlank() || Notes.isBlank()) return false
        return true
    }

    fun addNewTodoData(
        Title: String,
        Notes: String ) {
        val newData = getNewData(Title, Notes)
        InsertNewTodo(newData)
    }

    private fun getNewData(
        title: String,
        notes: String): Todo  {
        return Todo(
            Title = title,
            Notes = notes )
    }

    private fun InsertNewTodo(todo: Todo) {
        viewModelScope.launch {
            todoDao.insert(todo)
        }
    }

    fun retriveData(id: Int): LiveData<Todo>{
        return todoDao.getData(id).asLiveData()
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