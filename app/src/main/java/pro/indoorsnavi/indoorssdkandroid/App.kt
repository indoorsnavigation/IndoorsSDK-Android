package pro.indoorsnavi.indoorssdkandroid

import android.app.Application
import pro.indoorsnavi.indoorssdkcore.core.INCore
import pro.indoorsnavi.indoorssdkcore.core.INCoreConfiguration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeINCore()
    }

    private fun initializeINCore() {
        val configuration = INCoreConfiguration.defaultConfiguration(applicationContext)
        INCore.initializeWithConfiguration(applicationContext, configuration)
    }
}