package com.cognisolve

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.attributes.*
import com.cognisolve.models.PracticeResponse
import com.cognisolve.models.PracticeQuestion
import com.cognisolve.ui.theme.varVariable

@Composable
fun PracticeSection(
    response: PracticeResponse,
    onSubmitAnswer: (question: String, learnerAnswer: String, correctAnswer: String) -> Unit
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var currentAnswer by remember { mutableStateOf("") }
    
    val questionList = response.questions
    if (questionList.isEmpty()) return
    
    val question = questionList[currentQuestionIndex]

    Div({
        sectionContainerStyle("--md-sys-color-primary-container")
    }) {
        // Header
        Div({
            style {
                display(DisplayStyle.Flex)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.SpaceBetween)
                marginBottom(12.px)
            }
        }) {
            H2({ 
                style {
                    margin(0.px)
                    color(varVariable("--md-sys-color-on-surface")) // Usually dark text on primary container
                    fontSize(22.px)
                    fontWeight("600")
                }
            }) { Text("Micro-Practice") }
            
            Span({
                style {
                    color(varVariable("--md-sys-color-primary"))
                    fontSize(14.px)
                    fontWeight("bold")
                }
            }) {
                Text("Question ${currentQuestionIndex + 1} of ${questionList.size}")
            }
        }

        // Question Text
        P({
            style {
                fontSize(18.px)
                fontWeight("500")
                margin(0.px, 0.px, 16.px, 0.px)
                color(varVariable("--md-sys-color-on-surface"))
            }
        }) {
            Text(question.question)
        }

        // Answer Input (MCQ vs Short Answer)
        val options = question.options
        if (question.questionType == "mcq" && options != null && options.isNotEmpty()) {
            Div({
                style {
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Column)
                    gap(8.px)
                }
            }) {
                options.forEach { option ->
                    val isSelected = currentAnswer == option
                    Div({
                        onClick { currentAnswer = option }
                        style {
                            padding(12.px)
                            borderRadius(8.px)
                            border {
                                width = 2.px
                                style = LineStyle.Solid
                                color = if (isSelected) varVariable("--md-sys-color-primary") else varVariable("--md-sys-color-outline")
                            }
                            backgroundColor(if (isSelected) varVariable("--md-sys-color-surface") else Color.transparent)
                            cursor("pointer")
                            property("transition", "all 0.2s")
                        }
                    }) {
                        Text(option)
                    }
                }
            }
        } else {
            // Short Answer
            TextArea(currentAnswer) {
                placeholder("Type your answer here...")
                onInput { currentAnswer = it.value }
                style {
                    textInputStyle()
                    minHeight(80.px)
                    property("resize", "vertical")
                }
            }
        }

        // Submit Action
        Div({
            style {
                display(DisplayStyle.Flex)
                justifyContent(JustifyContent.FlexEnd)
                marginTop(16.px)
            }
        }) {
            Button(attrs = {
                onClick { 
                    onSubmitAnswer(question.question, currentAnswer, question.correctAnswer) 
                    // Move to next question immediately inside the UI for prototype
                    if (currentQuestionIndex < questionList.size - 1) {
                        currentQuestionIndex++
                        currentAnswer = ""
                    }
                }
                if (currentAnswer.isBlank()) disabled()
                style { primaryButtonStyle(currentAnswer.isBlank()) }
            }) {
                Text("Check Answer")
            }
        }
    }
}
