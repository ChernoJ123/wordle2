package com.example.wordle2

object FourLetterWordList {
    private val words = listOf("WORD", "GAME", "TEST", "CODE")

    fun getRandomFourLetterWord(): String {
        return words.random()
    }
}