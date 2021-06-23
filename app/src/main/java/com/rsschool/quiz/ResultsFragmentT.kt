package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class ResultsFragmentT : Fragment() {

    private val binding = null
    private var resultsInterface: ResultsInterface? = null

    private var score = 0
    private var answers: MutableList<Int> = mutableListOf<Int>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultsInterface = context as ResultsInterface
    }

    override fun onDetach() {
        super.onDetach()
        resultsInterface = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }




    companion object {
        private const val keyResults = "results"
        private const val keyAnswers = "answers"

        fun newInstance(results: Int, answers: MutableList<Int>): ResultsFragment {
            val fragment = ResultsFragment()
            val bundle = bundleOf(
                Pair(keyResults, results),
                Pair(keyAnswers, answers)
            )

            fragment.arguments = bundle
            return fragment
        }
    }
}
