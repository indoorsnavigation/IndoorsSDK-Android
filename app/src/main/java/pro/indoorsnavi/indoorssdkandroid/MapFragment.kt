package pro.indoorsnavi.indoorssdkandroid

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import pro.indoorsnavi.indoorssdkmap.map.views.INGlobalMapFragment

class MapFragment : INGlobalMapFragment() {

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentBuilding = viewModel.getCurrentBuilding()
        val buildings = viewModel.getCurrentBuildings()

        setBuildings(buildings)
        setCurrentBuilding(currentBuilding)
    }
}
