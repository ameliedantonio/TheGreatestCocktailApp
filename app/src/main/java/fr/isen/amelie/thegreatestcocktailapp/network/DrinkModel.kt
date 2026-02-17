package fr.isen.amelie.thegreatestcocktailapp.network

import com.google.gson.annotations.SerializedName
import java.io.Serial
import java.io.Serializable

class Drinks(
    val drinks: List<DrinkModel>
): Serializable

class DrinkModel (
    @SerializedName(value= "idDrink") val id: String = ""
): Serializable