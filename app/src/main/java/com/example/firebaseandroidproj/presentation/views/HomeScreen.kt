package com.example.firebaseandroidproj.presentation.views

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.firebaseandroidproj.GoogleAuthUiClient
import com.example.firebaseandroidproj.R
import com.example.firebaseandroidproj.presentation.components.CustomButton
import com.example.firebaseandroidproj.presentation.view_model.FirebaseViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import java.util.Locale

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val lifecycleOwner = LocalLifecycleOwner.current
        val context= LocalContext.current
        val viewModel=FirebaseViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val googleAuthUiClient = remember {
            GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context),
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row ( verticalAlignment = Alignment.CenterVertically){
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(Color.Red), content = {
                        AsyncImage(
                            model = viewModel.firebaseAuth.currentUser?.photoUrl?:"https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png",
                            contentDescription = "profile photo",
                            imageLoader = ImageLoader(context), contentScale = ContentScale.FillBounds, modifier = Modifier.size(70.dp))
                    }
                )
                Spacer(modifier = Modifier.width(7.dp))
                Column {
                    Text(
                        text = (
                                stringResource(id = R.string.hello) +if(viewModel.firebaseAuth.currentUser?.displayName?.isEmpty() == true) ": none" else ": ${viewModel.firebaseAuth.currentUser?.displayName?:"none"}"
                                ),
                        fontSize = 20.sp
                    )
                    Text(
                        text = (
                                stringResource(id = R.string.email) + ": ${viewModel.firebaseAuth.currentUser?.email}"
                                ),
                        fontSize = 20.sp
                    )

                }

            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = (
                        stringResource(id = R.string.chooselang)
                        ),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))


            Row {
                Button(onClick = {
                    updateLocale(context,"ar")
                    (context as? Activity)?.recreate()
                }, shape = RoundedCornerShape(8.dp),) {
                    Text(text = "Arabic", fontSize = 22.sp)


                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    updateLocale(context,"en")
                    (context as? Activity)?.recreate()
                }, shape = RoundedCornerShape(5.dp),) {
                    Text(text = "English", fontSize = 22.sp)


                }



            }

            Box(modifier = Modifier.padding(16.dp)) {
                CustomButton(text = stringResource(id = R.string.logout), onClick = {
                    lifecycleOwner.lifecycleScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(context, context.getString(R.string.logoutSuccess), Toast.LENGTH_LONG).show()
                        val sharedPreferences =context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        val editor=sharedPreferences.edit()
                        editor.putInt("loginState",0)
                        editor.apply()
                        navigator.push(LoginScreen())



                    }


                }, Color.Red)


            }









        }
    }
}

fun updateLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources = context.resources
    val config = resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)
    resources.updateConfiguration(config, resources.displayMetrics)
    val sharedPreferences =context.getSharedPreferences("prefs",Context.MODE_PRIVATE)
    val editor=sharedPreferences.edit()
    editor.putString("langCode",languageCode)
    editor.apply()

}