package com.example.rently.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Counter(
    value: Int,
    onValueIncrease: (Int) -> Unit,
    onValueDecrease: (Int) -> Unit,
    size: Dp = 15.dp,
    backgroundColor: Color = MaterialTheme.colors.primary,
    tint: Color = Color.White,
    fontSize: TextUnit = 18.sp,
    label: String = "Label",
    labelSize: TextUnit = 28.sp
) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, fontSize = labelSize, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp)).background(backgroundColor), contentAlignment = Alignment.Center){
            Text(modifier = Modifier.padding(5.dp), text = value.toString(), fontSize = fontSize)
        }
        /*Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .size(size),
                onClick = { onValueIncrease(value) },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add, contentDescription = "Add", tint = tint
                    )
                },
            )

            IconButton(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .size(size)
                    .padding(5.dp),
                onClick = { onValueDecrease(value) },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Remove, contentDescription = "Remove", tint = tint
                    )
                },
            )
        }*/
    }
}

@Preview
@Composable
fun CounterPreview() {
    MaterialTheme() {
        Counter(value = 3, onValueIncrease = {}, onValueDecrease = {})
    }
}