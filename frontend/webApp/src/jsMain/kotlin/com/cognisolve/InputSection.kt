package com.cognisolve

import androidx.compose.runtime.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.attributes.*
import com.cognisolve.ui.theme.varVariable

@Composable
fun InputSection(
    isLoading: Boolean,
    onSubmit: (concept: String, doubt: String, code: String?) -> Unit
) {
    var concept by remember { mutableStateOf("") }
    var doubt by remember { mutableStateOf("") }
    var codeSnippet by remember { mutableStateOf("") }

    Div({
        sectionContainerStyle("--md-sys-color-surface-container-low")
    }) {
        H2({ sectionHeaderStyle() }) { Text("What are you learning?") }

        // Concept Input
        Div({ inputGroupStyle() }) {
            Label(forId = "input-concept") { Text("Topic or Concept") }
            TextInput(concept) {
                id("input-concept")
                placeholder("e.g. Recursion, Pointers in C, React State")
                onInput { concept = it.value }
                if (isLoading) disabled()
                style { textInputStyle() }
            }
        }

        // Doubt Input
        Div({ inputGroupStyle() }) {
            Label(forId = "input-doubt") { Text("What's confusing you?") }
            TextArea(doubt) {
                id("input-doubt")
                placeholder("e.g. I don't get why the function calls itself.")
                onInput { doubt = it.value }
                if (isLoading) disabled()
                style { 
                    textInputStyle()
                    minHeight(80.px)
                    property("resize", "vertical")
                }
            }
        }

        // Code Input (Optional)
        Div({ inputGroupStyle() }) {
            Label(forId = "input-code") { Text("Code Snippet (Optional)") }
            TextArea(codeSnippet) {
                id("input-code")
                placeholder("Paste code here...")
                onInput { codeSnippet = it.value }
                if (isLoading) disabled()
                style { 
                    textInputStyle()
                    minHeight(100.px)
                    fontFamily("'JetBrains Mono', 'Fira Code', monospace")
                    property("resize", "vertical")
                }
            }
        }

        // Submit Button
        Button(attrs = {
            id("submit-button")
            onClick { onSubmit(concept, doubt, codeSnippet.takeIf { it.isNotBlank() }) }
            if (isLoading || concept.isBlank() || doubt.isBlank()) disabled()
            style { primaryButtonStyle(isLoading || concept.isBlank() || doubt.isBlank()) }
        }) {
            Text(if (isLoading) "Analyzing..." else "Explain it to me →")
        }
    }
}

// ── Shared UI Styles ─────────────────────────────────────────────

fun AttrsScope<*>.sectionContainerStyle(bgColorVar: String) {
    style {
        backgroundColor(varVariable(bgColorVar))
        padding(24.px)
        borderRadius(16.px)
        property("border", "1px solid var(--md-sys-color-outline)")
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(16.px)
        property("box-shadow", "0 2px 12px -2px rgba(0, 0, 0, 0.08)")
        property("transition", "background-color 0.3s ease, box-shadow 0.3s ease, border-color 0.3s ease")
        property("backdrop-filter", "blur(8px)")
    }
}

fun AttrsScope<*>.sectionHeaderStyle() {
    style {
        marginTop(0.px)
        marginBottom(8.px)
        color(varVariable("--md-sys-color-on-surface"))
        fontSize(24.px)
        fontWeight("600")
        letterSpacing((-0.3).px)
    }
}

fun AttrsScope<*>.inputGroupStyle() {
    style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(8.px)
        color(varVariable("--md-sys-color-on-surface"))
        fontSize(14.px)
        fontWeight("500")
    }
}

fun StyleScope.textInputStyle() {
    width(100.percent)
    padding(12.px, 14.px)
    boxSizing("border-box")
    borderRadius(10.px)
    backgroundColor(varVariable("--md-sys-color-background"))
    color(varVariable("--md-sys-color-on-surface"))
    property("border", "1.5px solid var(--md-sys-color-outline)")
    fontSize(16.px)
    fontFamily("inherit")
    property("transition", "border-color 0.2s ease, box-shadow 0.2s ease")
    property("outline", "none")
}

fun StyleScope.primaryButtonStyle(isDisabled: Boolean) {
    padding(14.px, 28.px)
    borderRadius(10.px)
    property("border", "none")
    backgroundColor(varVariable(if (isDisabled) "--md-sys-color-outline" else "--md-sys-color-primary"))
    color(varVariable("--md-sys-color-on-primary"))
    fontSize(16.px)
    fontWeight("600")
    cursor(if (isDisabled) "not-allowed" else "pointer")
    property("transition", "all 0.2s ease")
    marginTop(8.px)
    opacity(if (isDisabled) 0.6 else 1.0)
    property("transform", "scale(1)")
    letterSpacing(0.3.px)
}
