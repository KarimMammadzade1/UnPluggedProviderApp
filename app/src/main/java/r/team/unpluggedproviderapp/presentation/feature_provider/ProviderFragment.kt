package r.team.unpluggedproviderapp.presentation.feature_provider

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import r.team.unpluggedproviderapp.core_ui.component.CoreFragment
import r.team.unpluggedproviderapp.core_ui.state.collectEvent
import r.team.unpluggedproviderapp.core_ui.state.render
import r.team.unpluggedproviderapp.core_ui.state.uiStateDiffRender
import r.team.unpluggedproviderapp.databinding.FragmentProviderBinding

@AndroidEntryPoint
class ProviderFragment : CoreFragment<FragmentProviderBinding>(FragmentProviderBinding::inflate) {
    private val viewModel: ProviderViewModel by viewModels()
    private val viewRender = uiStateDiffRender {
        ProviderViewState::isLoading{ isLoading ->
            binding.loadingView.rootView.isVisible = isLoading
        }
        ProviderViewState::devicesData{ data ->
            Log.e("testing", "$data ", )
        }
        ProviderViewState::errorMessage{ error ->
            //state style error
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        bindViewModelActions()
    }

    private fun bindViewModelActions() {
        with(viewModel) {
            render(viewLifecycleOwner, render = viewRender)
            collectEvent(lifecycle) { event ->
                return@collectEvent when (event) {
                    is ProviderViewEvent.ShowError -> {
                        Snackbar.make(
                            requireView(),
                            event.errorModelDO.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }

        }
    }

    private fun initClickListeners() {
        with(binding) {
            fetchElementsButton.setOnClickListener {
                viewModel.fetchElements()
            }
        }
    }
}