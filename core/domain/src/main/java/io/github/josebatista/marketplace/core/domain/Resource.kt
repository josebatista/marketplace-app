package io.github.josebatista.marketplace.domain

public sealed interface Resource<out D, out E> {
    public class Success<out D>(public val data: D) : Resource<D, Nothing>
    public class Error<out E>(public val message: UiText) : Resource<Nothing, E>
}
