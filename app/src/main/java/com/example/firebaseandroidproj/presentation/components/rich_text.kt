package com.example.firebaseandroidproj.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseandroidproj.R

@Composable
fun RichText(textOne:String, textTwo:String, onClick:(Int)->Unit) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 20.sp)){
            append(textOne + " "+ stringResource(id = R.string.have_account))
        }

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xff9364cf), fontSize = 20.sp)) {
            append(" $textTwo")
        }
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        ClickableText(
            text = annotatedString,
            onClick = onClick
        )
    }
}