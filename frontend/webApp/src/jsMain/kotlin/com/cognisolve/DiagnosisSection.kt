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
            padding(40.px)
            textAlign("center")
            color(varVariable("--md-sys-color-primary"))
            fontSize(18.px)
            fontWeight("500")
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            alignItems(AlignItems.Center)
            gap(20.px)
            property("animation", "fadeIn 0.3s ease-out")
        }
    }) {
        // Spinner
        Div({
            style {
                width(44.px)
                height(44.px)
                borderRadius(50.percent)
                property("border", "4px solid var(--md-sys-color-outline)")
                property("border-top-color", "var(--md-sys-color-primary)")
                property("animation", "spin 0.8s linear infinite")
            }
        })
        
        // Message with pulse
        Span({
            style {
                property("animation", "pulse 1.5s ease-in-out infinite")
            }
        }) {
            Text(message)
        }
    }
}

@Composable
fun ErrorState(message: String, onRetry: (() -> Unit)? = null) {
    Div({
        style {
            padding(24.px)
            backgroundColor(Color("#FFF0F0"))
            color(Color("#D32F2F"))
            borderRadius(12.px)
            property("border", "1px solid #EF5350")
            fontSize(16.px)
            fontWeight("500")
            display(DisplayStyle.Flex)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.SpaceBetween)
            gap(16.px)
            property("animation", "fadeIn 0.3s ease-out")
        }
    }) {
        Span {
            Text("⚠️ Error: $message")
        }
        
        if (onRetry != null) {
            Button(attrs = {
                onClick { onRetry() }
                style {
                    backgroundColor(Color("#D32F2F"))
                    color(Color.white)
                    border { width = 0.px }
                    padding(8.px, 16.px)
                    borderRadius(8.px)
                    cursor("pointer")
                    fontWeight("600")
                    fontSize(14.px)
                    property("transition", "opacity 0.2s")
                    property("white-space", "nowrap")
                }
            }) {
                Text("Retry")
            }
        }
    }
}
