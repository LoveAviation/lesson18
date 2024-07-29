package com.example.lesson18.other_classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class image(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "img_src")
    val imgSrc: String,
    @ColumnInfo(name = "date")
    val date: String
)
