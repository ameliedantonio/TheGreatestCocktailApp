package fr.isen.amelie.thegreatestcocktailapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    fun getRandomCocktail(): Call<Drinks>

    @GET("list.php?c=list")
    fun getListCategory(): Call<Drinks>

    @GET("filter.php")
    fun getDrinksByCategory(@Query(value="c") category: String): Call<Drinks>
}