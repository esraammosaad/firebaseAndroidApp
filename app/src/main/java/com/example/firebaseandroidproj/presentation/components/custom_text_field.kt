package com.example.firebaseandroidproj.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextField(textFieldLabel:String,textFieldIcon: ImageVector,text:MutableState<String>) {


    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        OutlinedTextField(

            value = text.value,
            onValueChange = { text.value = it },
            leadingIcon = {
                Icon(
                    imageVector = textFieldIcon,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            label = { Text(textFieldLabel) },
            textStyle = TextStyle(
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        )
    }
}