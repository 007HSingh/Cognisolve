package com.cognisolve.ui.theme

import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import androidx.compose.runtime.*

// Theme state — mutableStateOf so toggling triggers recomposition
object ThemeState {
    var isDark by mutableStateOf(false)
}

// Helper to reference CSS variables in inline styles
fun varVariable(name: String): CSSColorValue = Color("var($name)")

@Composable
fun CognisolveTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    // Update theme state
    ThemeState.isDark = darkTheme
    val scheme = if (darkTheme) CognisolveDarkColorScheme else CognisolveLightColorScheme

    // Root wrapper that sets CSS custom properties as inline styles
    // These cascade to all children via var(--token-name)
    Div({
        id("cognisolve-theme-root")
        style {
            // ── Map color tokens to CSS custom properties ──
            property("--md-sys-color-primary", scheme.primary)
            property("--md-sys-color-on-primary", scheme.onPrimary)
            property("--md-sys-color-background", scheme.background)
            property("--md-sys-color-surface", scheme.surface)
            property("--md-sys-color-on-surface", scheme.onSurface)
            property("--md-sys-color-surface-container", scheme.surfaceContainer)
            property("--md-sys-color-surface-container-low", scheme.surfaceContainerLow)
            property("--md-sys-color-primary-container", scheme.primaryContainer)
            property("--md-sys-color-secondary-container", scheme.secondaryContainer)
            property("--md-sys-color-tertiary-container", scheme.tertiaryContainer)
            property("--md-sys-color-outline", scheme.outline)

            // ── Global base styles ──
            backgroundColor(varVariable("--md-sys-color-background"))
            color(varVariable("--md-sys-color-on-surface"))
            fontFamily("Inter", "Roboto", "sans-serif")
            minHeight(100.vh)
            width(100.percent)
            margin(0.px)
            padding(0.px)
            property("transition", "background-color 0.3s ease, color 0.3s ease")
        }
    }) {
        content()
    }
}
