package fr.isen.amelie.thegreatestcocktailapp.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.platform.LocalContext
import fr.isen.amelie.thegreatestcocktailapp.activities.DrinksActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onCategoryClick: (String) -> Unit = {}
) {
    // Partie 3
    val categories: List<String> = listOf(
        "Beer",
        "Cocktail",
        "Cocoa",
        "Coffee",
        "Shot",
        "Soft Drink"
    )

    Scaffold(
        topBar = {
            TopAppBar2(snackbarHostState, "Categories")
        },
        containerColor = Color(0xFFFFE5E5)
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories.size) { index ->
                val category = categories[index]
                val context = LocalContext.current
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(8.dp)
                        .clickable {
                            onCategoryClick(category)
                            /*val intent = Intent(context, DrinksActivity::class.java)
                            intent.putExtra("category", category)
                            context.startActivity(intent)
                             */
                        },
                    shape = RoundedCornerShape(32.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = getCategoryColor(category)
                    )
                ) {
                    // voile glass
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White.copy(alpha = 0.18f))
                    ) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = category,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                letterSpacing = 0.5.sp,
                                color = Color(0xFF281A0D)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getCategoryColor(category: String): Color {
    return when (category) {
        "Beer" -> Color(0xFFFFF0F2)
        "Cocktail" -> Color(0xFFFFF0F2)
        "Cocoa" -> Color(0xFFFFF0F2)
        "Coffee" -> Color(0xFFFFF0F2)
        "Shot" -> Color(0xFFFFF0F2)
        "Soft Drink" -> Color(0xFFFFF0F2)
        else -> Color.White
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar2(
    snackbarHostState: SnackbarHostState,
    title: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = title,
            fontWeight = FontWeight.Bold, // gras
            fontSize = 24.sp
            )
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF891E1E), // couleur de fond
            titleContentColor = Color(0xFFFFCDD2),    // couleur du texte
            actionIconContentColor = Color.White
        )

    )
}
