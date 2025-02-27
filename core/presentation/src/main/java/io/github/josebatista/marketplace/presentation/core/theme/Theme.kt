package io.github.josebatista.marketplace.presentation.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Dark color scheme used in the application.
 *
 * Uses light purple ([Purple80]) as primary, light purple-grey ([PurpleGrey80]) as secondary,
 * and light pink ([Pink80]) as tertiary colors.
 */
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

/**
 * Light color scheme used in the application.
 *
 * Uses dark purple ([Purple40]) as primary, dark purple-grey ([PurpleGrey40]) as secondary,
 * and dark pink ([Pink40]) as tertiary colors.
 */
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * Applies the Marketplace application's theme to the provided content.
 *
 * This composable sets up the [MaterialTheme] for the application. It determines the appropriate color scheme
 * based on the following conditions:
 * - If [dynamicColor] is true and the device is running Android S (API level 31) or higher, it uses dynamic
 *   color schemes generated from the current wallpaper.
 *   The dynamic scheme is selected based on the [darkTheme] parameter.
 * - Otherwise, if [darkTheme] is true, [DarkColorScheme] is applied; if false, [LightColorScheme] is applied.
 *
 * The chosen color scheme and a predefined [Typography] are then applied to the [content] via [MaterialTheme].
 *
 * @param darkTheme Determines whether to use a dark theme. Defaults to the system setting provided
 * by [isSystemInDarkTheme].
 * @param dynamicColor Determines whether to use dynamic color schemes on supported devices. Defaults to true.
 * @param content The composable content to which the theme will be applied.
 */
@Composable
public fun MarketplaceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
