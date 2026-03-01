package com.cognisolve

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.cognisolve.models.ExplainResponse
import com.cognisolve.models.ConfusionType
import com.cognisolve.ui.theme.varVariable

@Composable
fun DiagnosisSection(response: ExplainResponse) {
    Div({
        sectionContainerStyle("--md-sys-color-secondary-container")
    }) {
        Div({
            style {
                display(DisplayStyle.Flex)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.SpaceBetween)
                marginBottom(8.px)
            }
        }) {
            H2({ 
                style {
                    margin(0.px)
                    color(varVariable("--md-sys-color-on-surface"))
                    fontSize(22.px)
                    fontWeight("600")
                }
            }) { Text("Diagnosis") }
            
            // Confusion Type Badge
            Span({
                style {
                    backgroundColor(varVariable("--md-sys-color-primary"))
                    color(varVariable("--md-sys-color-on-primary"))
                    padding(6.px, 12.px)
                    borderRadius(16.px)
                    fontSize(14.px)
                    fontWeight("bold")
                    property("text-transform", "uppercase")
                    letterSpacing(0.5.px)
                }
            }) {
                Text(formatConfusionType(response.confusionType))
            }
        }

        // Justification text based on the diagnosis
        P({
            style {
                color(varVariable("--md-sys-color-on-surface"))
                fontSize(16.px)
                lineHeight("1.6")
                margin(0.px)
                opacity(0.9)
            }
        }) {
            Text(getConfusionDescription(response.confusionType))
        }
    }
}

fun formatConfusionType(type: ConfusionType): String {
    return type.name.replace("_", " ")
}

fun getConfusionDescription(type: ConfusionType): String {
    return when(type) {
        ConfusionType.CONCEPTUAL -> "You're missing the core 'why' or mental model behind this. We'll use an analogy to build the intuition."
        ConfusionType.PROCEDURAL -> "You understand the concept, but the exact steps are unclear. We'll break it down step-by-step."
        ConfusionType.ABSTRACTION_GAP -> "There's a disconnect between the high-level idea and the low-level execution. We'll bridge that gap."
        ConfusionType.MISCONCEPTION -> "You have a slight misunderstanding about how this works. We'll address the specific mistake."
        ConfusionType.TRANSFER -> "You know this from another language/context, but applying it here is tricky. We'll focus on the differences."
        ConfusionType.UNKNOWN -> "Let's simplify this and take another approach."
    }
}

// ── Shared States ────────────────────────────────────────────────

@Composable
fun LoadingState(message: String) {
    Div({
        style {
            padding(32.px)
            textAlign("center")
            color(varVariable("--md-sys-color-primary"))
            fontSize(18.px)
            fontWeight("500")
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            alignItems(AlignItems.Center)
            gap(16.px)
        }
    }) {
        // Simple CSS spinner
        Div({
            style {
                width(40.px)
                height(40.px)
                border {
                    width = 4.px
                    style = LineStyle.Solid
                    color = varVariable("--md-sys-color-outline")
                }
                property("border-top-color", "var(--md-sys-color-primary)")
                borderRadius(50.percent)
                property("animation", "spin 1s linear infinite")
            }
        })
        Text(message)
        
        // Inject animation using raw HTML block
        Style(LoadingStyleSheet)
    }
}

object LoadingStyleSheet : StyleSheet() {
    init {
        "@keyframes spin" {
            "0%" { property("transform", "rotate(0deg)") }
            "100%" { property("transform", "rotate(360deg)") }
        }
    }
}

@Composable
fun ErrorState(message: String) {
    Div({
        style {
            padding(24.px)
            backgroundColor(Color("#FFF0F0")) // Static red for error
            color(Color("#D32F2F"))
            borderRadius(12.px)
            border {
                width = 1.px
                style = LineStyle.Solid
                color = Color("#EF5350")
            }
            fontSize(16.px)
            fontWeight("500")
        }
    }) {
        Text("⚠️ Error: $message")
    }
}
