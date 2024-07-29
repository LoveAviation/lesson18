package com.example.lesson18.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lesson18.domain.ImageDao
import com.example.lesson18.other_classes.image

@Database(entities = [image::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun imageDao(): ImageDao
}