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
                    fontFamily("monospace")
                    property("resize", "vertical")
                }
            }
        }

        // Submit Button
        Button(attrs = {
            onClick { onSubmit(concept, doubt, codeSnippet.takeIf { it.isNotBlank() }) }
            if (isLoading || concept.isBlank() || doubt.isBlank()) disabled()
            style { primaryButtonStyle(isLoading || concept.isBlank() || doubt.isBlank()) }
        }) {
            Text(if (isLoading) "Analyzing..." else "Explain it to me")
        }
    }
}

// ── Shared UI Styles ─────────────────────────────────────────────

fun AttrsScope<*>.sectionContainerStyle(bgColorVar: String) {
    style {
        backgroundColor(varVariable(bgColorVar))
        padding(24.px)
        borderRadius(16.px)
        border {
            width = 1.px
            style = LineStyle.Solid
            color = varVariable("--md-sys-color-outline")
        }
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        gap(16.px)
        property("box-shadow", "0 4px 6px -1px rgba(0, 0, 0, 0.1)")
        property("transition", "background-color 0.3s ease")
    }
}

fun AttrsScope<*>.sectionHeaderStyle() {
    style {
        marginTop(0.px)
        marginBottom(8.px)
        color(varVariable("--md-sys-color-on-surface"))
        fontSize(24.px)
        fontWeight("600")
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
    padding(12.px)
    boxSizing("border-box")
    borderRadius(8.px)
    backgroundColor(varVariable("--md-sys-color-background"))
    color(varVariable("--md-sys-color-on-surface"))
    border {
        width = 1.px
        style = LineStyle.Solid
        color = varVariable("--md-sys-color-outline")
    }
    fontSize(16.px)
    fontFamily("inherit")
    // Focus ring outline would go here via CSS classes ideally, simplified for Compose Web
}

fun StyleScope.primaryButtonStyle(isDisabled: Boolean) {
    padding(12.px, 24.px)
    borderRadius(8.px)
    border { width = 0.px }
    backgroundColor(varVariable(if (isDisabled) "--md-sys-color-outline" else "--md-sys-color-primary"))
    color(varVariable("--md-sys-color-on-primary"))
    fontSize(16.px)
    fontWeight("600")
    cursor(if (isDisabled) "not-allowed" else "pointer")
    property("transition", "all 0.2s ease")
    marginTop(8.px)
}
