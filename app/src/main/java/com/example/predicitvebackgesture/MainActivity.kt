package com.example.predicitvebackgesture

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import android.window.OnBackInvokedCallback
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.predicitvebackgesture.ui.theme.PredicitveBackGestureTheme
import java.util.*
import kotlin.system.exitProcess


class MainActivity : ComponentActivity() {
    private var isExit = false
    private var hasTask = false

    private var timerExit: Timer = Timer()
    lateinit var onBackInvokedCallback: OnBackInvokedCallback

    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PredicitveBackGestureTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Yellow)
                        .clickable {
                            startActivity(Intent(this, SecondActivity::class.java))
                        }
                ) {
                    Greeting("First")
                }
            }
        }

        onBackInvokedCallback = OnBackInvokedCallback {
            if (!isExit) {
                isExit = true
                Toast.makeText(
                    this, "Press again to exit", Toast.LENGTH_SHORT
                ).show()
                if (!hasTask) {
                    timerExit.schedule(object : TimerTask() {
                        override fun run() {
                            isExit = false
                            hasTask = true
                            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                                OnBackInvokedDispatcher.PRIORITY_DEFAULT, onBackInvokedCallback
                            )
                        }
                    }, 2000)
                } else {
                    onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback)
                }
            }
        }
        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT, onBackInvokedCallback
            )
        }
    }
}

