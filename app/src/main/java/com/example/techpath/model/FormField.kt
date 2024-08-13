package com.example.techpath.model

import androidx.compose.ui.text.input.ImeAction

data class FormField(
    val label: String,
    val placeholder: String,
    val imeAction: ImeAction,
    val onNext: () -> Unit
)
