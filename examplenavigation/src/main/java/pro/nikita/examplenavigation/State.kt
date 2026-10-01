package pro.nikita.examplenavigation

import pro.indoorsnavi.indoorssdkcore.navigation.model.INUserPosition

sealed class State {
    data object None : State()

    data class AuthorizeSuccess(val stateMassage: String): State()
    data class AuthorizeFailed(val stateMassage: String): State()

    data class LoadingApplication(val stateMassage: String): State()
    data class LoadingBuildings(val stateMassage: String): State()

    data class SuccessLoad(val stateMassage: String): State()
    data class ErrorLoading(val stateMassage: String): State()

    data class IndoorsPosition(val x: Float, val y: Float, val floorId: Long, val typeNavigation: INUserPosition.TypeNavigation): State()
    data class GlobalPosition(val lat: Double, val lon: Double, val typeNavigation: INUserPosition.TypeNavigation): State()
}