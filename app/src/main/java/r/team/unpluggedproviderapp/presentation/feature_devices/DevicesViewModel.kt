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
import r.team.unpluggedproviderapp.domain.usecase.GetDevicesUseCase
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getDevicesUseCase: GetDevicesUseCase
) : CoreViewModel<DevicesViewState>(savedStateHandle),
    UiStateDelegate<DevicesViewState, DevicesViewEvent> by
    UiStateDelegateImpl(DevicesViewState()) {


    fun fetchElements() {
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true) }
            when (val resultWrapper = getDevicesUseCase.run(Unit)) {
                is ResultWrapper.Success -> {

                    updateUiState {
                        it.copy(
                            isLoading = false,
                            devicesData = resultWrapper.success
                        )
                    }
                }

                is ResultWrapper.Error -> {
                    updateUiState { it.copy(isLoading = false) }
                    sendEvent(DevicesViewEvent.ShowError(resultWrapper.error))

                }
            }
        }
    }

    fun saveElements(data: List<DeviceResponseDO>) {

    }
}