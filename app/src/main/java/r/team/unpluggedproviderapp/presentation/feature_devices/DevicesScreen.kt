package r.team.unpluggedproviderapp.presentation.feature_devices

import r.team.unpluggedproviderapp.core_domain.model.ErrorModelDO
import r.team.unpluggedproviderapp.core_ui.state.CoreViewState

data class DevicesViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) : CoreViewState()

interface DevicesViewEvent {
    data class ShowError(val errorModelDO: ErrorModelDO = ErrorModelDO()) : DevicesViewEvent
    object SaveSuccessful : DevicesViewEvent
}