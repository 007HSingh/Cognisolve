package com.cognisolve.ui.theme

data class CognisolveColorScheme(
    val primary: String,
    val onPrimary: String,
    val background: String,
    val surface: String,
    val onSurface: String,
    val surfaceContainer: String,
    val surfaceContainerLow: String,
    val primaryContainer: String,
    val secondaryContainer: String,
    val tertiaryContainer: String,
    val outline: String,
)

val CognisolveLightColorScheme = CognisolveColorScheme(
    primary = "#00696F",
    onPrimary = "#FFFFFF",
    background = "#F5FAFA",
    surface = "#F5FAFA",
    onSurface = "#171D1D",
    surfaceContainer = "#E9EFEF",
    surfaceContainerLow = "#EFF5F5", // Slightly lighter than surfaceContainer
    primaryContainer = "#9CF8FF",    // Based on dark primary logic for containers
    secondaryContainer = "#BCE8EB",
    tertiaryContainer = "#DFC0FF",
    outline = "#6D797A"
)

val CognisolveDarkColorScheme = CognisolveColorScheme(
    primary = "#9CF8FF",
    onPrimary = "#00363A",
    background = "#0F1415",
    surface = "#0F1415",
    onSurface = "#DEE3E4",
    surfaceContainer = "#1B2121",
    surfaceContainerLow = "#171D1D",
    primaryContainer = "#004F54",
    secondaryContainer = "#254F52",
    tertiaryContainer = "#533671", // Deduced darker tertiary
    outline = "#879394"
)
