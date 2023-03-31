package com.example.ai.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ai.base_ai_classes.*
import com.example.ai.base_ai_classes.Optimazer.RMSProp
import com.example.ai.base_ai_classes.loss.MeanSquaredError
import com.example.core.data.usecases.*
import org.koin.core.component.KoinComponent

class ThemeTypeWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val saveThemeTypePrediction: saveThemeTypePrediction,
    private val getBestPerfomance: getBestPerfomanceMetric,
    private val SetRandomLearningMethodType: SetRandomLearningMethodType,
    private val getThemeTypeDataset: getThemeTypeDataset,
) : CoroutineWorker(context, workerParams), KoinComponent {

    override suspend fun doWork(): Result {
        Log.i(
            "Prediction2",
            "ThemeTypeWorker started}",
        )
        val dataSet = downloadDataset()
        if (dataSet.size < 15) {
            setRandomType()
            Log.i(
                "Prediction2",
                "ThemeTypeWorker ended}",
            )
            return Result.success()
        } else {
            val model = Model(
                inputDims = 6,
                layers = arrayOf(
                    Dense(12, ActivationOps.ReLU()),
                    Dense(6, ActivationOps.Sigmoid()),
                    Dense(3, ActivationOps.Softmax()),
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
            Log.i(
                "Prediction2",
                "ThemeTypeWorker ended}",
            )
            return Result.success()
        }
    }

    private suspend fun saveMnemoType(prediction: DoubleArray) {
       // saveThemeTypePrediction.execute(prediction)
    }

    private suspend fun getBestPerformanceMetrics(): DoubleArray {
        //                          K     D    Ch   T     Tw1   Tw2
        return getBestPerfomance.execute()
    }

    private suspend fun setRandomType() {
        SetRandomLearningMethodType.execute()
    }

    private suspend fun downloadDataset(): List<Pair<Matrix, Matrix>> {
        //  metrics                        K     D    Ch   T     Tw1   Tw2
        //  Theme type  1 = Theme Thesis ,2 = Question - Answer, 3 =Open Theme

        return getThemeTypeDataset.execute().map {
                (first, second) ->
            Pair(
                MatrixOps.uniform(1, first),
                MatrixOps.uniform(1, second),
            )
        }
    }
}
