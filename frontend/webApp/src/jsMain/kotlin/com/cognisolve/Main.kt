package com.cognisolve

import org.jetbrains.compose.web.renderComposable
import com.cognisolve.ui.theme.CognisolveTheme

fun main() {
    renderComposable(rootElementId = "root") {
        CognisolveTheme(darkTheme = false) {
            MainScreen()
        }
    }
}
