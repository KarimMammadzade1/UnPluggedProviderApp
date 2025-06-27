package r.team.unpluggedproviderapp.core_ui.state

/**
 * Efficient UI state renderer for performing differential updates.
 * This class facilitates rendering UI updates efficiently by detecting changes in UI states and invoking callbacks
 * only for the properties that have changed since the last render.
 *
 * @param T The type of UI state to be rendered.
 * @property renders List of render operations for properties of the UI state.
 */
class CoreViewStateDiffRender<T> private constructor(
    private val renders: List<Render<T, Any>>
) {

    private var lastUiState: T? = null

    fun render(newState: T) {
        lastUiState.let { oldUiState ->
            renders.asSequence().forEach { render ->
                val property = render.property
                val newProperty = property(newState)
                if (oldUiState == null || property(oldUiState) != newProperty) {
                    render.callback(newProperty)
                }
            }
        }

        lastUiState = newState
    }

    private class Render<T, R>(
        val property: (T) -> R,
        val callback: (R) -> Unit,
    )

    /**
     * it's obligatory to clear render in onDestroyView
     */
    fun clear() {
        lastUiState = null
    }

    /**
     * Builder class for constructing instances of UiStateDiffRender.
     *
     * This builder allows developers to specify UI state properties and their associated
     * rendering callbacks, and then construct a UiStateDiffRender instance.
     *
     * @param T The type of the UI state.
     * @property renders A list of property-render pairs representing the UI state properties
     *                   and their associated callbacks.
     * @constructor Creates a new Builder instance.
     */
    class Builder<T> @PublishedApi internal constructor() {

        private val renders = mutableListOf<Render<T, Any>>()

        operator fun <R> ((T) -> R).invoke(callback: (R) -> Unit) {
            renders += Render(
                property = this,
                callback = callback,
            ) as Render<T, Any>
        }

        fun build(): CoreViewStateDiffRender<T> = CoreViewStateDiffRender(renders)
    }
}
