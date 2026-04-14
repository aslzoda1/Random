package com.example.random

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.random.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var secretNumber = 0
    private var attempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startNewGame()

        binding.buttonCheck.setOnClickListener {
            checkGuess()
        }
    }

    private fun startNewGame() {
        secretNumber = Random.nextInt(1, 101)
        attempts = 0
        binding.textViewAttempts.text = "Attempts: 0"
        binding.textViewStatus.text = "Good Luck! 🍀"
        binding.textViewStatus.setTextColor(Color.parseColor("#2D3436"))
        binding.editTextGuess.text?.clear()
    }

    private fun checkGuess() {
        val userGuessString = binding.editTextGuess.text.toString()

        if (userGuessString.isEmpty()) {
            binding.editTextGuess.error = "Enter a number!"
            return
        }

        val userGuess = userGuessString.toInt()
        attempts++
        binding.textViewAttempts.text = "Attempts: $attempts"

        val shake = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)

        when {
            userGuess < secretNumber -> {
                binding.textViewStatus.text = "Higher! ⬆️"
                binding.textViewStatus.setTextColor(Color.parseColor("#E67E22"))
                binding.textViewStatus.startAnimation(shake)
            }
            userGuess > secretNumber -> {
                binding.textViewStatus.text = "Lower! ⬇️"
                binding.textViewStatus.setTextColor(Color.parseColor("#E67E22"))
                binding.textViewStatus.startAnimation(shake)
            }
            else -> {
                binding.textViewStatus.text = "BINGO! 🎉"
                binding.textViewStatus.setTextColor(Color.parseColor("#27AE60"))

                binding.buttonCheck.text = "Play Again 🔄"
                binding.buttonCheck.setOnClickListener {
                    resetUI()
                }
            }
        }
        binding.editTextGuess.text?.clear()
    }

    private fun resetUI() {
        startNewGame()
        binding.buttonCheck.text = "Submit Guess"
        binding.buttonCheck.setOnClickListener {
            checkGuess()
        }
    }
}