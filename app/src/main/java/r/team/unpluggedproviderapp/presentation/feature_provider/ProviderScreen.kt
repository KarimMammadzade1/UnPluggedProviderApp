package r.team.unpluggedproviderapp.presentation.feature_provider

import r.team.unpluggedproviderapp.core_domain.model.ErrorModelDO
import r.team.unpluggedproviderapp.core_ui.state.CoreViewState
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO

data class ProviderViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val devicesData: List<DeviceResponseDO> = emptyList()
) : CoreViewState()

interface ProviderViewEvent {
    data class ShowError(val errorModelDO: ErrorModelDO = ErrorModelDO()) : ProviderViewEvent
}