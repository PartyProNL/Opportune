package me.partypronl.opportune

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import me.partypronl.opportune.MainActivity.DetailPage
import me.partypronl.opportune.MainActivity.HomePage
import me.partypronl.opportune.data.Company
import me.partypronl.opportune.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    @Serializable
    object HomePage
    @Serializable
    data class DetailPage(val company: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()

    AppTheme {
        NavHost(navController, startDestination = HomePage) {
            composable<HomePage> { HomeScreen(navController) }

            composable<DetailPage> { backStackEntry ->
                val detailPage: DetailPage = backStackEntry.toRoute()
                DetailScreen(navController, detailPage.company)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    App()
}