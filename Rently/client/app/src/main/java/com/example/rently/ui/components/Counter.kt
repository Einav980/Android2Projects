package com.example.rently.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rently.ui.theme.RentlyTheme

@Composable
fun Counter(
    value: Int,
    onValueChanged: (Int) -> Unit,
    backgroundColor: Color = MaterialTheme.colors.primary,
    tint: Color = Color.White,
    icon: ImageVector? = null,
    fontStyle: TextStyle = MaterialTheme.typography.h4,
) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value.toString(), style = fontStyle, fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            IconButton(modifier = Modifier.clip(CircleShape).background(backgroundColor).size(32.dp), onClick = {onValueChanged(value + 1)}) {
                Icon(modifier = Modifier.padding(3.dp), imageVector = Icons.Filled.Add, contentDescription = "Add", tint = tint)
            }
            if(icon != null){
                Box(modifier = Modifier.padding(5.dp)){
                    Icon(modifier = Modifier.padding(8.dp).scale(1.5f), imageVector = icon, contentDescription = null)
                }
            }
            IconButton(modifier = Modifier.clip(CircleShape).background(backgroundColor).size(32.dp), onClick = {onValueChanged(value - 1)}) {
                Icon(modifier = Modifier.padding(3.dp), imageVector = Icons.Filled.Remove, contentDescription = "Remove", tint = tint)
            }
        }
    }
}

@Preview
@Composable
fun CounterPreview() {
    RentlyTheme() {
        Counter(value = 3, onValueChanged = {}, icon = Icons.Filled.Bed)
    }
}