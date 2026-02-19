package fr.isen.amelie.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.amelie.thegreatestcocktailapp.activities.DetailCocktailActivity

@Composable
fun DrinksScreen(
    modifier: Modifier,
    category: String,
    snackbarHostState: SnackbarHostState
) {
    val drinks: List<String> = listOf(
        "Mojito",
        "Negroni",
        "Blue Lagoon",
        "Cuba Libre"
    )

    Scaffold(
        topBar = {
            TopAppBar2(
                snackbarHostState,
                "Drinks List"
            )
        },
        containerColor = Color(0xFF891E1E)
    ) { innerPadding ->
        LazyColumn(
            modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFFCDD2),
                            Color(0xFFFFF0F2)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp)
        ) {
            item {
                Text(text = category, color = Color(0xFF891E1E), fontSize = 50.sp)
            }

            items(items = drinks) { drink ->
                val context = LocalContext.current
                Button(
                    onClick = {
                        val intent = Intent(context, DetailCocktailActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(size = 25.dp),
                    colors = ButtonColors(
                        containerColor = Color.White.copy(alpha = 0.3f),
                        contentColor = Color(0xFF891E1E),
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    )
                ) {
                    Text(text = drink, fontSize = 30.sp)
                }
            }
        }
    }
}

