package com.oxyhotel.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val primary = Color(0xFF4ADE80)

val secondary = Color(0xFFFFFFFF)

val tertiary = Color(0xBFFFFFFF)

val background = Color(0xFF262626)

val onPrimary = Color(0xFF53A777)

val onBackground = Color(0xFF1A1818)

val paintedStarColor = Color(0xFFFFDA2A)

val linearGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        return LinearGradientShader(
            from = Offset((size.width * 0.5).toLong().toFloat(), 0f),
            to = Offset((size.width * 0.5).toFloat(), size.height),
            colors = listOf(
                Color(0xFF37DE7E),
                Color(0xFF1EBB60)
            ),
            colorStops = listOf(0.0f, 1.0f)
        )
    }

}

val error = Color(0xFFF75555)