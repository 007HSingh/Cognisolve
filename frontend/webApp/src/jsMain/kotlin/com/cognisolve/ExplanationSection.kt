package com.cognisolve

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.cognisolve.models.ExplainResponse
import com.cognisolve.models.ExplanationStrategy
import com.cognisolve.ui.theme.varVariable
import org.jetbrains.compose.web.attributes.AttrsScope

@Composable
fun ExplanationSection(
    response: ExplainResponse,
    onRequestPractice: () -> Unit
) {
    Div({
        sectionContainerStyle("--md-sys-color-surface-container")
    }) {
        // Header & Strategy Badge
        Div({
            style {
                display(DisplayStyle.Flex)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.SpaceBetween)
                marginBottom(16.px)
            }
        }) {
            H2({ 
                style {
                    margin(0.px)
                    color(varVariable("--md-sys-color-on-surface"))
                    fontSize(22.px)
                    fontWeight("600")
                }
            }) { Text("Explanation") }
            
            Span({
                style {
                    backgroundColor(varVariable("--md-sys-color-tertiary-container"))
                    color(varVariable("--md-sys-color-on-surface")) // Darker text on tertiary usually matches better
                    padding(6.px, 12.px)
                    borderRadius(16.px)
                    fontSize(12.px)
                    fontWeight("bold")
                    property("text-transform", "uppercase")
                }
            }) {
                Text(formatStrategyName(response.strategyUsed) + " STRATEGY")
            }
        }

        // Main Explanation
        P({ explanationTextStyle() }) {
            Text(response.explanation)
        }

        // Conditionally rendered parts
        response.analogy?.let {
            ContentBlock("💡 Analogy", it)
        }

        response.keyInsight?.let {
            ContentBlock("🔑 Key Insight", it)
        }

        response.commonMistake?.let {
            ContentBlock("⚠️ Common Mistake", it)
        }

        response.followUpHint?.let {
            ContentBlock("🧭 Follow-up", it)
        }

        // Action button to move to practice
        Div({
            style {
                display(DisplayStyle.Flex)
                justifyContent(JustifyContent.FlexEnd)
                marginTop(16.px)
            }
        }) {
            Button(attrs = {
                onClick { onRequestPractice() }
                style { 
                    primaryButtonStyle(false) 
                    property("background-color", "var(--md-sys-color-on-surface)")
                    property("color", "var(--md-sys-color-background)")
                }
            }) {
                Text("Test my understanding →")
            }
        }
    }
}

@Composable
fun ContentBlock(title: String, content: String) {
    Div({
        style {
            backgroundColor(varVariable("--md-sys-color-background")) // Inside surface container, so use background
            padding(16.px)
            borderRadius(8.px)
            marginTop(12.px)
            property("border-left", "4px solid var(--md-sys-color-primary)")
        }
    }) {
        H4({
            style {
                margin(0.px, 0.px, 8.px, 0.px)
                color(varVariable("--md-sys-color-primary"))
                fontSize(16.px)
            }
        }) {
            Text(title)
        }
        P({
            style {
                margin(0.px)
                color(varVariable("--md-sys-color-on-surface"))
                fontSize(15.px)
                lineHeight("1.5")
            }
        }) {
            Text(content)
        }
    }
}

fun formatStrategyName(strategy: ExplanationStrategy): String {
    return strategy.name.replace("_", " ")
}

fun AttrsScope<*>.explanationTextStyle() {
    style {
        color(varVariable("--md-sys-color-on-surface"))
        fontSize(16.px)
        lineHeight("1.6")
        margin(0.px)
    }
}
