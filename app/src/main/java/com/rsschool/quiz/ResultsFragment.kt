package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rsschool.quiz.databinding.FragmentResultsBinding
import java.lang.IllegalStateException


interface ResultsInterface {
    fun close()
    fun reset(doReset: Boolean)
    fun shareResult(textMessage: String)
}

private const val keyResults = "results"
private const val keyAnswers = "answers"


class ResultsFragment : Fragment() {

    private var binding: FragmentResultsBinding? = null
    private var resultsInterface: ResultsInterface? = null

    private var score = 0
    private var answers: MutableList<Int> = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun updateState() {
        binding?.tvResult?.text = "Score: $score / 100"
    }

    private fun sharedResult(): String {
        var result = "Score: $score\n"
        answers.forEachIndexed { index, answer ->
            result += "${(index+1).toString(10)}. ${Questions.questions[index].text}\n"
            Questions.questions[index].answers.forEachIndexed{ answerIndex, currentAnswer ->
                result += "${1 + answerIndex}. ${currentAnswer.answer}\n"
            }
            result += "- ${Questions.questions[index].answers[answer].answer}\n"
        }
        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var arg: Bundle? = null
        try {
            arg = requireArguments()
            if (arg != null) {
                score = arg.getInt(keyResults)
                answers = arg.get(keyAnswers) as MutableList<Int>
            }
        } catch (e: Throwable) { }

        updateState()

        binding?.ibReset?.setOnClickListener {
            resultsInterface?.reset(true)
        }
        binding?.ibClose?.setOnClickListener {
            resultsInterface?.close()
        }
        binding?.ibShare?.setOnClickListener {
            resultsInterface?.shareResult(sharedResult())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultsInterface = context as ResultsInterface
    }

    override fun onDetach() {
        super.onDetach()
        resultsInterface = null
    }



    companion object {

        private const val keyResults = "results"
        private const val keyAnswers = "answers"

        @JvmStatic
        fun newInstance(answers: MutableList<Int>, results: Int): ResultsFragment {
            val fragment = ResultsFragment()
            val bundle = bundleOf(
                Pair(ResultsFragment.keyResults, results),
                Pair(ResultsFragment.keyAnswers, answers)
            )

            fragment.arguments = bundle
            return fragment
        }
    }
}