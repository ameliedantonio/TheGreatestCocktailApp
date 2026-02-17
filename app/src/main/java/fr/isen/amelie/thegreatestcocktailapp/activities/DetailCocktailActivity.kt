package fr.isen.amelie.thegreatestcocktailapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import fr.isen.amelie.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.amelie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class DetailCocktailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val snackbarHostState = SnackbarHostState()
        setContent {
            TheGreatestCocktailAppTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    }
                ) { innerPadding ->
                    DetailCocktailScreen(
                        modifier = Modifier.Companion.padding(innerPadding),
                        snackbarHostState
                    )
                }
            }
        }
    }
}