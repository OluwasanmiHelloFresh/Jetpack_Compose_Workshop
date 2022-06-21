package com.example.jetpackcomposeworkshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeworkshop.ui.theme.JetpackComposeWorkshopTheme
import com.example.jetpackcomposeworkshop.ui.theme.Typography

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

                }
            }
        }
    }
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
fun RecipeImage() {
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
            onClick = { /*TODO*/ }, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clip(CircleShape)
                .background(
                    Color.LightGray.copy(alpha = 0.8f)
                )
        ) {
            Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null)
        }
    }
}

@Composable
fun RecipeCTAs() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        TextButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null)
            Text(text = "Nutrition")
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add meal")
        }
    }
}

@Composable
fun RecipeCard() {
    Column() {
        RecipeImage()
        RecipeTitle()
        RecipeCTAs()
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPReview() {
    RecipeCard()
}

