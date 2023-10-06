package com.example.ai.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.ai.base_ai_classes.ActivationOps
import com.example.ai.base_ai_classes.Dense
import com.example.ai.base_ai_classes.MatrixOps
import com.example.ai.base_ai_classes.Model
import com.example.ai.base_ai_classes.Optimazer.RMSProp
import com.example.ai.base_ai_classes.loss.MeanSquaredError

class AiTainingWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val trainData = doubleArrayOf(10000.0, 1200.0)
        val answerData = doubleArrayOf(1.0, 0.0)
        val trainData2 = doubleArrayOf(-10000.0, -1200.0)
        val answerData2 = doubleArrayOf(0.0, 1.0)
        val model = Model(
            inputDims = 2,
            layers = arrayOf(
                Dense(12, ActivationOps.ReLU()),
                Dense(6, ActivationOps.Sigmoid()),
                Dense(2, ActivationOps.Softmax()),
            ),
        )
        val loss = MeanSquaredError()
        val optimizer = RMSProp().apply { learningRate = 0.01 }
        model.compile(loss, optimizer)

        val x = MatrixOps.uniform(1, trainData)
        val y = MatrixOps.uniform(1, answerData)
        for (i in 0..40) {
            model.forward(x, y)
            model.backward()
        }
        val x2 = MatrixOps.uniform(1, trainData2)
        val y2 = MatrixOps.uniform(1, answerData2)
        for (i in 0..40) {
            model.forward(x2, y2)
            model.backward()
        }
        Log.i(
            "Prediction2",
            "predicted = ${ model.forward(
                MatrixOps.uniform(1,doubleArrayOf(500.0, -500.0))
                ,
                y,
            )}",
        )

        return Result.success()
    }

    private fun uploadUserData(): Boolean {
        // do upload work here
        return true
    }
}
