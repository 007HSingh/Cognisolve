package com.cognisolve

import org.jetbrains.compose.web.renderComposable
import com.cognisolve.ui.theme.CognisolveTheme
import com.cognisolve.ui.theme.ThemeState

fun main() {
    renderComposable(rootElementId = "root") {
        CognisolveTheme(darkTheme = ThemeState.isDark) {
            MainScreen()
        }
    }
}
