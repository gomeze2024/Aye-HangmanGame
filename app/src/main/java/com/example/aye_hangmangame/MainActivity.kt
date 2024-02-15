package com.example.aye_hangmangame

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var hintCounter : Int = 0
    private var incorrectCounter : Int = 0
    private var chosenIndex : Int = 0
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

    private lateinit var head : ImageView
    private lateinit var body : ImageView
    private lateinit var leftArm : ImageView
    private lateinit var rightArm : ImageView
    private lateinit var leftLeg : ImageView
    private lateinit var rightLeg : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLetterButtons()

        wordArray = resources.getStringArray(R.array.wordList)
        hintArray = resources.getStringArray(R.array.hintList)

        if (savedInstanceState != null) {
            hintCounter = savedInstanceState.getInt(HINT_COUNTER, 0)
            incorrectCounter = savedInstanceState.getInt(INCORRECT_COUNTER, 0)
            chosenIndex = savedInstanceState.getInt(CHOSEN_INDEX,0)
            chosenWord = wordArray[chosenIndex]
            gameState = savedInstanceState.getString(GAME_STATE, "")
            guessedLetters = savedInstanceState.getIntegerArrayList(GUESSED_LETTERS) ?: ArrayList()
            lettersAdapter.setGuessedLetters(guessedLetters)
        } else {
            chosenIndex = Random.nextInt(wordArray.size - 1)
            chosenWord = wordArray[chosenIndex]
            wordHint = hintArray[chosenIndex]
            gameState = "_".repeat(chosenWord.length)
            guessedLetters = ArrayList()
        }

        initLetterButtons()
        hintUpdate()
        lettersAdapter.setDisableFalse()

        newGameButton = findViewById(R.id.newGame)
        findViewById<TextView>(R.id.gameTextView).text = gameState

        head = findViewById(R.id.head)
        body = findViewById(R.id.body)
        leftArm = findViewById(R.id.leftArm)
        rightArm = findViewById(R.id.rightArm)
        leftLeg = findViewById(R.id.leftLeg)
        rightLeg = findViewById(R.id.rightLeg)

        setHangManInvisible()
        checkIfWon()
        checkIfLost()

        //General onClickListener for the letters.
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.newGame -> startNewGame()
                R.id.hint -> showHint()
            }
        }

        newGameButton.setOnClickListener(clickListener)
    }

    private fun setHangManInvisible () {
        head.visibility = View.INVISIBLE
        body.visibility = View.INVISIBLE
        leftArm.visibility = View.INVISIBLE
        rightArm.visibility = View.INVISIBLE
        leftLeg.visibility = View.INVISIBLE
        rightLeg.visibility = View.INVISIBLE
    }

    private fun checkLetterValidity(letter: String) {
        if (chosenWord.contains(letter)) {
            updateSecretWord(letter)
            checkIfWon()

        } else {
            incorrectCounter++
            checkIfLost()
        }
    }

    private fun initLetterButtons() {
        letterButtons = findViewById(R.id.recyclerView)
        letterButtons.setHasFixedSize(true)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            letterButtons.layoutManager = GridLayoutManager(this, 5, RecyclerView.VERTICAL, false)
            hintButton = findViewById(R.id.hint)
            hintView = findViewById(R.id.hintView)
        } else {
            letterButtons.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        }

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

    private fun disableLetterButtons(){
        lettersAdapter.setDisableTrue()
        initLetterButtons()
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
        chosenIndex = Random.nextInt(wordArray.size - 1)
        chosenWord = wordArray[chosenIndex]
        wordHint = hintArray[chosenIndex]
        gameState = "_".repeat(chosenWord.length)
        guessedLetters = ArrayList()

        hintCounter = 0
        incorrectCounter = 0

        findViewById<TextView>(R.id.gameTextView).text = gameState

        initLetterButtons()
        lettersAdapter.setDisableFalse()

        head.visibility = View.INVISIBLE
        body.visibility = View.INVISIBLE
        leftArm.visibility = View.INVISIBLE
        rightArm.visibility = View.INVISIBLE
        leftLeg.visibility = View.INVISIBLE
        rightLeg.visibility = View.INVISIBLE
    }

    private fun showHint(){
        if (incorrectCounter == 5) {
            //If clicking would cause user to lose
            Toast.makeText(this, "Hint not available.", Toast.LENGTH_SHORT).show()
        } else {
            //give a hint
            hintCounter++
            incorrectCounter++
            hintUpdate()
        }
    }

    private fun hintUpdate() {
        when (hintCounter) {
            0 -> {}
            1 -> {disableHalfLetters()}
            2 -> {showVowels()}
            //Shows all vowels
        }
    }

    private fun disableHalfLetters(){
        //Disables half remaining letters
        //calc num of remaining letters
        val numLettersRemaining = (letterList.size - GUESSED_LETTERS.length) / 2
        //Gets indices of not guessed letters
        val allUnguessedIndices = letterList.indices.filter { it !in guessedLetters }
        //Gets indices of all incorrect letters
        val allIncorrectIndices = allUnguessedIndices.filter { letterList[it].letterName.toCharArray()[0] !in chosenWord}.toMutableList()
        for (i in 1..numLettersRemaining) {
            val randomIndex = Random.nextInt(allIncorrectIndices.size)
            guessedLetters.add(randomIndex)
        }
        lettersAdapter.setGuessedLetters(guessedLetters)
        initLetterButtons()
    }

    private fun showVowels() {
        val vowels = listOf(0, 4,9,14,20)
        for (index in vowels) {
            guessedLetters.add(index)
            updateSecretWord(letterList[index].letterName)
        }
        initLetterButtons()
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

    private fun checkIfWon() {
        if (gameState == chosenWord){
            Toast.makeText(this, "You won! Up for a New Games?", Toast.LENGTH_SHORT).show()
            lettersAdapter.setDisableTrue()
        }
    }

    private fun checkIfLost() {
        when (incorrectCounter) {
            1 -> head.visibility = View.VISIBLE
            2 -> {head.visibility = View.VISIBLE
                body.visibility = View.VISIBLE}
            3 -> {head.visibility = View.VISIBLE
                body.visibility = View.VISIBLE
                leftArm.visibility = View.VISIBLE}

            4 -> {head.visibility = View.VISIBLE
                body.visibility = View.VISIBLE
                leftArm.visibility = View.VISIBLE
                rightArm.visibility = View.VISIBLE}
            5 -> {head.visibility = View.VISIBLE
                body.visibility = View.VISIBLE
                leftArm.visibility = View.VISIBLE
                rightArm.visibility = View.VISIBLE
                leftLeg.visibility = View.VISIBLE}
            6 -> {
                head.visibility = View.VISIBLE
                body.visibility = View.VISIBLE
                leftArm.visibility = View.VISIBLE
                rightArm.visibility = View.VISIBLE
                leftLeg.visibility = View.VISIBLE
                rightLeg.visibility = View.VISIBLE
                disableLetterButtons()
                Toast.makeText(this, "You Lost? Up for a New Game?", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_STATE, gameState)
        outState.putInt(CHOSEN_INDEX, chosenIndex)
        outState.putInt(HINT_COUNTER, hintCounter)
        outState.putInt(INCORRECT_COUNTER, incorrectCounter)
        outState.putIntegerArrayList(GUESSED_LETTERS, ArrayList(guessedLetters))
    }

    companion object {
        private const val GAME_STATE = "game_state"
        private const val CHOSEN_INDEX = "chosen_index"
        private const val HINT_COUNTER = "hint_counter"
        private const val INCORRECT_COUNTER = "incorrect_counter"
        private const val GUESSED_LETTERS = "remaining_letters"
    }
}