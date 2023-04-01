package com.example.ask_answer_domain

import com.example.ask_answer_data.Answer
import com.example.ask_answer_data.Question
import com.example.ask_answer_data.QuestionRepo

class QuestionRepoImp : QuestionRepo {
    override fun getAllQuestion() =
        listOf(
            Question(
                "What kind of action in the end of CA cycle ?  ",
                listOf(
                    Answer(
                        false,
                        "C1 cycle",
                        "C1 from CS"
                    ),
                    Answer(
                        false,
                        "Morgen cycle",
                        "mogenstain"
                    ),
                    Answer(
                        true,
                        "Cris cycle",
                        "CrisParcas"
                    ),
                    Answer(
                        true,
                        "C2 cycle",
                        "C2 from CS2"
                    )
                )
            )
        )
}
