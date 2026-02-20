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


/*
    // Obligatoires
    @SerializedName(value = "idDrink") val idDrink: String = "",
    @SerializedName(value = "strDrink") val strDrink: String = "",
    @SerializedName(value = "strCategory") val strCategory: String = "",
    @SerializedName(value = "strAlcoholic") val strAlcoholic: String = "",
    @SerializedName(value = "strGlass") val strGlass: String = "",
    @SerializedName(value = "strInstructions") val strInstructions: String = "",

    @SerializedName(value = "strIngredient1") val strIngredient1: String = "",
    @SerializedName(value = "strIngredient2") val strIngredient2: String = "",

    @SerializedName(value = "strMeasure1") val strMeasure1: String = "",
    @SerializedName(value = "strMeasure2") val strMeasure2: String = "",

    // Optionnels
    @SerializedName(value = "strIngredient3") val strIngredient3: String? = null,
    @SerializedName(value = "strIngredient4") val strIngredient4: String? = null,
    @SerializedName(value = "strIngredient5") val strIngredient5: String? = null,
    @SerializedName(value = "strIngredient6") val strIngredient6: String? = null,
    @SerializedName(value = "strIngredient7") val strIngredient7: String? = null,
    @SerializedName(value = "strIngredient8") val strIngredient8: String? = null,
    @SerializedName(value = "strIngredient9") val strIngredient9: String? = null,
    @SerializedName(value = "strIngredient10") val strIngredient10: String? = null,
    @SerializedName(value = "strIngredient11") val strIngredient11: String? = null,
    @SerializedName(value = "strIngredient12") val strIngredient12: String? = null,
    @SerializedName(value = "strIngredient13") val strIngredient13: String? = null,
    @SerializedName(value = "strIngredient14") val strIngredient14: String? = null,
    @SerializedName(value = "strIngredient15") val strIngredient15: String? = null,

    @SerializedName(value = "strMeasure3") val strMeasure3: String? = null,
    @SerializedName(value = "strMeasure4") val strMeasure4: String? = null,
    @SerializedName(value = "strMeasure5") val strMeasure5: String? = null,
    @SerializedName(value = "strMeasure6") val strMeasure6: String? = null,
    @SerializedName(value = "strMeasure7") val strMeasure7: String? = null,
    @SerializedName(value = "strMeasure8") val strMeasure8: String? = null,
    @SerializedName(value = "strMeasure9") val strMeasure9: String? = null,
    @SerializedName(value = "strMeasure10") val strMeasure10: String? = null,
    @SerializedName(value = "strMeasure11") val strMeasure11: String? = null,
    @SerializedName(value = "strMeasure12") val strMeasure12: String? = null,
    @SerializedName(value = "strMeasure13") val strMeasure13: String? = null,
    @SerializedName(value = "strMeasure14") val strMeasure14: String? = null,
    @SerializedName(value = "strMeasure15") val strMeasure15: String? = null,

    // Autres
    @SerializedName(value = "strDrinkThumb") val strDrinkThumb: String? = null
 */