package com.example.techpath.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.techpath.model.FormField
import com.example.techpath.ui.components.AppTopBar
import com.example.techpath.viewModel.TechPathViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, viewModel: TechPathViewModel = viewModel()) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Log.d("Navigation", "Home screen created")
    val focusManager = LocalFocusManager.current

    val areaOfInterest = remember { mutableStateOf("") }
    val skillsAlreadyHave = remember { mutableStateOf("") }
    val currentEducationLevel = remember { mutableStateOf("") }
    val priorWorkExperience = remember { mutableStateOf("") }
    val workEnvironment = remember { mutableStateOf("") }
    val learningPreference = remember { mutableStateOf("") }

    val fields = listOf(
        FormField(
            label = "What do you want to become?",
            placeholder = "e.g., Android Developer, Web developer, Data Scientist",
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        FormField(
            label = "What technical skills do you already possess?",
            placeholder = "e.g., programming languages, software proficiency, hardware knowledge",
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        FormField(
            label = "What is your current level of education?",
            placeholder = "e.g., high school, bachelor's, master's",
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        FormField(
            label = "Do you have any prior work experience in a related field?",
            placeholder = "e.g., Beginner, Intermediate, Advanced",
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        FormField(
            label = "What kind of work environment do you prefer?",
            placeholder = "e.g., large corporation, startup, freelance, research",
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        FormField(
            label = "How do you prefer to learn?",
            placeholder = "e.g., online courses, traditional classes, self-study",
            imeAction = ImeAction.Done,
            onNext = { focusManager.clearFocus() }
        )
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AppTopBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            item {
                Text(
                    text = "Unlock personalized career paths. Fill in the details.",
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }

            items(fields.size) { index ->
                OutlinedTextField(
                    value = when (index) {
                        0 -> areaOfInterest.value
                        1 -> skillsAlreadyHave.value
                        2 -> currentEducationLevel.value
                        3 -> priorWorkExperience.value
                        4 -> workEnvironment.value
                        5 -> learningPreference.value
                        else -> ""
                    },
                    onValueChange = {
                        when (index) {
                            0 -> areaOfInterest.value = it
                            1 -> skillsAlreadyHave.value = it
                            2 -> currentEducationLevel.value = it
                            3 -> priorWorkExperience.value = it
                            4 -> workEnvironment.value = it
                            5 -> learningPreference.value = it
                        }
                    },
                    label = { Text(text = fields[index].label) },
                    placeholder = { Text(text = fields[index].placeholder) },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = fields[index].imeAction),
                    keyboardActions = KeyboardActions(onNext = { fields[index].onNext() }),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        viewModel.generateText(
                            areaOfInterest.value,
                            skillsAlreadyHave.value,
                            currentEducationLevel.value,
                            priorWorkExperience.value,
                            workEnvironment.value,
                            learningPreference.value
                        )
                        Log.d("Navigation", "Navigating to Display")
                        navController.navigate("display")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    LocalContext.current
    val navController = rememberNavController()
    Home(navController = navController)
}
