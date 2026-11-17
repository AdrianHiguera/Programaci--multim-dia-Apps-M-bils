package com.example.jocparelles

enum class CardState { HIDDEN, SELECTED, MATCHED }

data class Card(
    val id: Int,            // posició a la graella (0..5)
    val imageRes: Int,      // imatge de la cara
    val state: CardState = CardState.HIDDEN
)

class ModelGame {

    private val faces = listOf(
        R.drawable.chiquito,
        R.drawable.eugenio,
        R.drawable.faemino,
        R.drawable.rubianes
    )
    private val backgrounds = listOf(
        R.drawable.fondo1,
        R.drawable.fondo2,
        R.drawable.fondo3,
        R.drawable.fondo4
    )

    val back: Int = R.drawable.back
    var background: Int = backgrounds.random()
        private set

    private var _cards: List<Card> = newCards()
    val cards: List<Card> get() = _cards

    val isFinished: Boolean get() = _cards.all { it.state == CardState.MATCHED }

    private fun newCards(): List<Card> {
        // 3 imatges diferents, cadascuna duplicada = 6 cartes
        val chosen = faces.shuffled().take(3)
        return (chosen + chosen).shuffled()
            .mapIndexed { index, res -> Card(index, res) }
    }

    fun flip(index: Int) {
        val selected = _cards.count { it.state == CardState.SELECTED }
        val card = _cards[index]
        if (card.state != CardState.HIDDEN || selected >= 2) return
        _cards = _cards.toMutableList().also {
            it[index] = card.copy(state = CardState.SELECTED)
        }
    }

    fun validate() {
        val selected = _cards.filter { it.state == CardState.SELECTED }
        if (selected.size != 2) return
        val equal = selected[0].imageRes == selected[1].imageRes
        _cards = _cards.map {
            if (it.state == CardState.SELECTED)
                it.copy(state = if (equal) CardState.MATCHED else CardState.HIDDEN)
            else it
        }
    }

    fun restart() {
        _cards = newCards()
        background = backgrounds.random()
    }
}