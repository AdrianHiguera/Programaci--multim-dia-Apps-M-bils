package com.example.jocparelles

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
class GameViewModel : ViewModel() {

    private val model = ModelGame()

    var cards by mutableStateOf(model.cards)
        private set
    var finished by mutableStateOf(model.isFinished)
        private set
    var background by mutableIntStateOf(model.background)
        private set

    val back: Int get() = model.back

    fun onCardClick(index: Int) {
        model.flip(index)
        refresh()
    }

    fun onValidate() {
        model.validate()
        refresh()
    }

    fun onRestart() {
        model.restart()
        refresh()
    }

    private fun refresh() {
        cards = model.cards
        finished = model.isFinished
        background = model.background
    }
}