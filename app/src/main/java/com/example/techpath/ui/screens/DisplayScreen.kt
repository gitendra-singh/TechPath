package com.example.techpath.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.techpath.ui.components.AppTopBar
import com.example.techpath.viewModel.TechPathViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Display(navController: NavController, viewModel: TechPathViewModel = viewModel()) {
    val generatedText by viewModel.generatedText.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isLoading by viewModel.isLoading.collectAsState()
    Log.d("Navigation", "Display screen created")

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(Color.LightGray),
        topBar = { AppTopBar() }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Keeps the padding from the scaffold
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 0.dp) // Reduced vertical padding
                ) {
                    item {
                        Text(
                            text = "TechPath:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth() // Ensure it spans full width
                                .padding(top = 8.dp, bottom = 4.dp) // Reduced bottom padding
                        )
                    }
                    items(generateAnnotatedText(generatedText)) { step ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth() // Ensure it spans full width
                                .padding(bottom = 8.dp), // Adjust the bottom padding to reduce space between items
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            val uriHandler = LocalUriHandler.current
                            ClickableText(
                                text = step,
                                modifier = Modifier.padding(8.dp), // Reduced padding for a more compact layout
                                onClick = { offset ->
                                    step.getStringAnnotations(tag = "URL", start = offset, end = offset)
                                        .firstOrNull()?.let { annotation ->
                                            uriHandler.openUri(annotation.item)
                                        }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
fun generateAnnotatedText(text: String): List<AnnotatedString> {
    val sections = text.split("\n")
    val annotatedTexts = mutableListOf<AnnotatedString>()

    for (section in sections) {
        val annotatedString = buildAnnotatedString {
            val regex = "\\*([^*]+):".toRegex() // Match patterns like *Text:
            val urlRegex = "(https?://[\\w.-]+)".toRegex() // Match URLs
            var lastIndex = 0

            urlRegex.findAll(section).forEach { matchResult ->
                val matchStart = matchResult.range.first
                val matchEnd = matchResult.range.last + 1

                val textBeforeUrl = section.substring(lastIndex, matchStart)
                regex.findAll(textBeforeUrl).forEach { boldMatch ->
                    val boldMatchStart = boldMatch.range.first
                    val boldMatchEnd = boldMatch.range.last + 1

                    if (boldMatchStart >= 0 && boldMatchStart <= textBeforeUrl.length) {
                        append(textBeforeUrl.substring(lastIndex, boldMatchStart).replace("*", ""))
                    }
                    if (boldMatchEnd > boldMatchStart && boldMatchEnd <= textBeforeUrl.length) {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(boldMatch.value.replace("*", ""))
                        }
                    }
                    lastIndex = boldMatchEnd
                }

                if (matchStart >= 0 && matchStart <= section.length) {
                    append(section.substring(lastIndex, matchStart).replace("*", ""))
                    pushStringAnnotation(tag = "URL", annotation = matchResult.value)
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append(matchResult.value)
                    }
                    pop()
                    lastIndex = matchEnd
                }
            }


            val remainingText = section.substring(lastIndex)
            regex.findAll(remainingText).forEach { boldMatch ->
                val boldMatchStart = boldMatch.range.first
                val boldMatchEnd = boldMatch.range.last + 1

               
                if (boldMatchStart >= 0 && boldMatchStart <= remainingText.length) {
                    append(remainingText.substring(lastIndex, boldMatchStart).replace("*", ""))
                }
                if (boldMatchEnd > boldMatchStart && boldMatchEnd <= remainingText.length) {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(boldMatch.value.replace("*", ""))
                    }
                }
                lastIndex = boldMatchEnd
            }

            if (lastIndex <= remainingText.length) {
                append(remainingText.substring(lastIndex).replace("*", ""))
            }
        }
        annotatedTexts.add(annotatedString)
    }

    return annotatedTexts
}

@Preview(showBackground = true)
@Composable
fun DisplayPreview() {
    val navController = rememberNavController()
    Display(navController = navController)
}
