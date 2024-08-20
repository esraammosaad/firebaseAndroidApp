package com.example.firebaseandroidproj.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(text:String, onClick :()->Unit,color:Color?=null){
    Button(onClick = onClick, shape = RoundedCornerShape(5.dp), modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 20.dp), colors =ButtonDefaults.buttonColors(

            containerColor = color?:Color. Unspecified
        ) ) {
        Text(text = text, fontSize = 22.sp)


    }


}

