package com.cognisolve

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.attributes.*
import com.cognisolve.ui.theme.varVariable
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.collectAsState
import com.cognisolve.viewmodel.CognisolveViewModel
import com.cognisolve.viewmodel.UiState

@Composable
fun MainScreen(viewModel: CognisolveViewModel = remember { CognisolveViewModel() }) {
    
    val explainState by viewModel.explainState.collectAsState<UiState<com.cognisolve.models.ExplainResponse>>()
    
    Div({
        style {
            padding(24.px)
            maxWidth(800.px)
            property("margin", "0 auto")
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            gap(24.px)
            fontFamily("Inter", "Roboto", "sans-serif")
        }
    }) {
        // App Header
        HeaderSection()
        
        // Input Section
        InputSection(
            isLoading = explainState is UiState.Loading,
            onSubmit = { concept, doubt, code -> 
                viewModel.submitQuestion(concept, doubt, code)
            }
        )
        
        // Diagnosis & Explanation Section
        when (val state = explainState) {
            is UiState.Idle -> { /* Show nothing yet */ }
            is UiState.Loading -> LoadingState("Analyzing confusion type...")
            is UiState.Error -> ErrorState(state.message)
            is UiState.Success -> {
                val successState = state as UiState.Success<com.cognisolve.models.ExplainResponse>
                DiagnosisSection(successState.data)
                
                ExplanationSection(
                    response = successState.data,
                    onRequestPractice = { viewModel.requestPractice() }
                )
                
                // Once practice is requested, show that section
                val practiceState by viewModel.practiceState.collectAsState<UiState<com.cognisolve.models.PracticeResponse>>()
                
                when (val pState = practiceState) {
                    is UiState.Idle -> { /* Show nothing */ }
                    is UiState.Loading -> LoadingState("Generating practice questions...")
                    is UiState.Error -> ErrorState(pState.message)
                    is UiState.Success -> {
                        val pSuccessState = pState as UiState.Success<com.cognisolve.models.PracticeResponse>
                        PracticeSection(
                            response = pSuccessState.data,
                            onSubmitAnswer = { question, learnerAnswer, correctAnswer ->
                                viewModel.submitFeedback(question, learnerAnswer, correctAnswer)
                            }
                        )
                        
                        val feedbackState by viewModel.feedbackState.collectAsState<UiState<com.cognisolve.models.FeedbackResponse>>()
                        when(val fState = feedbackState) {
                            is UiState.Idle -> { /* Nothing yet */ }
                            is UiState.Loading -> LoadingState("Evaluating answer...")
                            is UiState.Error -> ErrorState(fState.message)
                            is UiState.Success -> {
                                val fSuccessState = fState as UiState.Success<com.cognisolve.models.FeedbackResponse>
                                FeedbackSection(fSuccessState.data)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Div({
        style {
            textAlign("center")
            marginBottom(16.px)
        }
    }) {
        H1({
            style {
                color(varVariable("--md-sys-color-primary"))
                fontSize(36.px)
                fontWeight("800")
                margin(0.px, 0.px, 8.px, 0.px)
            }
        }) {
            Text("Cognisolve")
        }
        P({
            style {
                color(varVariable("--md-sys-color-on-surface"))
                fontSize(18.px)
                margin(0.px)
                opacity(0.8)
            }
        }) {
            Text("Explain like I'm stuck: AI tutor for conceptual confusion")
        }
    }
}
