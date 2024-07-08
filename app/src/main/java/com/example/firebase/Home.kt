package com.example.firebase

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp

class Home : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

                WelcomeScreen()
                Buttons()

        }
    }
}

@Composable
fun WelcomeScreen() {
    Text(text = "Welcome to Home Screen", fontSize = 19.sp, color = Color.Black)
}

@Composable
fun Buttons() {
    val context = LocalContext.current
    Button(onClick = { logOut(context) }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
        Text(text = "Sign out", fontSize = 17.sp, color = Color.Black)
    }
}

fun logOut(context : Context){
    Toast.makeText(context, "Logging Out you", Toast.LENGTH_SHORT).show()
}