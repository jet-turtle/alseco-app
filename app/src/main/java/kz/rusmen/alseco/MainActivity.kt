package kz.rusmen.alseco

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import kz.rusmen.alseco.ui.AlsecoLayout
import kz.rusmen.alseco.ui.AlsecoViewModel
import kz.rusmen.alseco.ui.theme.ALSECOTheme

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {

    private  val viewModel: AlsecoViewModel by viewModels {
        AlsecoViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ALSECOTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AlsecoLayout()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveStateDataStore()
    }
}
