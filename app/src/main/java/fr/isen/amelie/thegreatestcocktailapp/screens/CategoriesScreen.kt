package fr.isen.amelie.thegreatestcocktailapp.screens

import android.app.Activity
import android.content.Intent
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import fr.isen.amelie.thegreatestcocktailapp.R
import fr.isen.amelie.thegreatestcocktailapp.activities.DrinksActivity
import fr.isen.amelie.thegreatestcocktailapp.network.Drinks
import fr.isen.amelie.thegreatestcocktailapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    onCategoryClick: (String) -> Unit = {}
) {

    val categories: MutableState<List<String>> = remember{ mutableStateOf(emptyList()) }

    LaunchedEffect (Unit) {
        val call: Call<Drinks> = NetworkManager.api.getListCategory()
        call.enqueue(object : Callback<Drinks> {
            override fun onResponse(p0: Call<Drinks?>, p1: Response<Drinks?>) {
                categories.value = p1.body()?.drinks
                    ?.map { it.category }          // récupère strCategory -> category
                    ?.filter { it.isNotBlank() }   // évite les vides
                    ?.distinct()                   // évite doublons
                    ?: emptyList()
            }

            override fun onFailure(p0: Call<Drinks?>, p1: Throwable) {
                Log.e("error", p1.message.toString())
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar2(snackbarHostState, "Categories")
        },
        containerColor = colorResource(id = R.color.rose_clair)
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
            items(categories.value.size) { index ->
                val category = categories.value[index]
                //val context = LocalContext.current
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
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.rose_pale)
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
            containerColor = colorResource(id = R.color.bordeaux), // couleur de fond
            titleContentColor = colorResource(id = R.color.rose), // couleur du texte
            actionIconContentColor = Color.White
        )

    )
}
