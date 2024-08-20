package com.example.firebaseandroidproj

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthUiClient (
    val context: Context,
    private val oneTapClient: SignInClient) {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    suspend fun signInWithGoogle(): IntentSender?{
        val result= try {
            oneTapClient.beginSignIn(
                buildSignInRequest()

            ).await()

        }catch (exception:Exception){
            exception.printStackTrace()
            if(exception is CancellationException) throw exception
            null



        }
        return result?.pendingIntent?.intentSender

    }
    suspend fun signInWithIntent(intent: Intent):SignInResult{
        val credential=oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken=credential.googleIdToken
        val googleCredentials= GoogleAuthProvider.getCredential(googleIdToken,null)
        return try {
            val user =firebaseAuth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePhoto = photoUrl?.toString()

                    )
                }, errorMessage = ""
            )
        }catch (exception:Exception){
            exception.printStackTrace()
            if(exception is CancellationException) throw exception
            SignInResult(
                data = null,
                errorMessage = exception.message
            )



        }


    }
    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            firebaseAuth.signOut()

        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException)throw e

        }
    }


    private  fun buildSignInRequest(): BeginSignInRequest {

        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("25235536116-aatu0vnsbeessjo52e3mv7rch562i0f0.apps.googleusercontent.com")
                    .setFilterByAuthorizedAccounts(false)
                    .build()).setAutoSelectEnabled(true)
            .build()
    }
}