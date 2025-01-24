package com.example.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "todo")
data class Todo(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int? = null
)
