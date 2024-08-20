package com.example.firebaseandroidproj

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.Navigator
import com.example.firebaseandroidproj.presentation.views.HomeScreen
import com.example.firebaseandroidproj.presentation.views.LoginScreen
import com.example.firebaseandroidproj.presentation.views.updateLocale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context=LocalContext.current
            val sharedPreferences =context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            updateLocale(context,sharedPreferences.getString("langCode","en")?:"en")
           if(sharedPreferences.getInt("loginState",0)==1) Navigator(screen = HomeScreen()) else Navigator(screen = LoginScreen())
        }
    }
}

