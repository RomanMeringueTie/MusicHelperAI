package com.example.maps.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import java.text.BreakIterator
import java.text.StringCharacterIterator

@Composable
fun TypeWritingText(text: String, style: TextStyle, color: Color, textAlign: TextAlign, delay: Long) {
    val breakIterator =
        remember(text) { BreakIterator.getCharacterInstance() }
    var substringText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(text) {
        breakIterator.text = StringCharacterIterator(text)
        var nextIndex = breakIterator.next()
        while (nextIndex != BreakIterator.DONE) {
            substringText = text.subSequence(0, nextIndex).toString()
            nextIndex = breakIterator.next()
            delay(delay)
        }
    }

    Text(
        text = substringText,
        style = style,
        color = color,
        textAlign = textAlign
    )
}