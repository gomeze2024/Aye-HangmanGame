package com.example.aye_hangmangame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var hintCounter : Int = 0
    private var incorrectCounter : Int = 0
    private lateinit var gameState: String
    private lateinit var chosenWord : String
    private lateinit var guessedLetters : ArrayList<Int>
    private lateinit var wordArray : Array<String>

    private lateinit var newGameButton: Button
    private lateinit var hintButton: Button

    private val letterButtons = arrayOf(R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, 
        R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.m, R.id.n, R.id.o, R.id.p, R.id.q, R.id.r, 
        R.id.s, R.id.t, R.id.u, R.id.v, R.id.w, R.id.x, R.id.y, R.id.z)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            hintCounter = savedInstanceState.getInt(HINT_COUNTER, 0)
            incorrectCounter = savedInstanceState.getInt(INCORRECT_COUNTER, 0);
            chosenWord = savedInstanceState.getString(CHOSEN_WORD,"")
            gameState = savedInstanceState.getString(GAME_STATE, "")
            guessedLetters = savedInstanceState.getIntegerArrayList(GUESSED_LETTERS) ?: ArrayList()
        } else {
            wordArray = resources.getStringArray(R.array.wordList)
            chosenWord = wordArray[Random.nextInt(wordArray.size)]
            gameState = "_".repeat(chosenWord.length)
            guessedLetters = ArrayList()
        }

        hintButton = findViewById<Button>(R.id.hint)
        newGameButton = findViewById<Button>(R.id.newGame)
        findViewById<TextView>(R.id.gameTextView).text = gameState

        var guessedText = ""
        for (buttonId in guessedLetters) {
            val button = findViewById<Button>(buttonId)
            button.visibility = View.INVISIBLE
            guessedText += button.text
        }
        findViewById<TextView>(R.id.guessedTextView).text = guessedText

        //General onClickListener for the letters.
        val clickListener = View.OnClickListener{ view ->
            when (view.id) {
                R.id.newGame -> {
                    //start a new game
                }
                R.id.hint -> {
                    //give a hint
                }
                //Button pressed was a letter
                else -> {
                    val button : Button = view as Button
                    val letter : String = button.text.toString()

                    button.isEnabled = false
                    button.visibility = View.INVISIBLE
                    guessedLetters.add(view.id)

                    checkLetterValidity(letter)
                }
            }
        }

        //Assigns each button to the onClickListener above
        hintButton.setOnClickListener(clickListener)
        newGameButton.setOnClickListener(clickListener)
        letterButtons.forEach { viewId: Int-> findViewById<Button>(viewId).setOnClickListener(clickListener)}
    }

    private fun checkLetterValidity(letter: String) {
        if (chosenWord.contains(letter)) {
            Toast.makeText(this, "On the board!", Toast.LENGTH_SHORT).show()
            updateSecretWord(letter)
            checkIfWon()

        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            incorrectCounter++
            checkIfLost()
        }
    }

    private fun updateSecretWord(guessedLetter : String) {
        var secretWord : String = ""
        gameState.forEach { c -> secretWord +=
            if (c.toString() == guessedLetter) {
                    secretWord += c
             } else {
                 secretWord += "_"
             }
        }
        gameState = secretWord
    }

    private fun checkIfWon() : Boolean{
        return gameState == chosenWord
    }

    private fun checkIfLost() {
        when (incorrectCounter) {
            1 -> hangmanDrawing.setImageResource()
            2 -> hangmanDrawing.setImageResource()
            3 -> hangmanDrawing.setImageResource()
            4 -> hangmanDrawing.setImageResource()
            5 -> hangmanDrawing.setImageResource()
            6 -> hangmanDrawing.setImageResource()
            7 -> hangmanDrawing.setImageResource()
            8 -> hangmanDrawing.setImageResource()
            9 -> hangmanDrawing.setImageResource()
            10 -> hangmanDrawing.setImageResource()
            11 -> {
                hhangmanDrawing.setImageResource()
                //You lost
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_STATE, gameState)
        outState.putString(CHOSEN_WORD, chosenWord)
        outState.putInt(HINT_COUNTER, hintCounter)
        outState.putInt(INCORRECT_COUNTER, incorrectCounter)
        outState.putIntegerArrayList(GUESSED_LETTERS, ArrayList(guessedLetters))
    }

    companion object {
        private const val GAME_STATE = "game_state"
        private const val CHOSEN_WORD = "chosen_word"
        private const val HINT_COUNTER = "hint_counter"
        private const val INCORRECT_COUNTER = "incorrect_counter"
        private const val GUESSED_LETTERS = "remaining_letters"
    }
}