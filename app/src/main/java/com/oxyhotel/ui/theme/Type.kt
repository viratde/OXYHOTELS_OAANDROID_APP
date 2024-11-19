package com.oxyhotel.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.oxyhotel.R

val robotoMono = FontFamily(
    Font(R.font.robotomono_medium, FontWeight.Medium),
    Font(R.font.robotomono_semibold, FontWeight.SemiBold),
)

val mplus = FontFamily(
    Font(R.font.mplus_extrabold, FontWeight.ExtraBold),
)


val Typography = Typography(

    headlineLarge = TextStyle(
        fontFamily = mplus,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 48.sp,
        color = secondary
    ),
    headlineMedium = TextStyle(
        fontFamily = mplus,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 37.sp,
        color = secondary
    ),
    headlineSmall = TextStyle(
        fontFamily = mplus,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 23.sp,
        color = secondary
    ),
    bodyMedium = TextStyle(
        fontFamily = mplus,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        color = secondary
    ),

    bodySmall = TextStyle(
        color = secondary
    ),
    
    titleMedium = TextStyle(
        fontFamily = robotoMono,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        color = secondary
    ),
    titleSmall = TextStyle(
        fontFamily = robotoMono,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        color = secondary
    ),
    titleLarge = TextStyle(
        color = secondary
    ),
    bodyLarge = TextStyle(
        color = secondary
    ),
    labelSmall = TextStyle(
        color = secondary
    ),
    labelMedium = TextStyle(
        color = secondary
    ),
    labelLarge = TextStyle(
        color = secondary
    )

)