package com.example.rusttutor.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.rusttutor.ui.step1.Step1Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Basics", "Quizzes", "Advanced", "Async")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Rust Tutor",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            // Simple text icon as fallback since we don't have vector icons set up
                            Text(
                                text = item.take(1),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            when (selectedItem) {
                0 -> Step1Screen()
                1 -> PlaceholderScreen("Step 2: Pattern Matching & Errors")
                2 -> PlaceholderScreen("Step 3: Traits, Generics & Lifetimes")
                3 -> PlaceholderScreen("Step 4: Concurrency & Async")
            }
        }
    }
}
