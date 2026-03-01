package com.cognisolve

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import com.cognisolve.ui.theme.varVariable

@Composable
fun MainScreen() {
    Div({
        style {
            padding(24.px)
            maxWidth(800.px)
            property("margin", "0 auto")
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            gap(24.px)
        }
    }) {
        H1({
            style {
                color(varVariable("--md-sys-color-primary"))
                fontSize(32.px)
                fontWeight("bold")
                margin(0.px)
            }
        }) {
            Text("Cognisolve")
        }

        P({
            style {
                color(varVariable("--md-sys-color-on-surface"))
                fontSize(16.px)
                margin(0.px)
            }
        }) {
            Text("Explain like I'm stuck: AI tutor for conceptual confusion.")
        }
        
        // Placeholder boxes to verify theme colors
        ThemeTestBox("Input Block", "--md-sys-color-surface-container-low")
        ThemeTestBox("Diagnosis Block", "--md-sys-color-secondary-container")
        ThemeTestBox("Explanation Block", "--md-sys-color-surface-container")
        ThemeTestBox("Practice Block", "--md-sys-color-primary-container")
        ThemeTestBox("Feedback Block", "--md-sys-color-tertiary-container")
    }
}

@Composable
fun ThemeTestBox(title: String, bgColorVar: String) {
    Div({
        style {
            backgroundColor(varVariable(bgColorVar))
            padding(16.px)
            borderRadius(12.px)
            border {
                width = 1.px
                style = LineStyle.Solid
                color = varVariable("--md-sys-color-outline")
            }
        }
    }) {
        H3({
            style {
                marginTop(0.px)
                marginBottom(8.px)
                color(varVariable("--md-sys-color-on-surface"))
            }
        }) {
            Text(title)
        }
    }
}
