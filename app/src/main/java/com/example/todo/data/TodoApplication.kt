package com.example.todo.data

import android.app.Application

class TodoApplication: Application() {
    val database: TodoDatabase by lazy { TodoDatabase.getDatabase(this) }
}