package r.team.unpluggedproviderapp.core_ui.component

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import r.team.unpluggedproviderapp.core_ui.state.CoreViewState

/**
 * Abstract base class for ViewModels that holds and manages UI-related data in a lifecycle-conscious way.
 * Extending this class allows subclasses to work with a specific type of state [S] and utilize a [SavedStateHandle]
 * to retain and manage UI state across configuration changes.
 *
 * @param savedStateHandle A [SavedStateHandle] instance provided by the Android framework to store and retrieve
 *                          UI-related data associated with this ViewModel across configuration changes.
 *
 * @constructor Creates a new instance of [UViewModel] with the provided [savedStateHandle].
 *
 * ```
 * class ExampleViewModel(
 *     savedStateHandle:SavedStateHandle,
 * ) : UViewModel<UiState>(savedStateHandle),
 *     UiStateDelegate<UiState, Event> by UiStateDelegateImpl(UiState()) {
 *
 *     data class UiState(
 *         val isLoading: Boolean = false,
 *         val title: String = "",
 *         val login: String = "",
 *     )
 *
 *     sealed interface Event {
 *         object FinishFlow : Event
 *     }
 *
 *  }
 *  ```
 */
abstract class CoreViewModel<S:CoreViewState>(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    /**
     * Internal mutable state flow representing the UI state.
     * This flow is initialized with the value retrieved from the saved state handle, if available.
     */
    private val _uiState: MutableStateFlow<S?> = MutableStateFlow(
        savedStateHandle[UI_STATE]
    )

    /**
     * Exposed immutable state flow representing the UI state.
     * Consumers of this ViewModel should observe this flow to receive updates about UI state changes.
     */
    val uiSavedState = _uiState.asStateFlow()

    /**
     * Saves the provided UI state to the saved state handle.
     * This method should be called before accessing the UI state to ensure it's properly persisted.
     *
     * @param state The UI state to be saved.
     */
    open fun saveState(state: CoreViewState) {
        savedStateHandle[UI_STATE] = state
    }

    companion object {
        // Key for storing and retrieving UI state from the saved state handle.
        private const val UI_STATE = "ui_state"
    }

}

