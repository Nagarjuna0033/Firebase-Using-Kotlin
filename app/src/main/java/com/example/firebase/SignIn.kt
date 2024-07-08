package com.example.firebase

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun SignIn(context: Context) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf(true)
    }
    var passwordError by remember {
        mutableStateOf(true)
    }
    val auth = Firebase.auth
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize(),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextFields(
                email = email,
                password = password,
                emailError = emailError,
                passwordError = passwordError,
                onEmailChange = {email = it ; emailError = !isValidEmail(email)},
                onPasswordChange = {password = it ;passwordError = it.length < 6}
            )
            Buttons(context,
                auth,
                email,
                password,
                emailError,
                passwordError)
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 35.dp, end = 19.dp),Alignment.BottomEnd) {
        Tips(context)
    }


}

private fun isValidEmail(email : String): Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}



@Composable
private fun TextFields(email: String, password: String, emailError : Boolean, passwordError: Boolean, onEmailChange : (String)->Unit, onPasswordChange : (String)->Unit) {


    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp), verticalArrangement = Arrangement.spacedBy(15.dp)){
        TextField(textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),

            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(242,242,242),
                focusedContainerColor =  Color(242,242,242) ,
                focusedIndicatorColor = if(emailError) Color.Transparent else Color.Red, unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth(),
            value = email, label = { Text(text = "Email", fontSize = 19.sp, color = Color.Gray)},
            onValueChange = onEmailChange)
        TextField(textStyle = TextStyle(fontSize = 20.sp,  color = Color.Black),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(242,242,242),
                focusedContainerColor = Color(242,242,242),
                focusedIndicatorColor = if(passwordError) Color.Transparent else Color.Red,
                unfocusedIndicatorColor = Color.Transparent ),
            modifier = Modifier.fillMaxWidth(),
            value = password,
            label = { Text(text = "password", fontSize = 19.sp, color = Color.Gray)},
            onValueChange = onPasswordChange)
    }
}

@Composable
private fun Buttons(context : Context, auth : FirebaseAuth, email : String, password : String,emailError: Boolean,passwordError: Boolean) {
    Button(onClick = { if(!emailError && !passwordError) signUpUser(context,auth,email, password) else Toast.makeText(
        context,
        "Enter required Fields",
        Toast.LENGTH_SHORT
    ).show()}, modifier = Modifier
        .width(250.dp)
        .padding(15.dp), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(242,242,242))) {
        Text(text = "SIGN UP", fontSize = 19.sp, modifier = Modifier.padding(5.dp), color = Color.Black)
    }
}
@Composable
private fun Tips(context: Context) {

        Row(horizontalArrangement = Arrangement.End ,modifier = Modifier
            .fillMaxWidth()
            .padding(end = 15.dp), verticalAlignment =  Alignment.CenterVertically) {
            Text(text = "Already have an Account? ", fontSize = 17.sp, fontWeight = FontWeight.Bold)
            TextButton(onClick = {
                val intent = Intent(context, LogInActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(text = "Log in" ,fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
            }
        }

}

private fun signUpUser(context : Context, auth:FirebaseAuth, email : String, password: String){

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val intent = Intent(context, LogInActivity::class.java)
                Log.d("msg",task.result.toString())

                context.startActivity(intent)
            } else {
                // If sign in fails, display a message to the user.
                try {
                    Log.d("msg",task.exception.toString())
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show()
                    throw task.exception!!
                }catch (e : FirebaseAuthUserCollisionException){
                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
                }catch (e : Exception){
                    Log.d("msg",e.toString())
                }

            }
        }
}