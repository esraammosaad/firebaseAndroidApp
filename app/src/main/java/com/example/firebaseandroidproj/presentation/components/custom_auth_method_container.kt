package com.example.firebaseandroidproj.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomAuthMethodContainer(text:String,icon:Int,onClick:()->Unit){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
        .fillMaxWidth()
        .padding(
            horizontal = 22.dp,
            vertical = 4.dp
        ),
        colors = ButtonDefaults.buttonColors(
            Color.Transparent


        ),            border = BorderStroke(1.dp, Color.Gray),

    ) {
       Row (
           horizontalArrangement = Arrangement.Center,
           verticalAlignment = Alignment.CenterVertically
       ){
           Image(
               painter = painterResource(id =icon ),
               contentDescription = null,
               Modifier
                   .width(30.dp)
                   .height(25.dp))
           Spacer(
               modifier = Modifier.width(3.dp)
           )
           Text(
               text = text,
               fontSize = 18.sp,
               color = Color(0xff9364cf),
               fontWeight = FontWeight.Bold
           )
       }


    }
}