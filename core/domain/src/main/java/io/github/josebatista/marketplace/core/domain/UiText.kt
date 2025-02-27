package io.github.josebatista.marketplace.domain

import android.content.Context
import androidx.annotation.StringRes

public sealed interface UiText {
    public data class DynamicText(val text: String) : UiText
    public data class StringResource(@StringRes val resId: Int, val args: Any? = null) : UiText

    public fun asString(context: Context): String {
        return when (this) {
            is DynamicText -> text
            is StringResource -> context.getString(resId, args)
        }
    }
}
