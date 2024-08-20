package com.example.firebaseandroidproj.presentation.views

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.firebaseandroidproj.GoogleAuthUiClient
import com.example.firebaseandroidproj.R
import com.example.firebaseandroidproj.presentation.components.CustomAuthMethodContainer
import com.example.firebaseandroidproj.presentation.components.CustomButton
import com.example.firebaseandroidproj.presentation.components.CustomTextField
import com.example.firebaseandroidproj.presentation.components.RichText
import com.example.firebaseandroidproj.presentation.view_model.FirebaseViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch


class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val lifecycleOwner = LocalLifecycleOwner.current
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val viewModel = remember { FirebaseViewModel() }
        val googleAuthUiClient = remember {
            GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context),
            )
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if (result.resultCode == RESULT_OK) {
                    lifecycleOwner.lifecycleScope.launch {
                        val signInResult = googleAuthUiClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        if (signInResult.errorMessage?.isEmpty() == true) {
                            val sharedPreferences =context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                            val editor=sharedPreferences.edit()
                            editor.putInt("loginState",1)
                            editor.apply()
                            navigator.push(HomeScreen())
                        } else {
                            Toast.makeText(context, signInResult.errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                )
                CustomTextField(stringResource(id = R.string.email), Icons.Default.Person, email)
                CustomTextField(stringResource(id = R.string.password), Icons.Default.Info, password)
                CustomButton(
                    text = stringResource(id = R.string.login),
                    onClick = {
                        viewModel.login(
                            email.value,
                            password.value,
                            onLoginSuccess = { navigator.push(HomeScreen()) },
                            onLoginError = {
                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                            },
                            context
                        )
                    }
                )
                RichText(stringResource(id = R.string.dont), stringResource(id = R.string.signup)) { offset ->
                    if (offset in 25..38) {
                        navigator.push(RegisterScreen())
                    }
                }
                CustomAuthMethodContainer(
                    text = stringResource(id = R.string.login_with_facebook),
                    icon = R.drawable.facebook,
                    onClick = {}
                )
                CustomAuthMethodContainer(
                    text = stringResource(id = R.string.login_with_google),
                    icon = R.drawable.google,
                    onClick = {
                        lifecycleOwner.lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signInWithGoogle()
                            signInIntentSender?.let {
                                launcher.launch(
                                    IntentSenderRequest.Builder(it).build()
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}