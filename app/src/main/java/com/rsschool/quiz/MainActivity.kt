package com.rsschool.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsschool.quiz.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), QuizInterface, ResultsInterface {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.frameLayout.id, QuizFragment())
                .commit()
        }
    }

    override fun setQuestion(questionCount: Int, answers: MutableList<Int>, reset: Boolean) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameLayout.id, QuizFragment.newInstance(questionCount, answers.toIntArray(), reset))
            .commit()
    }

    override fun setResult(answers: MutableList<Int>, result: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameLayout.id, ResultsFragment.newInstance(answers, result))
            .commit()
    }

    override fun reset(reset: Boolean) {
        setQuestion(reset = true)
    }

    override fun close() {
        finish()
        exitProcess(0)
    }

    override fun shareResult(message: String) {
        val shareIntent = Intent()
        shareIntent.apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Quiz result")
            putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }
    }
}