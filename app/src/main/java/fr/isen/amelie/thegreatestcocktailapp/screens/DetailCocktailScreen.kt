package fr.isen.amelie.thegreatestcocktailapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import fr.isen.amelie.thegreatestcocktailapp.R
import kotlinx.coroutines.launch

@Composable
fun DetailCocktailScreen(modifier: Modifier, snackbarHostState: SnackbarHostState) {
    var isFavorite by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(snackbarHostState)
        },
        containerColor = Color(0xFFF4E4C1)
    ) { innerPadding ->
        Column(
            modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.spritz),
                contentDescription = "photo spritz",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(size = 300.dp)
                //contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Spritz",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800), //ou 0xFFFF6F00
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Categorie : Cocktail")
            Text(text = "Verre : Verre à vin")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ingrédients",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF8E1)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("• 6 cl de Prosecco")
                    Text("• 4 cl d’Aperol")
                    Text("• 2 cl d’eau gazeuse")
                    Text("• Glaçons")
                    Text("• Tranche d’orange")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Recette",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF8E1)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("1.  Remplir un verre de glaçons")
                    Text("2.  Ajouter le Prosecco")
                    Text("3.  Ajouter l’Aperol")
                    Text("4.  Compléter avec l’eau gazeuse")
                    Text("5.  Mélanger doucement")
                    Text("6.  Ajouter une tranche d’orange")
                }
            }
        }
    }
}

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    snakebarHostState: SnackbarHostState) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Random")
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFF9800), // couleur de fond
            titleContentColor = Color.White,    // couleur du texte
            actionIconContentColor = Color.White
        ),
        actions = {
            val added = "Ajouté aux favoris"
            val removed = "Retiré des favoris"
//            val context = LocalContext.current
            val snackbarScope = rememberCoroutineScope ()
            val isFav = remember { mutableStateOf(false) }
            IconToggleButton(
                isFav.value,
                onCheckedChange = {
                    isFav.value = !isFav.value
                    // TOAST
//                    Toast.makeText(
//                        context,
//                        if (isFav.value) added else removed,
//                        Toast.LENGTH_SHORT
//                    ).show()
                    snackbarScope.launch {
                        snakebarHostState.showSnackbar(if (isFav.value) added else removed)
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFav.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "fav",
                    tint = if (isFav.value) Color.Red else Color.Gray
                )
            }
        }
    )
}

