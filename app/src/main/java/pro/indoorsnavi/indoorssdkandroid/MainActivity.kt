package pro.indoorsnavi.indoorssdkandroid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import pro.indoorsnavi.indoorssdkandroid.databinding.ActivityMainBinding
import pro.indoorsnavi.indoorssdkcore.core.INCore
import pro.indoorsnavi.indoorssdkcore.core.INCoreConfiguration

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[ActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeINCore()
        initViewModel()
    }

    private fun initializeINCore() {
        val configuration = INCoreConfiguration.defaultConfiguration(applicationContext)
        INCore.initializeWithConfiguration(applicationContext, configuration)
    }

    private fun initViewModel() {
        viewModel.getStateLiveData().value = State.None
        viewModel.getStateLiveData().observe(this) { data -> render(data) }
    }

    private fun render(state: State) {
        when (state) {
            is State.AuthorizeSuccess -> {
                binding.message.text = "Проверка авторизации, подождите пожалуйста ..."
            }
            is State.AuthorizeFailed -> {
                Toast.makeText(this,"Authorize failed", Toast.LENGTH_SHORT).show()
                binding.messageProgress.visibility = View.GONE
            }

            is State.LoadingApplication -> {
                binding.message.text = "Загрузка приложения, подождите пожалуйста ..."
            }

            is State.LoadingBuildings -> {
                binding.message.text = "Загрузка зданий, подождите пожалуйста ..."
            }

            is State.SuccessLoad -> {
                binding.messageProgress.visibility = View.GONE
                addMapFragment()
            }

            is State.ErrorLoading -> {
                binding.messageProgress.visibility = View.GONE
                Toast.makeText(this,"Error loading", Toast.LENGTH_SHORT).show()
            }

            is State.None -> {

            }
        }
    }

    private fun addMapFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.mainContainer, MapFragment())
            .commit()
    }

}