package com.rsschool.quiz

class Answer(val answer: String, val score: Int)

class Question(val text: String, val answers: Array<Answer>)

class Questions {
    companion object {
        val questions = arrayOf(
            Question(text = "1 + 1 = ?", answers = arrayOf(
                Answer("1", 5),
                Answer("splunge", 0),
                Answer("sometimes 3", 0),
                Answer("4 because why not", 0),
                Answer("2", 15),
            )),
            Question(text = "Do kitties meow?", answers = arrayOf(
                Answer("not sure", 0),
                Answer("cube", 0),
                Answer("they only purr", 0),
                Answer("no doubt", 15),
                Answer("2", 0),
            )),
            Question(text = "What did Marry Poppins say?", answers = arrayOf(
                Answer("fortnite is vanilla pubg!", 0),
                Answer("may the force be with you", 0),
                Answer("supercalifragilisticexpialidocious", 25),
                Answer("asta la vista, baby", 0),
                Answer("toto, i've a feeling we're not in kansas anymore", 0),
            )),
            Question(text = "Choose the most fluffy one:", answers = arrayOf(
                Answer("fun", 0),
                Answer("sun", 0),
                Answer("dolphin", 0),
                Answer("kitty", 15),
                Answer("hedgehog", 0),
            )),
            Question(text = "Essential part of sweets:", answers = arrayOf(
                Answer("sugar", 30),
                Answer("soda", 0),
                Answer("salt", 0),
                Answer("sulphur", 0),
                Answer("scandium", 0),
            )),
        )
    }
}