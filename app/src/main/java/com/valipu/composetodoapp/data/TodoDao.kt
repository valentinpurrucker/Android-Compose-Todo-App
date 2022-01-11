package com.valipu.composetodoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Data access object that defines the methods to work with on the room database.

@Dao
interface TodoDao {

    // Observe database and update if any changes happened.
    @Query("SELECT * FROM todo")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE id=:id")
    suspend fun getTodoById(id: Int): Todo?

    @Update
    suspend fun update(todo: Todo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)
}