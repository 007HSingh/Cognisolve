package com.cognisolve.viewmodel

import com.cognisolve.models.*
import com.cognisolve.repository.CognisolveRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class CognisolveViewModel(
    private val repository: CognisolveRepository = CognisolveRepository(),
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    // Explanation State
    private val _explainState = MutableStateFlow<UiState<ExplainResponse>>(UiState.Idle)
    val explainState: StateFlow<UiState<ExplainResponse>> = _explainState.asStateFlow()

    // Practice State
    private val _practiceState = MutableStateFlow<UiState<PracticeResponse>>(UiState.Idle)
    val practiceState: StateFlow<UiState<PracticeResponse>> = _practiceState.asStateFlow()

    // Feedback State
    private val _feedbackState = MutableStateFlow<UiState<FeedbackResponse>>(UiState.Idle)
    val feedbackState: StateFlow<UiState<FeedbackResponse>> = _feedbackState.asStateFlow()

    // Store the last context for practice requests
    private var lastConcept: String? = null
    private var lastExplanation: String? = null
    private var lastConfusionType: ConfusionType? = null

    fun submitQuestion(concept: String, doubt: String, codeSnippet: String? = null) {
        if (concept.isBlank() || doubt.isBlank()) {
            _explainState.value = UiState.Error("Concept and doubt cannot be empty")
            return
        }

        _explainState.value = UiState.Loading
        _practiceState.value = UiState.Idle
        _feedbackState.value = UiState.Idle

        coroutineScope.launch {
            val request = ExplainRequest(
                concept = concept,
                userDoubt = doubt,
                codeSnippet = codeSnippet?.takeIf { it.isNotBlank() }
            )
            
            repository.explain(request).fold(
                onSuccess = { response -> 
                    lastConcept = response.concept
                    lastExplanation = response.explanation
                    lastConfusionType = response.confusionType
                    _explainState.value = UiState.Success(response) 
                },
                onFailure = { error -> 
                    _explainState.value = UiState.Error(error.message ?: "Failed to get explanation")
                }
            )
        }
    }

    fun requestPractice() {
        val concept = lastConcept
        val explanation = lastExplanation
        val confusionType = lastConfusionType

        if (concept == null || explanation == null || confusionType == null) {
            _practiceState.value = UiState.Error("Cannot start practice without an explanation first")
            return
        }

        _practiceState.value = UiState.Loading
        _feedbackState.value = UiState.Idle

        coroutineScope.launch {
            val request = PracticeRequest(
                concept = concept,
                confusionType = confusionType,
                explanationGiven = explanation
            )

            repository.practice(request).fold(
                onSuccess = { response -> _practiceState.value = UiState.Success(response) },
                onFailure = { error -> _practiceState.value = UiState.Error(error.message ?: "Failed to generate practice questions") }
            )
        }
    }

    fun submitFeedback(question: String, learnerAnswer: String, correctAnswer: String) {
        val concept = lastConcept
        val confusionType = lastConfusionType

        if (concept == null || confusionType == null) {
            _feedbackState.value = UiState.Error("Missing context to submit feedback")
            return
        }

        if (learnerAnswer.isBlank()) {
            _feedbackState.value = UiState.Error("Answer cannot be empty")
            return
        }

        _feedbackState.value = UiState.Loading

        coroutineScope.launch {
            val request = FeedbackRequest(
                learnerId = "web_user", // Simplification for MVP
                concept = concept,
                question = question,
                learnerAnswer = learnerAnswer,
                correctAnswer = correctAnswer,
                confusionType = confusionType
            )

            repository.feedback(request).fold(
                onSuccess = { response -> _feedbackState.value = UiState.Success(response) },
                onFailure = { error -> _feedbackState.value = UiState.Error(error.message ?: "Failed to submit feedback") }
            )
        }
    }
}
