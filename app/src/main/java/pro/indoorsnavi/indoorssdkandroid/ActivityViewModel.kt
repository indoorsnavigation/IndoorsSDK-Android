package pro.indoorsnavi.indoorssdkandroid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.indoorsnavi.indoorssdk.core.INCore
import pro.indoorsnavi.indoorssdk.model.INApplication
import pro.indoorsnavi.indoorssdk.model.INBuilding
import pro.indoorsnavi.indoorssdk.services.INResponseData

class ActivityViewModel : ViewModel() {

    // Precnya City
    companion object {
        private const val BUILDING_ID = 10433L
        private const val CLIENT_ID: String = "XxlIlEUxExc6hsUk0v7hOiTctyKicAD4RoRIYdJt"
        private const val CLIENT_SECRET: String = "edELkEYTCWM36ufZv0emgIFPlYfxKHsLgPp7CXPJ8M2LnSap1BP2Bx3EuXrxtib1BH1hisSm6DwKNi80qA7GKbvORPB8L9flD5D5y7wAIrw2BdDlvfwBUARHtexLxWei"
    }

    private val stateLiveData = MutableLiveData<State>()

    private var currentApplication : INApplication? = null
    private var currentBuilding : INBuilding? = null
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
        INCore.getInstance().service.authorizeApplicationWithClientId(CLIENT_ID, CLIENT_SECRET) { success: Any ->
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
                loadBuildings()
            } else {
                stateLiveData.setValue(State.ErrorLoading("error loading"))
            }
        }
    }

    private fun loadBuildings() {
        stateLiveData.value = State.LoadingBuildings("loading buildings")

        INCore.getInstance().service.loadBuildingsOfApplication(currentApplication,{ resultBuildings: INResponseData ->

            val listBuildings = resultBuildings.getData() as ArrayList<INBuilding>

            if (listBuildings.isNotEmpty()) {
                buildings = listBuildings
                selectCurrentBuilding()

                stateLiveData.setValue(State.SuccessLoad("success loading"))
            } else {
                stateLiveData.setValue(State.ErrorLoading("error loading"))
            }
        }, { error -> })
    }

    private fun selectCurrentBuilding() {

        val buildingId = BUILDING_ID

        buildings?.forEach { building -> if(building.Id == buildingId) {
            currentBuilding = building
            return@forEach
        } }
    }

    fun getCurrentBuilding(): INBuilding? {
        return currentBuilding
    }

    fun getCurrentBuildings(): ArrayList<INBuilding>? {
        return buildings
    }

}