package com.example.todo.util

// these are the effects of the user interactions with the ui

sealed class UiEvent {

    object PopBackStack: UiEvent()

    data class Navigate(val route: String): UiEvent() // this happens when we click on any button or composable

    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ): UiEvent()

}

// all these occur only once when called not every time for ex when cofig changes these must not be called but when something specific happens they are called only once