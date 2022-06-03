package com.example.mvi_pattern_example.ui.intent

sealed class MainIntent {
    object FetchUser : MainIntent()
}