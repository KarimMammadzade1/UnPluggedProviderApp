package r.team.unpluggedproviderapp.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import r.team.unpluggedproviderapp.R
import r.team.unpluggedproviderapp.core_ui.component.CoreActivity
import r.team.unpluggedproviderapp.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : CoreActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {

    }
}