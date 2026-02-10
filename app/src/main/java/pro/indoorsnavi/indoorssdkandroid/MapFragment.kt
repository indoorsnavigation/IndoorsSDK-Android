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


        val renderConfig = INRenderConfig()
        val globalLight = INGlobalLight()

        val territoryId = "3"
        val windowLightWidth = 10500
        val windowLightHeight = 8000
        val mScale = 10.000158f

        renderConfig.limitFPS = null

        globalLight.Id = "global-light-id-$territoryId"
        globalLight.posLat = 55.65446205378
        globalLight.posLon = 37.50005908
        globalLight.posAlt = 350.0
        globalLight.lookLat = 55.65156958353682
        globalLight.lookLon = 37.501613311732584
        globalLight.lookAlt = 0.0
        globalLight.minLight = 224.0f
        globalLight.maxLight = 252.0f
        globalLight.windowLightHeight = (windowLightHeight / mScale)
        globalLight.windowLightWidth = (windowLightWidth / mScale)
        globalLight.mapShadowRes = 6100f
        globalLight.shading3DModelIntensity = 0.3f
        globalLight.shadowBrightness3DModel = 0.755f
        globalLight.shadowBrightnessExtrusionPolygon = 0.84f
        globalLight.minBiasShadow = 0.0002f
        globalLight.maxBiasShadow = 0.0005f
        globalLight.ambientColor = floatArrayOf(0.0f, 0.0f, 0.0f)
        globalLight.isShadowMappingEnabled = true
        globalLight.drawShadowOnCanvasBuildingId = ""

        renderConfig.addGlobalLightConfigForTerritory(globalLight, territoryId)


        binding.indoorsMapView.setMapRenderConfig(renderConfig)
        binding.indoorsMapView.setBuildings(buildings)
        binding.indoorsMapView.focusMapCameraToBuilding(currentBuilding)
    }
}
