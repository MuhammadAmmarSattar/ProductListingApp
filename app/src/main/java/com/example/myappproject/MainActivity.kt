package com.example.myappproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappproject.utils.AuthUtils.hideStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar(window)
        setTheme(R.style.Theme_MyAppProject)
        setContentView(R.layout.activity_main)

    }
}