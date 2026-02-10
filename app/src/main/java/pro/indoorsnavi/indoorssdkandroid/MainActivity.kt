package pro.indoorsnavi.indoorssdkandroid

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

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

    private fun render(state: State) {
        when (state) {
            is State.AuthorizeSuccess -> {
                findViewById<TextView>(R.id.message).text = "Проверка авторизации, подождите пожалуйста ..."
            }
            is State.AuthorizeFailed -> {
                Toast.makeText(this,"Authorize failed", Toast.LENGTH_SHORT).show()
                findViewById<View>(R.id.messageProgress).visibility = View.GONE
            }

            is State.LoadingApplication -> {
                findViewById<TextView>(R.id.message).text = "Загрузка приложения, подождите пожалуйста ..."
            }

            is State.LoadingBuildings -> {
                findViewById<TextView>(R.id.message).text = "Загрузка зданий, подождите пожалуйста ..."
            }

            is State.SuccessLoad -> {
                findViewById<View>(R.id.messageProgress).visibility = View.GONE
                addMapFragment()
            }

            is State.ErrorLoading -> {
                findViewById<View>(R.id.messageProgress).visibility = View.GONE
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