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
import androidx.compose.ui.res.colorResource
import androidx.compose.material.icons.filled.Search
import fr.isen.amelie.thegreatestcocktailapp.R
import fr.isen.amelie.thegreatestcocktailapp.screens.SearchScreen
import fr.isen.amelie.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.amelie.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.amelie.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import fr.isen.amelie.thegreatestcocktailapp.screens.DrinksScreen
import android.net.Uri
import fr.isen.amelie.thegreatestcocktailapp.screens.FavoriteScreen


enum class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
) {
    Home(title="Featured", Icons.Default.Home, route="home"),
    List(title="Categories", Icons.Default.Menu, route="list"),
    Search(title="Search", Icons.Default.Search, route="search"),
    Fav(title="Favorites", Icons.Default.Favorite, route="fav"),
}

// Pour avoir la bottombar sur la page DrinksScreen
object Routes {
    const val DRINKS = "drinks/{category}"
    fun drinks(category: String) = "drinks/${Uri.encode(category)}"
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
                            containerColor = colorResource(id = R.color.bordeaux)
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
                                        selectedIconColor = Color.White,
                                        selectedTextColor = colorResource(id = R.color.rose),
                                        unselectedIconColor = colorResource(id = R.color.rose),
                                        unselectedTextColor = colorResource(id = R.color.rose),
                                        indicatorColor = colorResource(id = R.color.rose)
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startNavigationItem.route
                    ) {
                        // La bottombar (4 boutons)
                        NavigationItem.entries.forEach { navigationItem ->
                            composable (navigationItem.route) {
                                when (navigationItem) {
                                    NavigationItem.Home -> DetailCocktailScreen(
                                        Modifier.padding(paddingValues = innerPadding),
                                        snackbarHostState = snackbarHostState,
                                        navController = navController,
                                        showBackButton = false
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
                                    NavigationItem.Search -> {
                                        SearchScreen(
                                            modifier = Modifier.padding(innerPadding),
                                            snackbarHostState = snackbarHostState
                                        )
                                    }
                                    NavigationItem.Fav -> {
                                        FavoriteScreen(
                                                modifier = Modifier.padding(innerPadding),
                                                snackbarHostState = snackbarHostState,
                                                onAddFavoriteClick = {
                                                    navController.navigate(NavigationItem.List.route)
                                                    currentNavigationItem.value = NavigationItem.List
                                                }
                                        )
                                    }
                                }
                            }
                        }
                        composable(
                            route = Routes.DRINKS,
                            arguments = listOf(navArgument("category") { type = NavType.StringType })
                        ) { backStackEntry ->

                            val categoryEncoded = backStackEntry.arguments?.getString("category") ?: ""
                            val category = android.net.Uri.decode(categoryEncoded)

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



