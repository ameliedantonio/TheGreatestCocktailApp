package fr.isen.amelie.thegreatestcocktailapp.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import fr.isen.amelie.thegreatestcocktailapp.R
import fr.isen.amelie.thegreatestcocktailapp.activities.DetailCocktailActivity
import fr.isen.amelie.thegreatestcocktailapp.activities.SharedPreferenceHelper
import fr.isen.amelie.thegreatestcocktailapp.network.DrinkModel
import fr.isen.amelie.thegreatestcocktailapp.network.Drinks
import fr.isen.amelie.thegreatestcocktailapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun FavoriteScreen(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState
) {

    val drinks: MutableState<List<DrinkModel>> = remember { mutableStateOf<List<DrinkModel>> (value=listOf()) }
    val sharedPreferences = SharedPreferenceHelper(LocalContext.current)
    val favList = sharedPreferences.getFavoriteList()

    LaunchedEffect(Unit) {
        for (id in favList) {
            val call: Call<Drinks> = NetworkManager.api.getDrinksById(id)

            call.enqueue(object : Callback<Drinks> {
                override fun onResponse(p0: Call<Drinks?>, p1: Response<Drinks?>) {
                    drinks.value += p1.body()?.drinks?.first() ?: DrinkModel()
                }

                override fun onFailure(p0: Call<Drinks?>, p1: Throwable) {
                    Log.e("error", p1.message.toString())
                }
            })
        }
    }

    Scaffold(
        topBar = {
            TopAppBar2(
                snackbarHostState,
                "Favorites"
            )
        },
        containerColor = colorResource(id = R.color.bordeaux)
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


            items(drinks.value) { drink ->
                val context = LocalContext.current
                Button(
                    onClick = {
                        val intent = Intent(context, DetailCocktailActivity::class.java)
                        intent.putExtra("drinkId", drink.id)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(size = 25.dp),
                    colors = ButtonColors(
                        containerColor = Color.White.copy(alpha = 0.3f),
                        contentColor = colorResource(id = R.color.bordeaux),
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    )
                ) {
                    Text(drink.name, fontSize = 30.sp)
                }
            }
        }
    }
}

