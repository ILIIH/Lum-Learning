package com.example.ai.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ai.base_ai_classes.*
import com.example.ai.base_ai_classes.Optimazer.RMSProp
import com.example.ai.base_ai_classes.loss.MeanSquaredError
import com.example.core.data.usecases.*
import org.koin.core.component.KoinComponent

class TimeLearningWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val saveTimeLearningPrediction: saveTimeLearningPrediction,
    private val getBestPerfomance: getBestPerfomanceMetric,
    private val SetRandomLearningMethodType: SetRandomLearningMethodType,
    private val getThemeLearningDataset: getThemeLearningDataset,
) :
    CoroutineWorker(context, workerParams), KoinComponent {

    override suspend fun doWork(): Result {
        val dataSet = downloadDataset()
        if (dataSet.size > 15) {
            val model = Model(
                inputDims = 6,
                layers = arrayOf(
                    Dense(12, ActivationOps.ReLU()),
                    Dense(6, ActivationOps.Sigmoid()),
                    Dense(12, ActivationOps.ReLU()),
                    Dense(6, ActivationOps.Sigmoid()),
                    Dense(12, ActivationOps.ReLU()),
                    Dense(8, ActivationOps.Softmax()),
                ),
            )
            val loss = MeanSquaredError()
            val optimizer = RMSProp().apply { learningRate = 0.01 }
            model.compile(loss, optimizer)

            for (i in 0..EPOCH) {
                dataSet.forEach { (inputData, outputData) ->
                    model.forward(inputData, outputData)
                    model.backward()
                }
            }

            val bestPerformance = getBestPerformanceMetrics()

            val prediction = model.predict(
                MatrixOps.uniform(
                    1,
                    doubleArrayOf(
                        0.0,
                        0.0,
                        0.0,
                        bestPerformance[3],
                        bestPerformance[4],
                        bestPerformance[5],
                    ),
                ),
            )

            saveMnemoType(prediction.returnFirstRow())
            return Result.success()
        } else {
            return Result.success()
        }
    }

    private suspend fun saveMnemoType(prediction: DoubleArray) {
             saveTimeLearningPrediction.execute(prediction)
    }

    private suspend fun getBestPerformanceMetrics(): DoubleArray {
        //                          K     D    Ch   T     Tw1   Tw2
        return getBestPerfomance.execute()
    }

    private suspend fun downloadDataset(): List<Pair<Matrix, Matrix>> {
        //  metrics                        K     D    Ch   T     Tw1   Tw2
        //  Theme type  1 = Theme Thesis ,2 = Question - Answer, 3 =Open Theme

        return getThemeLearningDataset.execute().map {
                (first, second) ->
            Pair(
                MatrixOps.uniform(1, first),
                MatrixOps.uniform(1, second),
            )
        }
    }
}
