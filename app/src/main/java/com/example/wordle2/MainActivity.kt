package com.example.wordle2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wordle2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var secretWord = FourLetterWordList.getRandomFourLetterWord()
    private var guesses = 0
    private val maxGuesses = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            val guess = binding.inputGuess.text.toString().uppercase()

            if (guess.length != 4) {
                Toast.makeText(this, "Enter 4 letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (guesses >= maxGuesses) {
                Toast.makeText(this, "No guesses left!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            checkGuess(guess)
            binding.inputGuess.text.clear()
        }

        binding.restartBtn.setOnClickListener {
            restartGame()
        }
    }

    private fun checkGuess(guess: String) {
        val result = checkGuessResult(guess)

        val tv = when (guesses) {
            0 -> binding.guess1
            1 -> binding.guess2
            else -> binding.guess3
        }

        tv.text = "$guess  →  $result"

        guesses++

        if (guess == secretWord) {
            Toast.makeText(this, "YOU WIN!", Toast.LENGTH_LONG).show()
            binding.submitBtn.isEnabled = false
        } else if (guesses == maxGuesses) {
            Toast.makeText(this, "Word was $secretWord", Toast.LENGTH_LONG).show()
            binding.submitBtn.isEnabled = false
        }
    }

    // REQUIRED FORMAT: O + X
    private fun checkGuessResult(guess: String): String {
        val result = StringBuilder()

        for (i in guess.indices) {
            when {
                guess[i] == secretWord[i] -> result.append("O")
                secretWord.contains(guess[i]) -> result.append("+")
                else -> result.append("X")
            }
        }
        return result.toString()
    }

    private fun restartGame() {
        secretWord = FourLetterWordList.getRandomFourLetterWord()
        guesses = 0

        binding.guess1.text = ""
        binding.guess2.text = ""
        binding.guess3.text = ""
        binding.submitBtn.isEnabled = true

        Toast.makeText(this, "New Game!", Toast.LENGTH_SHORT).show()
    }
}
