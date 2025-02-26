package com.example.auevent.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.auevent.R
import com.example.auevent.viewmodel.HomeViewModel

@Composable
fun CategoryPage(navController: NavController, homeViewModel: HomeViewModel) {
    val categories = listOf(
        Category("Social Activities", R.drawable.ic_social),
        Category("Travel and Outdoor", R.drawable.ic_travel),
        Category("Health and Wellbeing", R.drawable.ic_health),
        Category("Hobbies and Passions", R.drawable.ic_hobbies)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Categories",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategoryItemOnCategoryPage(category, navController)
            }
        }
    }
}

@Composable
fun CategoryItemOnCategoryPage(category: Category, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(CircleShape)
            .clickable { navController.navigate("categoryEventsPage/${category.name}") } // Navigate with category name
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = category.pictureRes),
            contentDescription = "Category Image",
            modifier = Modifier.size(50.dp)
        )
        Text(text = category.name, fontSize = 12.sp)
    }
}