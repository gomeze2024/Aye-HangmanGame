package com.example.aye_hangmangame

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

data class Letter(val letterName : String)

class LetterAdapter (private val letterList : List<Letter>) :
    RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {

    var onLetterClick : ((Letter) -> Unit)? = null
    private var guessedLetters = ArrayList<Int>()

    class LetterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val letterName : Button = itemView.findViewById(R.id.letter)
    }

    fun setGuessedLetters(guessedLetters: ArrayList<Int>) {
        this.guessedLetters = guessedLetters
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_letter, parent, false)
        return LetterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return letterList.size
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val letter = letterList[position]
        holder.letterName.text = letter.letterName

        if (guessedLetters.contains(position)) {
            holder.letterName.isEnabled = false
            holder.letterName.visibility = View.INVISIBLE
        } else {
            holder.letterName.isEnabled = true
            holder.letterName.visibility = View.VISIBLE
        }

        holder.letterName.setOnClickListener {
            onLetterClick?.invoke(letter)
            holder.letterName.isEnabled = false
            holder.letterName.visibility = View.INVISIBLE
        }
    }
}