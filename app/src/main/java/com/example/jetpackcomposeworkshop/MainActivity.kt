package com.example.jetpackcomposeworkshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeworkshop.ui.theme.JetpackComposeWorkshopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeWorkshopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipeScreen()
                }
            }
        }
    }
}

@Composable
fun RecipeScreen() {
    var recipesList by remember {
        mutableStateOf(getRecipes())
    }
    LazyColumn {
        itemsIndexed(recipesList) { index, recipe ->
            val selectMeal = {
                recipesList = recipesList.toMutableList().also { recipeList ->
                    recipeList[index] = recipe.copy(isSelected = !recipe.isSelected)
                }
            }
            val cardClicked = {
                recipesList = recipesList.toMutableList().also { recipeList ->
                    recipeList[index] = recipe.copy(isExpanded = !recipe.isExpanded)
                }
            }
            val toggleFav = {
                recipesList = recipesList.toMutableList().also { recipeList ->
                    recipeList[index] = recipe.copy(isFavourite = !recipe.isFavourite)
                }
            }
            RecipeCard(recipe, selectMeal, cardClicked, toggleFav)
        }
    }
}

@Preview
@Composable
fun RecipeScreenPreview() {
    RecipeScreen()
}

@Composable
fun RecipeTitle() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Vegemite toast", fontWeight = FontWeight.SemiBold, fontSize = 26.sp)
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "Toast with vegemite", fontWeight = FontWeight.Light)
    }
}

@Composable
fun RecipeImage(isFav: Boolean, toggleFav: () -> Unit) {
    Box {
        Image(
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.recipe_image),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        IconButton(
            onClick = toggleFav, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clip(CircleShape)
                .background(
                    Color.LightGray.copy(alpha = 0.8f)
                )
        ) {
            if (isFav) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                )
            }

        }
    }
}

@Composable
fun RecipeCTAs(isSelected: Boolean, cardClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(onClick = { }) {
            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null)
            Text(text = "Nutrition")
        }
        Button(
            onClick = cardClicked,
            colors = if (isSelected) ButtonDefaults.buttonColors(backgroundColor = Color.Green) else ButtonDefaults.buttonColors(
                backgroundColor = Color.Red
            )
        ) {
            if (isSelected) {
                Text(text = "Remove meal")
            } else {
                Text(text = "Add meal")
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    mealSelected: () -> Unit,
    cardClicked: () -> Unit,
    toggleFav: () -> Unit
) {
    val border = if (recipe.isSelected) BorderStroke(width = 2.dp, Color.Green) else null
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .clickable { cardClicked() },
        border = border
    ) {
        Column {
            RecipeImage(recipe.isFavourite, toggleFav)
            RecipeTitle()
            RecipeInfo()
            if (recipe.isExpanded) {
                IngredientList()
            }
            RecipeCTAs(recipe.isSelected, mealSelected)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IngredientList() {
    val ingredients = listOf(
        Ingredient("Beef", "400 g"), Ingredient("Carrot", "1"),
        Ingredient("Onion", "3"), Ingredient("Potato", "4"),
        Ingredient("Tomato paste", "2 sp")
    )

    Card(border = BorderStroke(2.dp, Color.Green)) {
        Column(Modifier.fillMaxWidth()) {
            ingredients.forEach {
                Text(modifier = Modifier.fillMaxWidth(), text = "${it.item} ${it.quantity}")
            }
        }
    }
}


data class Ingredient(val item: String, val quantity: String)

@Composable
fun RecipeInfo() {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RecipePrepTime()
        Divider(
            Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.LightGray,
        )
        RecipePrepType()
        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color.Red)
        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.Blue)
        RecipeTags()
    }
}

@Composable
fun RecipeTags() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(8.dp)) {
        Text(text = "Family friendly")
        Text(text = "Easy to cook")
        Text(text = "Low carb")
    }
}

@Composable
fun RecipePrepType() {
    TextButton(
        onClick = { /*TODO*/ }, modifier = Modifier
            .padding(8.dp, 8.dp, 16.dp, 8.dp)
            .clip(RoundedCornerShape(999.dp))
            .background(
                Color.Green
            )

    ) {
        Text(text = "Quick prep", color = Color.Black)
    }
}


@Composable
fun RecipePrepTime() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
        Icon(imageVector = Icons.Filled.Info, contentDescription = null)
        Text("40 min", fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeInfoPreview() {
    RecipeInfo()
}

@Preview(showBackground = true)
@Composable
fun RecipePrepTimePreview() {
    RecipePrepTime()
}

@Preview(showBackground = true)
@Composable
fun RecipePrepTypePreview() {
    RecipePrepType()
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPReview() {
    RecipeCard(getRecipes()[0], { }, {}, {})
}

data class Recipe(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
    val isSelected: Boolean = false,
    val isFavourite: Boolean = false,
    val isExpanded: Boolean = false
)

fun getRecipes() = listOf(
    Recipe("Recipe name 1", "Description 1", R.drawable.recipe_image),
    Recipe("Recipe name 2", "Description 2", R.drawable.recipe_image),
    Recipe("Recipe name 3", "Description 3", R.drawable.recipe_image),
    Recipe("Recipe name 4", "Description 4", R.drawable.recipe_image),
    Recipe("Recipe name 5", "Description 5", R.drawable.recipe_image),
    Recipe("Recipe name 6", "Description 6", R.drawable.recipe_image)
)
