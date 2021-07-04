package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

interface QuizInterface {
    fun setQuestion(questionCount: Int = 0, answers: MutableList<Int> = mutableListOf<Int>(), reset: Boolean = true)
    fun setResult(answers: MutableList<Int> = mutableListOf<Int>(), result: Int = 0)
}

class QuizFragment : Fragment() {

    private var quizInterface: QuizInterface? = null
    private var binding: FragmentQuizBinding? = null
    private var reset: Boolean = false
    private var currentAnswers = mutableListOf<Int>()
    private var questionNumber = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        var bundle: Bundle? = null
        reset = false
        try {
            bundle = arguments
            reset = bundle?.getBoolean(RESET) ?: false
            if(!reset) {
                currentAnswers = bundle?.getIntArray(ANSWERS)?.toMutableList() ?: mutableListOf<Int>()
                questionNumber = bundle?.getInt(QUESTION_NUMBER) ?: 0
            }
        } catch (e: Throwable) { }

        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun updateState() {
        //(activity as? MainActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(questionNumber != 0)
        if(questionNumber == 0) {
            binding?.toolbar?.navigationIcon = null
        }

        binding?.apply {
            toolbar.title = "Question #${1 + questionNumber}"
            question.text = Questions.questions[questionNumber].text
            optionOne.text = Questions.questions[questionNumber].answers[0].answer
            optionTwo.text = Questions.questions[questionNumber].answers[1].answer
            optionThree.text = Questions.questions[questionNumber].answers[2].answer
            optionFour.text = Questions.questions[questionNumber].answers[3].answer
            optionFive.text = Questions.questions[questionNumber].answers[4].answer

            previousButton.isEnabled = questionNumber > 0
            if(currentAnswers.size <= questionNumber) {
                nextButton.isEnabled = false
                radioGroup.clearCheck()
            }
            else {
                when(currentAnswers[questionNumber]) {
                    0 -> optionOne
                    1 -> optionTwo
                    2 -> optionThree
                    3 -> optionFour
                    else -> optionFive
                }.isChecked = true

                nextButton.text = if (questionNumber == 4) "submit" else "next"
            }
        }
    }

    private fun calcScore(): Int {
        var score = 0
        currentAnswers.forEachIndexed { index, value ->
            score += Questions.questions[index].answers[value].score
        }
        return score
    }

    private fun advanceQuestion() {
        questionNumber += 1
    }
    private fun growAnswers(value : Int = 0) {
        currentAnswers.add(value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateState()

        binding?.toolbar?.getChildAt(1)?.setOnClickListener {
            questionNumber -= 1
            quizInterface?.setQuestion(questionNumber, currentAnswers, false)
            updateState()
        }

        binding?.previousButton?.setOnClickListener {
            questionNumber -= 1
            quizInterface?.setQuestion(questionNumber, currentAnswers, false)
            updateState()
        }

        binding?.nextButton?.setOnClickListener {
            advanceQuestion()
            if (questionNumber == 5) {
                val score = calcScore()
                quizInterface?.setResult(currentAnswers, score)
            } else {
                binding?.toolbar?.title = "Question #${1 + questionNumber}"
                quizInterface?.setQuestion(questionNumber, currentAnswers, false)
                updateState()
            }
        }

        binding?.radioGroup?.setOnCheckedChangeListener { _, radioId ->

            if(questionNumber >= currentAnswers.size) {
                //currentAnswers.add(0)
                growAnswers()
            }
            currentAnswers[questionNumber] = when (radioId) {
                binding?.optionOne?.id -> 0
                binding?.optionTwo?.id ->  1
                binding?.optionThree?.id -> 2
                binding?.optionFour?.id ->  3
                else -> 4
                //binding?.optionFive?.id -> 4
            }
            binding?.nextButton?.isEnabled = true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        quizInterface = context as QuizInterface
    }

    override fun onDetach() {
        super.onDetach()
        quizInterface = null
    }


    companion object {

        private const val RESET = "RESET"
        private const val QUESTION_NUMBER = "QUESTIONS_COUNT"
        private const val ANSWERS = "ANSWERS"

        @JvmStatic
        fun newInstance(questionNumber: Int = 0, answers: IntArray = intArrayOf(), reset: Boolean = true): QuizFragment {
            val fragment = QuizFragment()
            val bundle = bundleOf(
                Pair(RESET, reset),
                Pair(ANSWERS, answers),
                Pair(QUESTION_NUMBER, questionNumber)
            )

            fragment.arguments = bundle
            return fragment
        }
    }
}
