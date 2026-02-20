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
import androidx.compose.ui.text.style.TextAlign
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
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import androidx.compose.runtime.MutableState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import fr.isen.amelie.thegreatestcocktailapp.R
import fr.isen.amelie.thegreatestcocktailapp.network.DrinkModel
import fr.isen.amelie.thegreatestcocktailapp.network.Drinks
import fr.isen.amelie.thegreatestcocktailapp.network.NetworkManager


@Composable
fun DetailCocktailScreen(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    showBackButton: Boolean = true

) {
    val drink: MutableState<DrinkModel> = remember {mutableStateOf(value = DrinkModel())}

    LaunchedEffect(Unit) {
        val call: Call<Drinks> = NetworkManager.api.getRandomCocktail()
        call.enqueue(object : Callback<Drinks> {
            override fun onResponse(p0: Call<Drinks?>, p1: Response<Drinks?>) {
                drink.value = p1.body()?.drinks?.first() ?: DrinkModel()
            }

            override fun onFailure(p0: Call<Drinks?>, p1: Throwable) {
                Log.e("error", p1.message.toString())
            }
        })
    }

    var isFavorite by remember { mutableStateOf(false) }

    val ingredients = drink.value.getIngredients()

    Scaffold(
        topBar = {
            TopAppBar(
                snackbarHostState = snackbarHostState,
                navController = navController,
                showBackButton = showBackButton
            )
        },
        containerColor = colorResource(R.color.rose_clair),
    ) { innerPadding ->
        Column(
            modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = drink.value.imageURL,
                contentDescription = drink.value.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .size(size = 300.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = drink.value.name,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black, //ou 0xFFFF9800
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Category : " + drink.value.category)
            Text(text = "Type of glass : " + drink.value.glass)
            Text(text = drink.value.alcoholic)

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ingredients",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.rose_pale)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (ingredients.isEmpty()) {
                        Text(
                            text = "No ingredients found",
                            color = Color(0xFF281A0D),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        ingredients.forEach { (ingredient, measure) ->
                            Text(
                                text = "• ${measure.trim()} of ${ingredient.trim()}",
                                color = Color(0xFF281A0D),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Recipe",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.rose_pale)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = drink.value.instructions,
                        color = Color(0xFF281A0D)
                    )
                        }
                    }
                }
            }
        }



@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    snackbarHostState: SnackbarHostState,
    navController : NavController,
    showBackButton: Boolean
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Cocktail Spotlight",
            fontWeight = FontWeight.Bold, // gras
            fontSize = 24.sp
            )
        },

        // Flèche retour à gauche si showbackButton==true
        navigationIcon = {
            if (showBackButton) {
                /*
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = Color.White
                 */
                val activity = (LocalContext.current as? Activity)

                IconButton(onClick = {
                    val popped = navController.popBackStack()
                    if (!popped) {
                        activity?.finish()
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = Color.White
                    )
                }
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.bordeaux), // couleur de fond
            titleContentColor = colorResource(id = R.color.rose), // couleur du texte
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        actions = {
            val added = "Added to your favorites"
            val removed = "Removed from your favorites"
//            val context = LocalContext.current
            val snackbarScope = rememberCoroutineScope ()
            val isFav = remember { mutableStateOf(false) }

            IconToggleButton(
                isFav.value,
                onCheckedChange = {
                    isFav.value = !isFav.value
                    snackbarScope.launch {
                        snackbarHostState.showSnackbar(if (isFav.value) added else removed)
                    // TOAST
//                    Toast.makeText(
//                        context,
//                        if (isFav.value) added else removed,
//                        Toast.LENGTH_SHORT
//                    ).show()
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFav.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "fav",
                    tint = if (isFav.value)
                        colorResource(id = R.color.rose)
                    else Color.White
                )
            }
        }
    )
}

