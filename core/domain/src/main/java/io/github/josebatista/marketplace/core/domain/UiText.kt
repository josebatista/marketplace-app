package io.github.josebatista.marketplace.core.domain

import android.content.Context
import androidx.annotation.StringRes

/**
 * Represents a piece of text to be displayed in the UI.
 *
 * This sealed interface supports two types of text:
 * - [DynamicText]: A plain, dynamic string.
 * - [StringResource]: A string defined in the application's resources, optionally formatted with arguments.
 *
 * Use the [asString] method to convert a [UiText] into a [String] using the provided [Context].
 */
public sealed interface UiText {

    /**
     * A dynamic text string.
     *
     * @property text The dynamic text value.
     */
    public data class DynamicText(val text: String) : UiText

    /**
     * A text resource.
     *
     * @property resId The string resource ID.
     * @property args Optional arguments for formatting the string.
     */
    public data class StringResource(@StringRes val resId: Int, val args: Any? = null) : UiText

    /**
     * Converts this [UiText] instance into a [String] using the provided [context].
     *
     * - For [DynamicText], it returns the contained text.
     * - For [StringResource], it retrieves and formats the string from the application's resources.
     *
     * @param context The [Context] used to access resources.
     * @return The resulting string.
     */
    public fun asString(context: Context): String {
        return when (this) {
            is DynamicText -> text
            is StringResource -> context.getString(resId, args)
        }
    }
}
