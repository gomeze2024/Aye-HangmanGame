package com.example.aye_hangmangame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var hintCounter : Int = 0
    private var incorrectCounter : Int = 0
    private var randomNumber : Int = 0
    private var lettersNotGuessedYet : Int = 26
    private lateinit var gameState: String
    private lateinit var chosenWord : String
    private lateinit var wordHint : String
    private lateinit var hintView : TextView
    private lateinit var guessedLetters : ArrayList<Int>
    private lateinit var wordArray : Array<String>
    private lateinit var hintArray : Array<String>

    private lateinit var newGameButton: Button
    private lateinit var hintButton: Button

    private lateinit var letterButtons : RecyclerView
    private lateinit var letterList : ArrayList<Letter>
    private lateinit var lettersAdapter : LetterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            hintCounter = savedInstanceState.getInt(HINT_COUNTER, 0)
            incorrectCounter = savedInstanceState.getInt(INCORRECT_COUNTER, 0);
            chosenWord = savedInstanceState.getString(CHOSEN_WORD,"")
            gameState = savedInstanceState.getString(GAME_STATE, "")
            guessedLetters = savedInstanceState.getIntegerArrayList(GUESSED_LETTERS) ?: ArrayList()
            lettersAdapter.setGuessedLetters(guessedLetters)
        } else {
            wordArray = resources.getStringArray(R.array.wordList)
            hintArray = resources.getStringArray(R.array.hintList)
            randomNumber = Random.nextInt(wordArray.size)
            chosenWord = wordArray[randomNumber]
            wordHint = hintArray[randomNumber]
            gameState = "_".repeat(chosenWord.length)
            guessedLetters = ArrayList()
        }

        initLetterButtons()

        //hintButton = findViewById<Button>(R.id.hint)
        hintButton = findViewById<Button>(R.id.hint)
        hintView = findViewById<TextView>(R.id.hintWord)
        newGameButton = findViewById<Button>(R.id.newGame)
        findViewById<TextView>(R.id.gameTextView).text = gameState

        //General onClickListener for the letters.
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.newGame -> {
                    //start a new game
                    startNewGame()
                }
                R.id.hint -> {
                    if (incorrectCounter == 11) {
                        //If clicking would cause user to lose
                        Toast.makeText(this, "Hint not available.", Toast.LENGTH_SHORT).show()
                    } else {
                        //give a hint
                        when (hintCounter) {
                            0 -> {
                                hintView.text = wordHint
                                hintCounter++
                            }
                            1 -> {
                                //Disables half remaining letters
                                    //calc num of remaining letters
                                val numLettersRemaining = lettersNotGuessedYet / 2
                                //Gets indices of unguessed letters
                                val allUnguessedIndices = letterList.indices.filter { it !in guessedLetters }
                                //Gets indices of all incorrect letters
                                val allIncorrectIndices = allUnguessedIndices.filter { letterList[it].letterName.toCharArray()[0] !in chosenWord}.toMutableList()
                                    for (i in 1..numLettersRemaining) {
                                        val randomIndex = Random.nextInt(allIncorrectIndices.size)
                                        val button: Button = letterButtons.layoutManager?.findViewByPosition(allIncorrectIndices[randomIndex]) as Button
                                        button.isEnabled = false
                                        button.visibility = View.INVISIBLE
                                        allIncorrectIndices.removeAt(randomIndex)
                                    }
                                //Uses a turn
                                incorrectCounter++
                                hintCounter++
                            }
                            2 -> {
                                //Shows all vowels
                                showVowels()
                                //Uses a turn
                                incorrectCounter++
                                hintCounter++
                            }
                            else -> {
                                //No more hints to give
                                Toast.makeText(this, "No more hints!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                //Button pressed was a letter
                else -> {
                }
            }
        }

        newGameButton.setOnClickListener(clickListener)
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

    private fun initLetterButtons() {
        letterButtons = findViewById(R.id.recyclerView)
        letterButtons.setHasFixedSize(true)
        letterButtons.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        letterList = ArrayList()
        addLettersToList()

        lettersAdapter = LetterAdapter(letterList)
        letterButtons.adapter = lettersAdapter

        lettersAdapter.onLetterClick = {
            val letterIndex: Int = letterList.indexOf(it)

            if (letterIndex != 1) {
                guessedLetters.add(letterIndex)
            }

            checkLetterValidity(it.letterName)
        }
    }

    private fun addLettersToList() {
        letterList.add(Letter(getString(R.string.a)))
        letterList.add(Letter(getString(R.string.b)))
        letterList.add(Letter(getString(R.string.c)))
        letterList.add(Letter(getString(R.string.d)))
        letterList.add(Letter(getString(R.string.e)))
        letterList.add(Letter(getString(R.string.f)))
        letterList.add(Letter(getString(R.string.g)))
        letterList.add(Letter(getString(R.string.h)))
        letterList.add(Letter(getString(R.string.i)))
        letterList.add(Letter(getString(R.string.j)))
        letterList.add(Letter(getString(R.string.k)))
        letterList.add(Letter(getString(R.string.l)))
        letterList.add(Letter(getString(R.string.m)))
        letterList.add(Letter(getString(R.string.n)))
        letterList.add(Letter(getString(R.string.o)))
        letterList.add(Letter(getString(R.string.p)))
        letterList.add(Letter(getString(R.string.q)))
        letterList.add(Letter(getString(R.string.r)))
        letterList.add(Letter(getString(R.string.s)))
        letterList.add(Letter(getString(R.string.t)))
        letterList.add(Letter(getString(R.string.u)))
        letterList.add(Letter(getString(R.string.v)))
        letterList.add(Letter(getString(R.string.w)))
        letterList.add(Letter(getString(R.string.x)))
        letterList.add(Letter(getString(R.string.y)))
        letterList.add(Letter(getString(R.string.z)))
    }

    private fun startNewGame() {
        wordArray = resources.getStringArray(R.array.wordList)
        hintArray = resources.getStringArray(R.array.hintList)
        randomNumber = Random.nextInt(wordArray.size)
        chosenWord = wordArray[randomNumber]
        wordHint = hintArray[randomNumber]
        gameState = "_".repeat(chosenWord.length)
        guessedLetters = ArrayList()

        hintCounter = 0
        incorrectCounter = 0
        randomNumber = 0
        lettersNotGuessedYet = 26

        findViewById<TextView>(R.id.gameTextView).text = gameState

        hangmanDrawing.setImageResource(0)

        letterButtons.forEach { button : Button ->
            button.isEnabled = true
            button.visibility = View.VISIBLE
        }
    }

    private fun showVowels() {
        val vowels = listOf('A','E','I','O','U')
        for (letter in vowels) {
            if (chosenWord.contains(letter)) {
                updateSecretWord(letter.toString())
                when (letter) {
                    'A' -> {
                        val button: Button = findViewById(R.id.a)
                        button.isEnabled = false
                        button.visibility = View.INVISIBLE
                        guessedLetters.add(R.id.a)
                        lettersNotGuessedYet--
                    }
                    'E' -> {
                        val button: Button = findViewById(R.id.e)
                        button.isEnabled = false
                        button.visibility = View.INVISIBLE
                        guessedLetters.add(R.id.e)
                        lettersNotGuessedYet--
                    }
                    'I' -> {
                        val button: Button = findViewById(R.id.i)
                        button.isEnabled = false
                        button.visibility = View.INVISIBLE
                        guessedLetters.add(R.id.i)
                        lettersNotGuessedYet--
                    }
                    'O' -> {
                        val button: Button = findViewById(R.id.o)
                        button.isEnabled = false
                        button.visibility = View.INVISIBLE
                        guessedLetters.add(R.id.o)
                        lettersNotGuessedYet--
                    }
                    'U' -> {
                        val button: Button = findViewById(R.id.u)
                        button.isEnabled = false
                        button.visibility = View.INVISIBLE
                        guessedLetters.add(R.id.u)
                        lettersNotGuessedYet--
                    }
                }
            }
        }
    }


    private fun updateSecretWord(guessedLetter: String) {
        gameState = chosenWord.mapIndexed { index, char ->
            when {
                char.toString() == guessedLetter -> char
                gameState[index] != '_' -> gameState[index]
                else -> "_"
            }
        }.joinToString("")

        findViewById<TextView>(R.id.gameTextView).text = gameState
    }

    private fun checkIfWon() : Boolean{
        return gameState == chosenWord
    }

    private fun checkIfLost() {
        when (incorrectCounter) {
            /**1 -> hangmanDrawing.setImageResource()
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
                hangmanDrawing.setImageResource()
                //You lost
            }**/
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