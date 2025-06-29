package r.team.unpluggedproviderapp.presentation.feature_devices

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import r.team.unpluggedproviderapp.core_ui.component.CoreFragment
import r.team.unpluggedproviderapp.core_ui.state.collectEvent
import r.team.unpluggedproviderapp.core_ui.state.render
import r.team.unpluggedproviderapp.core_ui.state.uiStateDiffRender
import r.team.unpluggedproviderapp.databinding.FragmentProviderBinding
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO

@AndroidEntryPoint
class DevicesFragment : CoreFragment<FragmentProviderBinding>(FragmentProviderBinding::inflate) {
    private val viewModel: DevicesViewModel by viewModels()
    private val viewRender = uiStateDiffRender {
        DevicesViewState::isLoading{ isLoading ->
            binding.loadingView.rootView.isVisible = isLoading
        }
        DevicesViewState::errorMessage{ error ->
            //state style error
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModelActions()
    }

    private fun bindViewModelActions() {
        with(viewModel) {
            render(viewLifecycleOwner, render = viewRender)
            collectEvent(lifecycle) { event ->
                return@collectEvent when (event) {
                    is DevicesViewEvent.ShowError -> {
                        Snackbar.make(
                            requireView(),
                            event.errorModelDO.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is DevicesViewEvent.SaveSuccessful -> {
                        Snackbar.make(
                            requireView(),
                            "Saved successfully",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }

        }
    }
}