package com.valipu.composetodoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(@PrimaryKey(autoGenerate = true) val id: Int? = null, val title: String, val description: String?, val done: Boolean = false)
