package com.example.core.data.usecases

import android.util.Log
import com.example.core.domain.repo.GameRepository

class getMnemoTypePrediction(val repo: GameRepository) {
    suspend fun execute(): Int {
        val learningMethod = repo.getAllLearningMethod()
        Log.i("Learning_size", learningMethod.size.toString())
        return if (learningMethod.size > 25) {
            learningMethod.last().mnemoType
        } else {
            (1..6).random()
        }
    }
}
