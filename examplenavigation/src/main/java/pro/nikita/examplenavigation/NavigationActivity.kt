package pro.nikita.examplenavigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import pro.nikita.examplenavigation.databinding.ActivityMainBinding

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[NavigationActivityViewModel::class.java]
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

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getStateLiveData().value = State.None
        viewModel.getStateLiveData().observe(this) { data -> render(data) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeNavigation()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseNavigation()
    }

    @SuppressLint("SetTextI18n")
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
            }

            is State.ErrorLoading -> {
                binding.messageProgress.visibility = View.GONE
                Toast.makeText(this,"Error loading", Toast.LENGTH_SHORT).show()
            }

            is State.IndoorsPosition -> {
                binding.position.text = "Индорс позиция\n\nx: ${state.x}\ny: ${state.y}\nfloorId: ${state.floorId}\ntypeNavigation: ${state.typeNavigation}"
            }

            is State.GlobalPosition -> {
                binding.position.text = "Глобальная позиция\n\nlat: ${state.lat}\nlon: ${state.lon}\ntypeNavigation: ${state.typeNavigation}"
            }

            is State.None -> {

            }
        }
    }
}