package com.cognisolve

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.attributes.*
import com.cognisolve.ui.theme.varVariable
import com.cognisolve.ui.theme.ThemeState
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
            minHeight(100.vh)
            paddingBottom(64.px)
        }
    }) {
        // App Header with dark mode toggle
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
            is UiState.Error -> ErrorState(state.message) { viewModel.retryLastAction() }
            is UiState.Success -> {
                val successState = state as UiState.Success<com.cognisolve.models.ExplainResponse>
                AnimatedSection {
                    DiagnosisSection(successState.data)
                }
                
                AnimatedSection(delay = "0.15s") {
                    ExplanationSection(
                        response = successState.data,
                        onRequestPractice = { viewModel.requestPractice() }
                    )
                }
                
                // Once practice is requested, show that section
                val practiceState by viewModel.practiceState.collectAsState<UiState<com.cognisolve.models.PracticeResponse>>()
                
                when (val pState = practiceState) {
                    is UiState.Idle -> { /* Show nothing */ }
                    is UiState.Loading -> LoadingState("Generating practice questions...")
                    is UiState.Error -> ErrorState(pState.message) { viewModel.requestPractice() }
                    is UiState.Success -> {
                        val pSuccessState = pState as UiState.Success<com.cognisolve.models.PracticeResponse>
                        AnimatedSection {
                            PracticeSection(
                                response = pSuccessState.data,
                                onSubmitAnswer = { question, learnerAnswer, correctAnswer ->
                                    viewModel.submitFeedback(question, learnerAnswer, correctAnswer)
                                }
                            )
                        }
                        
                        val feedbackState by viewModel.feedbackState.collectAsState<UiState<com.cognisolve.models.FeedbackResponse>>()
                        when(val fState = feedbackState) {
                            is UiState.Idle -> { /* Nothing yet */ }
                            is UiState.Loading -> LoadingState("Evaluating answer...")
                            is UiState.Error -> ErrorState(fState.message)
                            is UiState.Success -> {
                                val fSuccessState = fState as UiState.Success<com.cognisolve.models.FeedbackResponse>
                                AnimatedSection {
                                    FeedbackSection(fSuccessState.data)
                                }
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
            display(DisplayStyle.Flex)
            justifyContent(JustifyContent.SpaceBetween)
            alignItems(AlignItems.Center)
            marginBottom(8.px)
            property("animation", "fadeIn 0.6s ease-out")
        }
    }) {
        // Left: Logo + Tagline
        Div({
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
            }
        }) {
            H1({
                style {
                    color(varVariable("--md-sys-color-primary"))
                    fontSize(36.px)
                    fontWeight("800")
                    margin(0.px, 0.px, 4.px, 0.px)
                    letterSpacing((-0.5).px)
                }
            }) {
                Text("Cognisolve")
            }
            P({
                style {
                    color(varVariable("--md-sys-color-on-surface"))
                    fontSize(16.px)
                    margin(0.px)
                    opacity(0.7)
                }
            }) {
                Text("AI tutor for conceptual confusion")
            }
        }

        // Right: Dark Mode Toggle
        Button(attrs = {
            id("dark-mode-toggle")
            onClick { ThemeState.isDark = !ThemeState.isDark }
            style {
                backgroundColor(Color.transparent)
                border { width = 0.px }
                cursor("pointer")
                fontSize(24.px)
                padding(8.px)
                borderRadius(50.percent)
                property("transition", "transform 0.3s ease, background-color 0.2s")
            }
            title(if (ThemeState.isDark) "Switch to light mode" else "Switch to dark mode")
        }) {
            Text(if (ThemeState.isDark) "☀️" else "🌙")
        }
    }
}

/** Wrapper that applies a fadeInUp animation to content sections */
@Composable
fun AnimatedSection(delay: String = "0s", content: @Composable () -> Unit) {
    Div({
        style {
            property("animation", "fadeInUp 0.4s ease-out $delay both")
        }
    }) {
        content()
    }
}
