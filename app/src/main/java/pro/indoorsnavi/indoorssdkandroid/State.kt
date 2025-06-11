package pro.indoorsnavi.indoorssdkandroid

sealed class State {

    data object None : State()

    data class AuthorizeSuccess(val stateMassage: String): State()
    data class AuthorizeFailed(val stateMassage: String): State()

    data class LoadingApplication(val stateMassage: String): State()
    data class LoadingBuildings(val stateMassage: String): State()

    data class SuccessLoad(val stateMassage: String): State()
    data class ErrorLoading(val stateMassage: String): State()

}