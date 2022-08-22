package com.example.gesturestesting

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.gesture.Gesture
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.gesturestesting.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contextWrapper: ContextWrapper
    private lateinit var directory: File
    private lateinit var gesturePath: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        viewListeners()
    }


    private fun init() {
        contextWrapper = ContextWrapper(application)
        directory = contextWrapper.getDir(
            "GestureApp",
            Context.MODE_PRIVATE
        )
        if (!directory.exists()) {
            directory.mkdir()
        }
        gesturePath = File(directory, "Gestures")
    }


    private fun viewListeners() {
        binding.gesture.addOnGestureListener(object : GestureOverlayView.OnGestureListener {
            override fun onGestureStarted(p0: GestureOverlayView?, p1: MotionEvent?) {}
            override fun onGesture(p0: GestureOverlayView?, p1: MotionEvent?) {}
            override fun onGestureEnded(p0: GestureOverlayView?, p1: MotionEvent?) {
                confirmGesture(p0?.gesture)
                p0?.clear(false)
            }

            override fun onGestureCancelled(p0: GestureOverlayView?, p1: MotionEvent?) {}
        })
    }


    private fun confirmGesture(gesture: Gesture?) {
        if (gesture != null) {
            val store: GestureLibrary = GestureLibraries.fromFile(gesturePath)
            store.addGesture("Gesture Password", gesture)
            store.save()
            setResult(RESULT_OK)
            startActivity(
                Intent(
                    this,
                    ConfirmGesture::class.java
                )
            )

        } else {
            Log.e("TAG", "confirmGesture: Gesture is null")
            setResult(RESULT_CANCELED)
        }
    }

}