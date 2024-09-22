package com.example.seontestapplication

import android.widget.Toast
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.seonsdk.main.PhotoSDK
import java.io.File

class PhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        val fragmentType = intent.getStringExtra("fragment_type") ?: "camera"

        if (savedInstanceState == null) {
            when (fragmentType) {
                "camera" -> showCameraFragment()
                "gallery" -> showGalleryFragment()
            }
        }
    }

    // Method to show CameraFragment
    private fun showCameraFragment() {
        val cameraFragment = PhotoSDK.takePhoto({ error ->
            // Handle errors from photo capture
            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        }, { photoFile ->
            // Handle success and return the file path to MainActivity
            Log.d("Photo Activity", photoFile.absolutePath)
            finishWithResult(photoFile)

        })

        replaceFragment(cameraFragment)
    }

    private fun showGalleryFragment() {
        PhotoSDK.accessPhotos(
            this,
            onAuthenticated = { fragment ->
                // Replace with the GalleryFragment once authentication is successful
                replaceFragment(fragment)
            },
            onError = { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.photo_fragment_container, fragment)
            .commit()
    }

    private fun finishWithResult(photoFile: File) {
        val intent = Intent().apply {
            putExtra("captured_photo_path", photoFile.absolutePath)
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}