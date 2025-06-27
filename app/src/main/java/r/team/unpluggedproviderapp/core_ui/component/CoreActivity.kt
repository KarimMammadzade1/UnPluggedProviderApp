package r.team.unpluggedproviderapp.core_ui.component

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import r.team.unpluggedproviderapp.R
import timber.log.Timber

typealias Inflate<T> = (LayoutInflater) -> T

abstract class CoreActivity<VB : ViewBinding>(
    private val inflate: Inflate<VB>,
) : AppCompatActivity() {

    /**
     * The ViewBinding instance associated with the activity.
     */
    private var _binding: VB? = null
    val binding get() = _binding!!

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * This method inflates the ViewBinding instance using the provided lambda function and sets
     * the content view to the root view of the binding.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * Note: Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("Create activity %s", this.javaClass.name)
        _binding = inflate.invoke(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}