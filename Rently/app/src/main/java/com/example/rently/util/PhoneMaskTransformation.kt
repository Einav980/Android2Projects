package com.example.rently.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneMaskTransformation: VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return maskFilter(text)
    }
}

fun maskFilter(text: AnnotatedString): TransformedText {

    // NNN-NNNNNNN
    // 050-2505005
    val trimmed = if (text.text.length >= 11) text.text.substring(0..9) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i==2) out += "-"
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 10) return offset +1
            return 11

        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <=3) return offset
            if (offset <=10) return offset -1
            return 10
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}