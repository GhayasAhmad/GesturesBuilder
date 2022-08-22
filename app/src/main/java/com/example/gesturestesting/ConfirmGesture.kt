package com.example.gesturestesting

import android.content.Context
import android.content.ContextWrapper
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gesturestesting.databinding.ActivityConfirmGestureBinding
import java.io.File


class ConfirmGesture : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmGestureBinding
    private lateinit var contextWrapper: ContextWrapper
    private lateinit var directory: File
    private lateinit var gesturePath: File
    private lateinit var store: GestureLibrary


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmGestureBinding.inflate(layoutInflater)
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

        store = GestureLibraries.fromFile(gesturePath)
        store.load()
    }


    private fun viewListeners() {
        binding.gesture.addOnGesturePerformedListener { _, gesture ->
            val predictions = store.recognize(gesture)
            for (prediction in predictions) {
                if (prediction.score > 1.0) {
                    Toast.makeText(this, "Gesture Matched", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Gesture Not Matched", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}