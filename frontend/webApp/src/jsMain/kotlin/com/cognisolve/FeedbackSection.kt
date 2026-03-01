package com.cognisolve

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.cognisolve.models.FeedbackResponse
import com.cognisolve.ui.theme.varVariable

@Composable
fun FeedbackSection(response: FeedbackResponse) {
    Div({
        sectionContainerStyle("--md-sys-color-tertiary-container")
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
                    color(varVariable("--md-sys-color-on-surface"))
                    fontSize(22.px)
                    fontWeight("600")
                }
            }) { Text("Feedback") }
            
            // Score Badge
            Span({
                style {
                    val badgeColor = if (response.isCorrect) "#4CAF50" else "#F44336" // Simplified status colors for MVP
                    backgroundColor(Color(badgeColor))
                    color(Color.white)
                    padding(6.px, 12.px)
                    borderRadius(16.px)
                    fontSize(14.px)
                    fontWeight("bold")
                }
            }) {
                Text(if (response.isCorrect) "Correct!" else "Needs Work")
            }
        }

        // Main Feedback Message
        P({
            style {
                color(varVariable("--md-sys-color-on-surface"))
                fontSize(18.px)
                lineHeight("1.6")
                margin(0.px, 0.px, 16.px, 0.px)
            }
        }) {
            Text(response.feedbackMessage)
        }

        // Conditionally render re-explanation if they got it wrong
        response.reExplanation?.let { rex ->
            Div({
                style {
                    backgroundColor(varVariable("--md-sys-color-surface"))
                    padding(16.px)
                    borderRadius(12.px)
                    property("border-left", "4px solid var(--md-sys-color-tertiary-container)")
                    marginBottom(16.px)
                }
            }) {
                H4({
                    style {
                        margin(0.px, 0.px, 8.px, 0.px)
                        color(varVariable("--md-sys-color-on-surface"))
                    }
                }) { Text("Let's try that again:") }
                P({
                    style {
                        margin(0.px)
                        color(varVariable("--md-sys-color-on-surface"))
                        fontSize(15.px)
                        lineHeight("1.5")
                    }
                }) { Text(rex) }
            }
        }

        // Encouragement
        P({
            style {
                margin(0.px)
                color(varVariable("--md-sys-color-on-surface"))
                fontSize(14.px)
                fontWeight("500")
                fontStyle("italic")
                textAlign("center")
            }
        }) {
            Text(response.encouragement)
        }
    }
}
