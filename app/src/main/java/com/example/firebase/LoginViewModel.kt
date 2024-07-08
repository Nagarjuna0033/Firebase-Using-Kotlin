package com.example.firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailError by mutableStateOf(true)
    var passwordError by mutableStateOf(true)

    private var auth : FirebaseAuth = Firebase.auth

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun logInUser(context : Context) {

        if(!emailError && !passwordError){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("msg", task.result.toString())
                    val intent = Intent(context, Home::class.java)
                    context.startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    try {
                        Log.d("msg", task.exception.toString())
                        Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show()
                        throw task.exception!!
                    } catch (e: FirebaseAuthUserCollisionException) {
                        Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.d("msg", e.toString())
                    }

                }
            }
        }else{
            Toast.makeText(context, "Enter Required Fields", Toast.LENGTH_SHORT).show()
        }
    }



}