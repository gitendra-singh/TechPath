package com.example.techpath.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techpath.model.TechPathRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TechPathViewModel : ViewModel() {
    private val _generatedText = MutableStateFlow("")
    val generatedText: StateFlow<String> = _generatedText

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun generateText(
        areaOfInterest: String,
        skillsAlreadyHave: String,
        currentEducationLevel: String,
        priorWorkExperience: String,
        workEnvironment: String,
        learningPreference: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val text = TechPathRepository.generateCareerPath(
                    areaOfInterest,
                    skillsAlreadyHave,
                    currentEducationLevel,
                    priorWorkExperience,
                    workEnvironment,
                    learningPreference
                )
                _generatedText.value = text
            } catch (e: Exception){
                _generatedText.value = "Failed to generate career path. Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }
}