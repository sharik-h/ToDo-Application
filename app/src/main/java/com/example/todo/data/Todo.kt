package com.example.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_data")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "Title")val Title: String,
    @ColumnInfo(name = "Notes")val Notes: String,
    @ColumnInfo(name = "color")val color: String
    )