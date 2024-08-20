package com.example.firebaseandroidproj.presentation.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class FirebaseViewModel : ViewModel(){

 val firebaseAuth:FirebaseAuth= FirebaseAuth.getInstance()


    fun login(
        email: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginError: (String) -> Unit,
        context: Context
    ) {

        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onLoginSuccess()
                        val sharedPreferences =context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        val editor=sharedPreferences.edit()
                        editor.putInt("loginState",1)
                        editor.apply()
                    }
                }
                .addOnFailureListener { exception ->
                    onLoginError(exception.message ?: "Unknown error")
                }
        }
        catch (exception: Exception) {
            onLoginError(exception.message ?: "An unexpected error occurred")
        }

    }

    fun register(
        email: String,
        password: String,
        onLoginSuccess: () -> Unit,
        onLoginError: (String) -> Unit,
        context: Context
    ) {

        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onLoginSuccess()
                        val sharedPreferences =context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        val editor=sharedPreferences.edit()
                        editor.putInt("loginState",1)
                        editor.apply()
                    }
                }
                .addOnFailureListener { exception ->
                    onLoginError(exception.message ?: "Unknown error")
                }
        }
        catch (exception: Exception) {
            onLoginError(exception.message ?: "An unexpected error occurred")
        }

    }







}