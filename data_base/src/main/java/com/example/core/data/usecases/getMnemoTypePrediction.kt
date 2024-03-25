package com.example.core.data.usecases

import android.util.Log
import com.example.core.domain.repo.GameRepository

class getMnemoTypePrediction(val repo: GameRepository) {
    suspend fun execute(changeType: Boolean = false): Int {
        val learningMethod = repo.getAllLearningMethod()
        return if (learningMethod.size > 25 && !changeType) {
            learningMethod.last().mnemoType
        } else {
            (1..6).random()
        }
    }
}
