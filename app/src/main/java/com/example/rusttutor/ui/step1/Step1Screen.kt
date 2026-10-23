package com.example.rusttutor.ui.step1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Step1Screen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Text(
                text = "Rust Basics",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Welcome to Rust! Let's explore some foundational concepts: Mutability, Shadowing, and Ownership.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        item {
            ExpandableConceptCard(
                title = "Mutability",
                description = "By default, variables in Rust are immutable. You must use the `mut` keyword to make them mutable.",
                codeSnippet = """
                    // Immutable by default
                    let x = 5;
                    // x = 6; // ERROR!

                    // Mutable
                    let mut y = 5;
                    y = 6; // OK!
                """.trimIndent()
            )
        }

        item {
            ExpandableConceptCard(
                title = "Shadowing",
                description = "You can declare a new variable with the same name as a previous variable. This 'shadows' the previous one, even allowing you to change its type.",
                codeSnippet = """
                    let spaces = "   "; // Type: &str
                    let spaces = spaces.len(); // Type: usize
                    // We just changed the type without needing a new name!
                """.trimIndent()
            )
        }

        item {
            ExpandableConceptCard(
                title = "Ownership",
                description = "Rust's most unique feature. Each value has a single 'owner'. When the owner goes out of scope, the value is dropped.",
                codeSnippet = """
                    {
                        let s = String::from("hello"); // s is valid
                        // do stuff with s
                    } // scope ends, s is dropped

                    let s1 = String::from("hello");
                    let s2 = s1; // s1 is moved to s2. s1 is no longer valid!
                    // println!("{}", s1); // ERROR!
                """.trimIndent()
            )
        }
    }
}

@Composable
fun ExpandableConceptCard(title: String, description: String, codeSnippet: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CodeSnippetViewer(code = codeSnippet)
                }
            }
        }
    }
}

@Composable
fun CodeSnippetViewer(code: String) {
    val keywordColor = MaterialTheme.colorScheme.primary
    val commentColor = Color.Gray
    val stringColor = Color(0xFF2E7D32) // A dark green for strings, assuming light mode fallback
    val textColor = MaterialTheme.colorScheme.onSurface

    val annotatedString = buildAnnotatedString {
        val lines = code.split("\n")
        lines.forEachIndexed { index, line ->
            if (line.trim().startsWith("//")) {
                withStyle(style = SpanStyle(color = commentColor)) {
                    append(line)
                }
            } else {
                val words = line.split(Regex("(?<=\\b|\\W)|(?=\\b|\\W)"))
                var inString = false
                for (word in words) {
                    if (word == "\"") {
                        inString = !inString
                        withStyle(style = SpanStyle(color = stringColor)) {
                            append(word)
                        }
                    } else if (inString) {
                        withStyle(style = SpanStyle(color = stringColor)) {
                            append(word)
                        }
                    } else if (word in listOf("let", "mut", "fn", "String::from", "usize", "&str")) {
                        withStyle(style = SpanStyle(color = keywordColor, fontWeight = FontWeight.Bold)) {
                            append(word)
                        }
                    } else {
                        withStyle(style = SpanStyle(color = textColor)) {
                            append(word)
                        }
                    }
                }
            }
            if (index < lines.size - 1) append("\n")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(
            text = annotatedString,
            fontFamily = FontFamily.Monospace,
            fontSize = 14.sp
        )
    }
}
