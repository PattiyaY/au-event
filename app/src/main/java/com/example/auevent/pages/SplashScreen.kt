package com.example.auevent.pages

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.auevent.MainActivity
import com.example.auevent.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

@Composable
fun SplashScreen(activity: ComponentActivity) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
        Text(text = "AU Event", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { signIn(activity) }) {
            Text(text = "Log in with GOOGLE")
        }
    }
}

private fun signIn(activity: ComponentActivity) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    val signInIntent = googleSignInClient.signInIntent
    activity.startActivityForResult(signInIntent, 100)
}

fun handleSignInResult(task: Task<GoogleSignInAccount>, activity: ComponentActivity) {
    try {
        val account = task.getResult(ApiException::class.java)
        if (account != null) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.finish()
        }
    } catch (e: ApiException) {
        e.printStackTrace()
    }
}
