package com.example.lesson18.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lesson18.other_classes.image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM images")
    fun getAll(): Flow<List<image>>

    @Insert
    suspend fun insert(images: image)
}