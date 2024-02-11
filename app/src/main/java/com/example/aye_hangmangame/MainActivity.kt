package com.example.aye_hangmangame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val A = findViewById<Button>(R.id.a)
        val B = findViewById<Button>(R.id.b)
        val C = findViewById<Button>(R.id.c)
        val D = findViewById<Button>(R.id.d)
        val E = findViewById<Button>(R.id.e)
        val F = findViewById<Button>(R.id.f)
        val G = findViewById<Button>(R.id.g)
        val H = findViewById<Button>(R.id.h)
        val I = findViewById<Button>(R.id.i)
        val J = findViewById<Button>(R.id.j)
        val K = findViewById<Button>(R.id.k)
        val L = findViewById<Button>(R.id.l)
        val M = findViewById<Button>(R.id.m)
        val N = findViewById<Button>(R.id.n)
        val O = findViewById<Button>(R.id.o)
        val P = findViewById<Button>(R.id.p)
        val Q = findViewById<Button>(R.id.q)
        val R = findViewById<Button>(R.id.r)
        val S = findViewById<Button>(R.id.s)
        val T = findViewById<Button>(R.id.t)
        val U = findViewById<Button>(R.id.u)
        val V = findViewById<Button>(R.id.v)
        val W = findViewById<Button>(R.id.w)
        val X = findViewById<Button>(R.id.x)
        val Y = findViewById<Button>(R.id.y)
        val Z = findViewById<Button>(R.id.z)
        val hintButton = findViewById<Button>(R.id.hint)
        val newGame = findviewById<Button>(R.id.newgame)
        var incorrectCounter = 0
        var hintPressedCounter = 0

    }
}