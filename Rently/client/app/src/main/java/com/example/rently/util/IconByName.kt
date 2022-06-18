package com.example.rently.util

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconByName(name: String) {
    val icon = getIconResourceByName(name = name)
    if (icon != null) {
        Icon(icon, "$name icon")
    }
    else{
        Icon(imageVector = Icons.Filled.Help, contentDescription = "Help")
    }
}

@Composable
fun getIconResourceByName(name: String): ImageVector?{
    val icon: ImageVector? = remember(name) {
        try {
            val cl = Class.forName("androidx.compose.material.icons.filled.${name}Kt")
            val method = cl.declaredMethods.first()
            method.invoke(null, Icons.Filled) as ImageVector
        } catch (_: Throwable) {
            null
        }
    }
    return icon
}