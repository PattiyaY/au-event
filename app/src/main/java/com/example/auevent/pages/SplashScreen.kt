package com.example.auevent.pages

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val signInLauncher = rememberLauncherForActivityResult(
        contract = StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task, activity)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.events_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
        Text(text = "AU Event", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { signIn(activity, signInLauncher) },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purple_strong)
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_icon),
                contentDescription = "Google Icon",
                modifier = Modifier.size(24.dp)
            )
            Text(text = "Log in with GOOGLE")
        }
    }
}

private fun signIn(activity: ComponentActivity, signInLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    val signInIntent = googleSignInClient.signInIntent
    signInLauncher.launch(signInIntent)
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

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    // Provide a dummy activity for preview purposes
    SplashScreen(activity = DummyActivity())
}

class DummyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}