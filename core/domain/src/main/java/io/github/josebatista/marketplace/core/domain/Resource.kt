package io.github.josebatista.marketplace.core.domain

/**
 * Represents a resource which can either be a success or an error.
 *
 * This sealed interface is used to encapsulate the result of an operation that may succeed or fail.
 * It has two possible outcomes:
 * - [Success]: Indicates a successful operation and contains the resulting data.
 * - [Error]: Indicates a failed operation and contains an error message as a [UiText].
 *
 * @param D The type of the data on success.
 * @param E The type of the error information (unused in [Success]).
 */
public sealed interface Resource<out D, out E> {

    /**
     * Represents a successful outcome containing the resulting data.
     *
     * @param D The type of the data.
     * @property data The data resulting from a successful operation.
     */
    public class Success<out D>(public val data: D) : Resource<D, Nothing>

    /**
     * Represents an error outcome containing an error message.
     *
     * @param E The type of the error information (unused in [Success]).
     * @property message A [UiText] describing the error.
     */
    public class Error<out E>(public val message: UiText) : Resource<Nothing, E>
}
