package fr.isen.amelie.thegreatestcocktailapp.network

import com.google.gson.annotations.SerializedName
import java.io.Serial
import java.io.Serializable
import java.net.URL

class Drinks(
    val drinks: List<DrinkModel>
): Serializable

data class DrinkModel (
    // Obligatoires
    @SerializedName(value= "idDrink") val id: String = "",
    @SerializedName(value = "strDrink") val name: String = "",
    @SerializedName(value = "strCategory") val category: String = "",
    @SerializedName(value = "strGlass") val glass: String = "",
    @SerializedName(value = "strAlcoholic") val alcoholic: String = "",

    @SerializedName(value = "strInstructions") val instructions: String = "",

    @SerializedName(value = "strIngredient1") val ingredient1: String = "",
    @SerializedName(value = "strIngredient2") val ingredient2: String = "",

    @SerializedName(value = "strMeasure1") val measure1: String = "",
    @SerializedName(value = "strMeasure2") val measure2: String = "",

    @SerializedName(value = "strDrinkThumb") val imageURL: String? = null,


    // Optionnels
    @SerializedName(value = "strIngredient3") val ingredient3: String? = null,
    @SerializedName(value = "strIngredient4") val ingredient4: String? = null,
    @SerializedName(value = "strIngredient5") val ingredient5: String? = null,
    @SerializedName(value = "strIngredient6") val ingredient6: String? = null,
    @SerializedName(value = "strIngredient7") val ingredient7: String? = null,
    @SerializedName(value = "strIngredient8") val ingredient8: String? = null,
    @SerializedName(value = "strIngredient9") val ingredient9: String? = null,
    @SerializedName(value = "strIngredient10") val ingredient10: String? = null,
    @SerializedName(value = "strIngredient11") val ingredient11: String? = null,
    @SerializedName(value = "strIngredient12") val ingredient12: String? = null,
    @SerializedName(value = "strIngredient13") val ingredient13: String? = null,
    @SerializedName(value = "strIngredient14") val ingredient14: String? = null,
    @SerializedName(value = "strIngredient15") val ingredient15: String? = null,

    @SerializedName(value = "strMeasure3") val measure3: String? = null,
    @SerializedName(value = "strMeasure4") val measure4: String? = null,
    @SerializedName(value = "strMeasure5") val measure5: String? = null,
    @SerializedName(value = "strMeasure6") val measure6: String? = null,
    @SerializedName(value = "strMeasure7") val measure7: String? = null,
    @SerializedName(value = "strMeasure8") val measure8: String? = null,
    @SerializedName(value = "strMeasure9") val measure9: String? = null,
    @SerializedName(value = "strMeasure10") val measure10: String? = null,
    @SerializedName(value = "strMeasure11") val measure11: String? = null,
    @SerializedName(value = "strMeasure12") val measure12: String? = null,
    @SerializedName(value = "strMeasure13") val measure13: String? = null,
    @SerializedName(value = "strMeasure14") val measure14: String? = null,
    @SerializedName(value = "strMeasure15") val measure15: String? = null,

): Serializable {

    fun getIngredients(): List<Pair<String, String>> {
        val list = listOf(
            ingredient1 to measure1,
            ingredient2 to measure2,
            (ingredient3 ?: "") to (measure3 ?: ""),
            (ingredient4 ?: "") to (measure4 ?: ""),
            (ingredient5 ?: "") to (measure5 ?: ""),
            (ingredient6 ?: "") to (measure6 ?: ""),
            (ingredient7 ?: "") to (measure7 ?: ""),
            (ingredient8 ?: "") to (measure8 ?: ""),
            (ingredient9 ?: "") to (measure9 ?: ""),
            (ingredient10 ?: "") to (measure10 ?: ""),
            (ingredient11 ?: "") to (measure11 ?: ""),
            (ingredient12 ?: "") to (measure12 ?: ""),
            (ingredient13 ?: "") to (measure13 ?: ""),
            (ingredient14 ?: "") to (measure14 ?: ""),
            (ingredient15 ?: "") to (measure15 ?: "")
        )

        return list
            .map { it.first.trim() to it.second.trim() }
            .filter { it.first.isNotEmpty() }
    }
}