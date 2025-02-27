package io.github.josebatista.marketplace.presentation.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Typography settings used throughout the Marketplace application.
 *
 * This typography definition sets the style for various text elements. Currently, it defines
 * the [bodyLarge] text style using the default font family with normal weight, a font size of 16 sp,
 * a line height of 24 sp, and a letter spacing of 0.5 sp.
 *
 * Additional text styles can be added to this definition as needed.
 */
internal val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)
