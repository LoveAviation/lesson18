package com.example.lesson18.presentation

import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lesson18.data.App
import com.example.lesson18.databinding.ActivityTakePhotoBinding
import com.example.lesson18.domain.ImageDao
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"

class TakePhotoActivity : AppCompatActivity() {
    private var imageCapture : ImageCapture? = null
    private lateinit var executor: Executor
    private lateinit var binding: ActivityTakePhotoBinding

    private val mainViewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val imageDao: ImageDao = (application as App).db.imageDao()
                return MainViewModel(imageDao) as T
            }
        }
    }

    private val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
        .format(System.currentTimeMillis())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTakePhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executor = ContextCompat.getMainExecutor(this)

        startCamera()
        binding.button.setOnClickListener {
            takePhoto()
            finish()
        }

        binding.undoButton.setOnClickListener {
            finish()
        }
    }


    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
        }, executor)
    }

    private fun takePhoto(){
        val imageCapture = imageCapture ?: return
        var fileName : String? = null

        val contentValues = ContentValues().apply{
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            fileName = name
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    mainViewModel.saveImage(outputFileResults.savedUri.toString(), fileName.toString())
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(this@TakePhotoActivity, "Error", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }

}