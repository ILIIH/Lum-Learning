package com.lum.ai.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lum.ai.base_ai_classes.*
import com.lum.ai.base_ai_classes.Optimazer.RMSProp
import com.lum.ai.base_ai_classes.loss.MeanSquaredError
import com.lum.core.data.usecases.*
import com.lum.ai.base_ai_classes.ActivationOps
import com.lum.ai.base_ai_classes.Dense
import com.lum.ai.base_ai_classes.Matrix
import com.lum.ai.base_ai_classes.MatrixOps
import com.lum.ai.base_ai_classes.Model
import org.koin.core.component.KoinComponent

class SpacedFactorWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val saveSpacedFactor: saveSpacedFactor,
    private val getBestPerfomance: getBestPerfomanceMetric,
    private val SetRandomLearningMethodType: SetRandomLearningMethodType,
    private val getSpacedFactorDataset: getSpacedFactorDataset,
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
                    Dense(3, ActivationOps.Sigmoid()),
                    Dense(5, ActivationOps.Softmax()),
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

            saveSpacedFactorType(prediction.returnFirstRow())
            return Result.success()
        } else {
            return Result.success()
        }
    }

    private suspend fun saveSpacedFactorType(prediction: DoubleArray) {
        saveSpacedFactor.execute(prediction)
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
        //  SpacedRepetitionFactor 1 = 1 ,2 =1.2, 3 =1.4, 4 = 1.8, 5 = 2

        return getSpacedFactorDataset.execute().map { (first, second) ->
            Pair(
                MatrixOps.uniform(1, first),
                MatrixOps.uniform(1, second),
            )
        }
    }
}
