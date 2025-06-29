package r.team.unpluggedproviderapp.presentation.feature_devices

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import r.team.unpluggedproviderapp.core_domain.model.ResultWrapper
import r.team.unpluggedproviderapp.core_ui.component.CoreViewModel
import r.team.unpluggedproviderapp.core_ui.state.UiStateDelegate
import r.team.unpluggedproviderapp.core_ui.state.UiStateDelegateImpl
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO
import r.team.unpluggedproviderapp.domain.usecase.GetDevicesFromDBUseCase
import r.team.unpluggedproviderapp.domain.usecase.GetDevicesUseCase
import r.team.unpluggedproviderapp.domain.usecase.SaveDevicesUseCase
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val saveDevicesUseCase: SaveDevicesUseCase,
) : CoreViewModel<DevicesViewState>(savedStateHandle),
    UiStateDelegate<DevicesViewState, DevicesViewEvent> by
    UiStateDelegateImpl(DevicesViewState()) {

    init {
        saveElements()
    }

    private fun saveElements() {
        viewModelScope.launch {
            when (val resultWrapper = saveDevicesUseCase.run(Unit)) {
                is ResultWrapper.Success -> {
                    sendEvent(DevicesViewEvent.SaveSuccessful)
                }

                is ResultWrapper.Error -> {
                    sendEvent(DevicesViewEvent.ShowError(resultWrapper.error))
                }
            }
        }
    }

}