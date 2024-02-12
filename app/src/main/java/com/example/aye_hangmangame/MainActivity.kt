package com.example.aye_hangmangame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var incorrectCounter = 0
    private var correctCounter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        correctCounter = 0
        incorrectCounter = 0
        //Gets list of possible hangman words from the strings.xml file
        val wordArray = resources.getStringArray(R.array.wordList)
        //Chooses one of the words at random, chosen word is the word to guess
        val chosenWord = wordArray[Random.nextInt(wordArray.size)]
        //Array of all 26 buttons for every letter
        val letterButtons = arrayOf(R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.m, R.id.n, R.id.o, R.id.p, R.id.q, R.id.r, R.id.s, R.id.t, R.id.u, R.id.v, R.id.w, R.id.x, R.id.y, R.id.z)
        val hintButton = findViewById<Button>(R.id.hint)
        val newGame = findViewById<Button>(R.id.newgame)
        var hintPressedCounter = 0

        //General onClickListener for the letters
        val clickListener = View.OnClickListener{ view ->
            //Casts the view as a button
            val button = view as Button
            //Get the letter that the button is associated with
            val letter = button.text.toString()
            //Check if letter is in the word
            checkLetterValidity(letter, chosenWord)
            //Changes the color of the button to show it's been pressed
            button.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
            //Makes the button un-clickable
            button.isEnabled = false
            //If all body parts shown, game over
            if (incorrectCounter == 6) {
                Toast.makeText(this, "Game Over!", Toast.LENGTH_LONG).show()
            }
            //If all letters have been guessed, player wins
            else if (correctCounter == chosenWord.length) {
                Toast.makeText(this, "You Win", Toast.LENGTH_LONG).show()
            }
        }

        //Assigns each letter button to the onClickListener with the variable name clickListener
        letterButtons.forEach { id -> findViewById<Button>(id).setOnClickListener(clickListener) }
    }
    private fun checkLetterValidity(letter: String, chosenWord: String) {
        if (!(chosenWord.contains(letter))) {
            //Code for making image of next body part appear

            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            incorrectCounter++
        } else {
            //Code for making the letter appear on the EditText to show it's a correct guess

            //Count how many instances of the letter is in the word
            val instanceCount = chosenWord.count { it == letter[0] }
            correctCounter += instanceCount
        }
    }
}