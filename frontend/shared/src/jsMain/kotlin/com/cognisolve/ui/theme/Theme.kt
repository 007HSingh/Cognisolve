package com.cognisolve.ui.theme

import org.jetbrains.compose.web.css.*
import androidx.compose.runtime.*

// Theme state
object ThemeState {
    var isDark by mutableStateOf(false)
}

// Global stylesheet for the app
object AppStyleSheet : StyleSheet() {
    val themeVars by style {
        val scheme = if (ThemeState.isDark) CognisolveDarkColorScheme else CognisolveLightColorScheme
        
        // Map Kotlin ColorScheme to CSS Variables
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

        // Global base styles
        backgroundColor(varVariable("--md-sys-color-background"))
        color(varVariable("--md-sys-color-on-surface"))
        fontFamily("Inter", "Roboto", "sans-serif")
        margin(0.px)
        padding(0.px)
        property("transition", "background-color 0.3s ease, color 0.3s ease")
    }
}

// Helper to easily reference CSS variables in inline styles
fun varVariable(name: String): CSSColorValue = Color("var($name)")

@Composable
fun CognisolveTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    // Update theme state
    ThemeState.isDark = darkTheme
    
    // Inject stylesheet
    Style(AppStyleSheet)
    content()
}
