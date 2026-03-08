package fr.isen.amelie.thegreatestcocktailapp.screens

import android.content.Intent
import android.util.Log
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.style.TextAlign
import fr.isen.amelie.thegreatestcocktailapp.R
import fr.isen.amelie.thegreatestcocktailapp.activities.DetailCocktailActivity
import fr.isen.amelie.thegreatestcocktailapp.network.DrinkModel
import fr.isen.amelie.thegreatestcocktailapp.network.Drinks
import fr.isen.amelie.thegreatestcocktailapp.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    val searchText = remember { mutableStateOf("") }
    val drinks: MutableState<List<DrinkModel>> = remember { mutableStateOf(listOf()) }
    val allDrinks: MutableState<List<DrinkModel>> = remember { mutableStateOf(listOf()) }
    val alcoholFilter = remember { mutableStateOf("All") }

    Scaffold(
        topBar = {
            TopAppBar2(
                snackbarHostState = snackbarHostState,
                title = "Search"
            )
        },
        containerColor = colorResource(id = R.color.bordeaux)
    ) { innerPadding ->
        val context = LocalContext.current

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(id = R.color.rose),
                            colorResource(id = R.color.rose_pale)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //barre de recherche
            item {
                OutlinedTextField(
                    value = searchText.value,
                    onValueChange = { newValue ->
                        searchText.value = newValue

                        if (newValue.isBlank()) {
                            drinks.value = emptyList()
                        } else {
                            val call: Call<Drinks> = NetworkManager.api.searchDrinksByName(newValue)
                            call.enqueue(object : Callback<Drinks> {
                                override fun onResponse(call: Call<Drinks?>, response: Response<Drinks?>) {

                                    allDrinks.value = response.body()?.drinks ?: emptyList()

                                    drinks.value = allDrinks.value.filter { drink ->

                                        alcoholFilter.value == "All" ||
                                                drink.alcoholic.equals(alcoholFilter.value, true)

                                    }
                                }

                                override fun onFailure(call: Call<Drinks?>, t: Throwable) {
                                    Log.e("error", t.message.toString())
                                }
                            })
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Search a drink") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            tint = colorResource(id = R.color.bordeaux)
                        )
                    },
                    trailingIcon = {
                        if (searchText.value.isNotEmpty()) {
                            IconButton(onClick = {
                                searchText.value = ""
                                drinks.value = emptyList()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "clear search",
                                    tint = colorResource(id = R.color.bordeaux)
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(id = R.color.rose_pale),
                        unfocusedContainerColor = colorResource(id = R.color.rose_pale),

                        focusedIndicatorColor = colorResource(id = R.color.bordeaux),
                        unfocusedIndicatorColor = colorResource(id = R.color.bordeaux),

                        cursorColor = colorResource(id = R.color.bordeaux),
                        focusedTextColor = colorResource(id = R.color.bordeaux),
                        unfocusedTextColor = colorResource(id = R.color.bordeaux)
                    )
                )
            }

            //filtre alcool
            item {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    FilterChip(
                        selected = alcoholFilter.value == "All",
                        onClick = {
                            alcoholFilter.value = "All"
                            drinks.value = allDrinks.value
                        },
                        label = { Text("All") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorResource(id = R.color.bordeaux),
                            selectedLabelColor = Color.White
                        )
                    )

                    FilterChip(
                        selected = alcoholFilter.value == "Alcoholic",
                        onClick = {
                            alcoholFilter.value = "Alcoholic"

                            drinks.value = allDrinks.value.filter {
                                it.alcoholic.equals("Alcoholic", true)
                            }
                        },
                        label = { Text("Alcoholic") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorResource(id = R.color.bordeaux),
                            selectedLabelColor = Color.White
                        )
                    )

                    FilterChip(
                        selected = alcoholFilter.value == "Non Alcoholic",
                        onClick = {
                            alcoholFilter.value = "Non Alcoholic"
                            drinks.value = allDrinks.value.filter {
                                it.alcoholic.equals("Non Alcoholic", true)
                            }
                        },
                        label = { Text("Non Alcoholic") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colorResource(id = R.color.bordeaux),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }


            if (searchText.value.isBlank()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 80.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.search_cocktail),
                            contentDescription = "search cocktail",
                            modifier = Modifier.size(200.dp)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Type the name of a drink you'd like",
                            color = colorResource(id = R.color.bordeaux),
                            fontSize = 22.sp
                        )
                    }
                }
            }

            if (searchText.value.isNotBlank() && drinks.value.isEmpty()) {
                item {
                    Text(
                        text = "No cocktail found",
                        color = colorResource(id = R.color.bordeaux),
                        fontSize = 20.sp
                    )
                }
            }

            items(drinks.value) { drink ->
                Button(
                    onClick = {
                        val intent = Intent(context, DetailCocktailActivity::class.java)
                        intent.putExtra("drinkId", drink.id)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonColors(
                        containerColor = Color.White.copy(alpha = 0.3f),
                        contentColor = colorResource(id = R.color.bordeaux),
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    )
                ) {
                    Text(
                        text = drink.name,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        lineHeight = 26.sp
                    )
                }
            }
        }
    }
}