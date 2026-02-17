package fr.isen.amelie.thegreatestcocktailapp.network

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("random.php")
    fun getRandomCocktail(): Call<Drinks>

}