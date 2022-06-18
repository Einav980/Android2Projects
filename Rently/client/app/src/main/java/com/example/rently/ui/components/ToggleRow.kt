package com.example.rently.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rently.ui.theme.RoundedSquareShape

@Composable
fun ToggleRow(text: String, isChecked: Boolean = false, onCheck: () -> Unit, style: TextStyle = MaterialTheme.typography.body1) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedSquareShape.large)
            .background(MaterialTheme.colors.background)
            .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text, style = style)
        Switch(checked = isChecked, onCheckedChange = { onCheck() })
    }
}

@Preview
@Composable
fun ToggleRowPreview() {
    ToggleRow(text = "Is parking included?", onCheck = {})
}