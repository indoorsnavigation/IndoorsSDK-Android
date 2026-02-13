package pro.indoorsnavi.indoorssdkandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pro.indoorsnavi.indoorssdkandroid.databinding.FragmentLayoutBinding
import pro.indoorsnavi.indoorssdkmap.map.renderer.INGlobalLight
import pro.indoorsnavi.indoorssdkmap.map.renderer.INRenderConfig

class MapFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    private lateinit var binding: FragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLayoutBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentBuilding = viewModel.getCurrentBuilding()
        val buildings = viewModel.getCurrentBuildings()

        binding.indoorsMapView.setMaxFps(60)
        binding.indoorsMapView.setBuildings(buildings)
        binding.indoorsMapView.focusMapCameraToBuilding(currentBuilding)
    }

    override fun onResume() {
        super.onResume()
        binding.indoorsMapView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.indoorsMapView.pause()
    }

    fun onBackPressed() = binding.indoorsMapView.onBackPressed()
}
