package pro.nikita.examplenavigation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.indoorsnavi.indoorssdkcore.core.INCore
import pro.indoorsnavi.indoorssdkcore.model.INApplication
import pro.indoorsnavi.indoorssdkcore.model.INBuilding
import pro.indoorsnavi.indoorssdkcore.navigation.INNavigation
import pro.indoorsnavi.indoorssdkcore.navigation.INNavigationDelegate
import pro.indoorsnavi.indoorssdkcore.navigation.model.INUserPosition
import pro.indoorsnavi.indoorssdkcore.services.INResponseData

class NavigationActivityViewModel : ViewModel() {

    companion object {
        private const val CLIENT_ID: String = "YOUR_CLIENT_ID"
        private const val CLIENT_SECRET: String = "YOUR_CLIENT_SECRET"
    }

    private val stateLiveData = MutableLiveData<State>()

    private var currentApplication : INApplication? = null
    private var buildings : ArrayList<INBuilding>? = null

    fun getStateLiveData() = stateLiveData

    init {
        verifyAccessToken()
    }

    private fun verifyAccessToken() {
        INCore.getInstance().service.verifyAccessTokenWithCompletionBlock { isAuthorized ->
            if (isAuthorized as Boolean) {
                loadApplication()
            } else {
                authorizeApplication()
            }
        }
    }

    private fun authorizeApplication() {
        INCore.getInstance().service.authorizeApplicationWithClientId(CLIENT_ID,CLIENT_SECRET) { success: Any ->
            if (success as Boolean) {
                onAuthorizeSuccess()
            } else {
                onAuthorizeFailed()
            }
        }
    }

    private fun onAuthorizeSuccess() {
        stateLiveData.value = State.AuthorizeSuccess("authorize success")
        loadApplication()
    }

    private fun onAuthorizeFailed() {
        stateLiveData.value = State.AuthorizeFailed("authorize failed")
    }

    private fun loadApplication() {
        stateLiveData.value = State.LoadingApplication("loading application")

        INCore.getInstance().service.loadApplicationsWithCompletionBlock { applications: Any? ->
            val listApplications = applications as ArrayList<INApplication>

            if(listApplications.isNotEmpty()) {
                currentApplication = listApplications[0]
            } else {
                stateLiveData.setValue(State.ErrorLoading("error loading"))
            }

            loadBuildings()
        }
    }

    private fun loadBuildings() {
        stateLiveData.value = State.LoadingBuildings("loading buildings")

        INCore.getInstance().service.loadBuildingsOfApplication(currentApplication) { resultBuildings: INResponseData ->
            val listBuildings = resultBuildings.getData() as ArrayList<INBuilding>
            if (listBuildings.isNotEmpty()) {
                loadingBuildingData(listBuildings)
            } else {
                stateLiveData.setValue(State.ErrorLoading("error loading"))
            }
        }
    }

    private fun loadingBuildingData(listBuildings: ArrayList<INBuilding>) {
        INCore.getInstance().service.loadBuildingService.loadBuildings(listBuildings) { loadedBuildings ->
            /** All buildings have been loaded **/
            buildings = loadedBuildings
            stateLiveData.setValue(State.SuccessLoad("success loading"))

            startNavigation()
        }
    }

    fun startNavigation() {
        INCore.getInstance().navigation.setBuildings(buildings)
        INCore.getInstance().navigation.setNavigationDelegate(object : INNavigationDelegate {

            override fun onPosition(navigation: INNavigation?, userPosition: INUserPosition) {
                super.onPosition(navigation, userPosition)

                /** INDOORS — a position determined indoors using positioning sensors **/
                if(userPosition.typeLocation == INUserPosition.TypeLocation.INDOORS) {
                    Log.i("navigation","indoors position " +
                            "x:${userPosition.buildingPosition.X} " +
                            "y:${userPosition.buildingPosition.Y} " +
                            "floorId:${userPosition.buildingPosition.Floor.Id}" +
                            "type navigation:${userPosition.typeNavigation}")

                    stateLiveData.value = State.IndoorsPosition(
                        userPosition.buildingPosition.X,
                        userPosition.buildingPosition.Y,
                        userPosition.buildingPosition.Floor.Id,
                        userPosition.typeNavigation)
                }

                /** GLOBAL — a position determined in the Earth's geographic coordinates using GNSS. **/
                if(userPosition.typeLocation == INUserPosition.TypeLocation.GLOBAL) {
                    Log.i("navigation","global position " +
                            "Lat:${userPosition.globalPosition.Lat} " +
                            "Lon:${userPosition.globalPosition.Lon} " +
                            "type navigation:${userPosition.typeNavigation}")

                    stateLiveData.value = State.GlobalPosition(
                        userPosition.globalPosition.Lat,
                        userPosition.globalPosition.Lon,
                        userPosition.typeNavigation)
                }
            }
        })

        INCore.getInstance().navigation.startNavigation()
        INCore.getInstance().navigation.startLocation()
    }

    fun stopNavigation() {
        INCore.getInstance().navigation.stopNavigation()
        INCore.getInstance().navigation.stopLocation()

        INCore.getInstance().navigation.setNavigationDelegate(null)
        INCore.getInstance().navigation.clearBuildings()
    }

    fun pauseNavigation() {
        INCore.getInstance().navigation.pauseNavigation()
        INCore.getInstance().navigation.pauseLocation()
    }

    fun resumeNavigation() {
        INCore.getInstance().navigation.resumeNavigation()
        INCore.getInstance().navigation.resumeLocation()
    }

    override fun onCleared() {
        super.onCleared()
        stopNavigation()
    }
}