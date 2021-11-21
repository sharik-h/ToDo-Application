package com.example.todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Query("SELECT * FROM todo_data ORDER BY id ASC ")
    fun getAllData(): Flow<List<Todo>>

    @Query("SELECT * FROM todo_data WHERE id = :id")
    fun getData(id: Int): Flow<Todo>

}