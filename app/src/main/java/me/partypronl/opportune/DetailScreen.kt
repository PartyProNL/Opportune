package me.partypronl.opportune

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, company: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(company) },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(route = MainActivity.HomePage) }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Text(
            text = "Details for $company",
            Modifier.fillMaxSize().padding(innerPadding)
        )
    }
}

@Composable
@Preview
fun DetailPreview() {
    val navController = rememberNavController()
    DetailScreen(navController, "ING")
}