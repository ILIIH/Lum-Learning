package com.lum.ai.workers

import android.content.Context
import android.util.Log
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

class DayPredictionWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val saveDayPrediction: saveDayPrediction,
    private val getBestPerfomance: getBestPerfomanceMetric,
    private val SetRandomLearningMethodType: SetRandomLearningMethodType,
    private val getDayPredictionDataset: getDayPredictionDataset,
) :
    CoroutineWorker(context, workerParams), KoinComponent {

    override suspend fun doWork(): Result {
        val dataSet = downloadDataset()
        if(dataSet.size > 15)  {
            val model = Model(
                inputDims = 6,
                layers = arrayOf(
                    Dense(12, ActivationOps.ReLU()),
                    Dense(6, ActivationOps.Sigmoid()),
                    Dense(7, ActivationOps.Softmax()),
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

            saveDayPredictionType(prediction.returnFirstRow())
            return Result.success()
        }
        else return Result.success()
    }

    private suspend fun saveDayPredictionType(prediction: DoubleArray) {
           saveDayPrediction.execute(prediction)
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
        //  TypeOfMnemoTechnic 1 = Mon ,2 = Tue, 3 =We, 4 = Th, 5 = Fri, 6 = Sat, 7 = Sun

        return getDayPredictionDataset.execute().map {
                (first, second) ->
            Pair(
                MatrixOps.uniform(1, first),
                MatrixOps.uniform(1, second),
            )
        }
    }
}
