package io.github.josebatista.marketplace.buildlogic

import java.util.Locale

/**
 * Extension function to capitalize a String if its needed.
 *
 * It was created because the original capitalize() Kotlin function has been deprecated.
 */
internal fun String.capitalized(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(Locale.getDefault())
        } else {
            it.toString()
        }
    }
}
