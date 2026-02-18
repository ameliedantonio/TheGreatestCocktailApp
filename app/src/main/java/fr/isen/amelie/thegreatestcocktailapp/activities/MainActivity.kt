package fr.isen.amelie.thegreatestcocktailapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.navigation.NavType
import androidx.navigation.navArgument
import fr.isen.amelie.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.amelie.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.amelie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import fr.isen.amelie.thegreatestcocktailapp.screens.DrinksScreen



enum class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    Home(title="A la une", Icons.Default.Home, route="home"),
    List(title="Categories", Icons.Default.Menu, route="list"),
    Fav(title="Favoris", Icons.Default.Favorite, route="fav"),
}

// Pour avoir la bottombar sur la page DrinksScreen
object Routes {
    const val DRINKS = "drinks/{category}"
    fun drinks(category: String) = "drinks/$category"
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme{
                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                val startNavigationItem = NavigationItem.Home
                val currentNavigationItem: MutableState<NavigationItem> = remember { mutableStateOf(value= startNavigationItem) }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(snackbarHostState)
                    },
                    bottomBar = {
                        NavigationBar (
                            containerColor = Color(0xFF891E1E) //rouge bordeaux
                        ) {
                            NavigationItem.entries.forEach { navigationItem ->
                                NavigationBarItem(
                                    selected = currentNavigationItem.value == navigationItem,
                                    onClick = {
                                        navController.navigate(navigationItem.route)
                                        currentNavigationItem.value = navigationItem
                                    },
                                    label = {
                                        Text(text = navigationItem.title)
                                    },
                                    icon = {
                                        Icon(navigationItem.icon, contentDescription = "")
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color.White,          // icône sélectionnée
                                        selectedTextColor = Color(0xFFFFCDD2),          // texte sélectionné
                                        unselectedIconColor = Color(0xFFFFCDD2),    // icône non sélectionnée
                                        unselectedTextColor = Color(0xFFFFCDD2),    // texte non sélectionné
                                        indicatorColor = Color(0xFFFFCDD2)        // fond de l’élément sélectionné (optionnel)
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        //modifier = Modifier.padding(innerPadding),
                        startDestination = startNavigationItem.route
                    ) {
                        // La bottombar (3 boutons)
                        NavigationItem.entries.forEach { navigationItem ->
                            composable (navigationItem.route) {
                                when (navigationItem) {
                                    NavigationItem.Home -> DetailCocktailScreen(
                                        Modifier.padding(
                                            paddingValues = innerPadding
                                        ), snackbarHostState
                                    )
                                    NavigationItem.List -> CategoriesScreen(
                                        Modifier.padding(
                                            paddingValues = innerPadding
                                        ), snackbarHostState,
                                        // Pour la bottombar dans DrinksScreen
                                        onCategoryClick = { category ->
                                            navController.navigate(Routes.drinks(category))
                                        }
                                    )
                                    NavigationItem.Fav -> { }
                                }
                            }
                        }
                        composable(
                            route = Routes.DRINKS,
                            arguments = listOf(navArgument("category") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val category = backStackEntry.arguments?.getString("category") ?: ""
                            DrinksScreen(
                                modifier = Modifier.padding(innerPadding),
                                category = category,
                                snackbarHostState = snackbarHostState
                            )
                        }
                    }
                }
            }
        }
    }
}











/*
@Composable
fun MonImage() {
    Image(
        painter = painterResource(id = R.drawable.cocktails),
        contentDescription = "Les Cocktails",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}


@Composable
fun MonBouton() {
    Button(onClick = {
        println("Le bouton a été cliqué!")
    }) {
        Text("Mon Bouton")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Row {
            Text("first !")
            Text(
                text = " Hello $name!",
                color = Color.Red,
                fontSize = 28.sp
            )
        }

        Text("Coucou")
        Row {
            Text("Ligne 3")
            Text(
                text = " Partie 2",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right,
                modifier = Modifier.fillMaxWidth()
            )
        }
        MonImage()
        MonBouton()
    }

}
 */