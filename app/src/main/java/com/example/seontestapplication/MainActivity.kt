package com.example.seontestapplication

import android.view.View
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var photoImageView: ImageView

    // Register the activity result launcher for PhotoActivity
    private val capturePhotoLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val photoPath = result.data?.getStringExtra("captured_photo_path")
                photoPath?.let {
                    showCapturedPhoto(it)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoImageView = findViewById(R.id.photo_image_view)

        val takePhotoButton: Button = findViewById(R.id.button_take_photo)
        takePhotoButton.setOnClickListener {
            // Launch PhotoActivity to show CameraFragment
            val intent = Intent(this, PhotoActivity::class.java).apply {
                putExtra("fragment_type", "camera")
            }
            capturePhotoLauncher.launch(intent)
        }

        val accessPhotosButton: Button = findViewById(R.id.button_access_photos)
        accessPhotosButton.setOnClickListener {
            // Launch PhotoActivity to show GalleryFragment
            val intent = Intent(this, PhotoActivity::class.java).apply {
                putExtra("fragment_type", "gallery")
            }
            startActivity(intent)
        }
    }

    // Display the captured photo using Glide
    private fun showCapturedPhoto(photoPath: String) {
        photoImageView.visibility = View.VISIBLE
        Glide.with(this)
            .load(photoPath)
            .into(photoImageView)
    }
}
