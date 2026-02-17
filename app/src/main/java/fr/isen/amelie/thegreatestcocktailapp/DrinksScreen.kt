package fr.isen.amelie.thegreatestcocktailapp

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrinksScreen(modifier: Modifier, category: String) {
    val drinks : List<String> = listOf(
        "Mojito",
        "Negroni",
        "Blue Lagoon",
        "Cuba Libre"
    )

    LazyColumn(modifier
        .fillMaxSize()
        .background(brush = Brush.linearGradient(colors = listOf(Color.Cyan, Color.Black)))
        .padding(all= 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)) {
        item {
            Text(text = category, color = Color.White, fontSize = 50.sp)
        }

        items(items=drinks) { drink ->
            Button (onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(size = 25.dp),
                colors = ButtonColors(
                    containerColor = Color.White.copy(alpha = 0.3f),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                )
            ) {
                Text(text = drink, fontSize = 30.sp)
            }
        }
    }
}