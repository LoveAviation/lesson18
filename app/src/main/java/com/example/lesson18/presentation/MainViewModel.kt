package com.example.lesson18.presentation

import android.media.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lesson18.domain.ImageDao
import com.example.lesson18.other_classes.image
import kotlinx.coroutines.launch

class MainViewModel(private val imageDao: ImageDao): ViewModel() {
    val images = this.imageDao.getAll()

    fun saveImage(name: String, date: String){
        viewModelScope.launch {
            imageDao.insert(
                image(
                    imgSrc = name,
                    date = date
                )
            )
        }
    }
}