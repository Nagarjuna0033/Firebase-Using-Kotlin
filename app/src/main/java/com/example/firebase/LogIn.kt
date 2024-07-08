package com.example.firebase

import android.content.Context
import android.content.Intent
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

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.PasswordVisualTransformation



@Composable
fun LogIn(loginViewModel: LoginViewModel, context: Context) {

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
                email = loginViewModel.email,
                password = loginViewModel.password,
                emailError = loginViewModel.emailError,
                passwordError = loginViewModel.passwordError,
                onEmailChange = {loginViewModel.email = it ; loginViewModel.emailError = !loginViewModel.isValidEmail(loginViewModel.email)},
                onPasswordChange = {loginViewModel.password = it ;loginViewModel.passwordError = it.length < 6}
            )
            Buttons(context = context,
                loginViewModel = loginViewModel,
                )
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 35.dp, end = 19.dp),Alignment.BottomEnd) {
        Tips()
    }


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
private fun Buttons(loginViewModel: LoginViewModel, context : Context) {
    Button(onClick = { if(!loginViewModel.emailError && !loginViewModel.passwordError) loginViewModel.logInUser(context) else Toast.makeText(
        context,
        "Enter required Fields",
        Toast.LENGTH_SHORT
    ).show()}, modifier = Modifier
        .width(250.dp)
        .padding(15.dp), shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(242,242,242))) {
        Text(text = "LOG IN", fontSize = 19.sp, modifier = Modifier.padding(5.dp), color = Color.Black)
    }
}
@Preview
@Composable
private fun Tips() {
    val context = LocalContext.current
    Row(horizontalArrangement = Arrangement.End ,modifier = Modifier
        .fillMaxWidth()
        .padding(end = 15.dp), verticalAlignment =  Alignment.CenterVertically) {
        Text(text = "Don't have an Account? ", fontSize = 17.sp, fontWeight = FontWeight.Bold)
        TextButton(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Sign in" ,fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Blue)
        }
    }

}


