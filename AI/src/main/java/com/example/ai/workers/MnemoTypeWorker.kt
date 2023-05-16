package com.example.ai.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ai.base_ai_classes.*
import com.example.ai.base_ai_classes.Optimazer.RMSProp
import com.example.ai.base_ai_classes.loss.MeanSquaredError
import com.example.core.data.usecases.SetRandomLearningMethodType
import com.example.core.data.usecases.getBestPerfomanceMetric
import com.example.core.data.usecases.getMnemoTypeDataset
import com.example.core.data.usecases.saveMnemoType
import org.koin.core.component.KoinComponent

const val EPOCH = 50

class MnemoTypeWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val saveMnemoType: saveMnemoType,
    private val getBestPerfomance: getBestPerfomanceMetric,
    private val SetRandomLearningMethodType: SetRandomLearningMethodType,
    private val getMnemoTypeDataset: getMnemoTypeDataset,
) :
    CoroutineWorker(context, workerParams), KoinComponent {

    override suspend fun doWork(): Result {
        Log.i(
            "Prediction2",
            "Worker started}",
        )
        val dataSet = downloadDataset()
        if (dataSet.size > 15) {
            val model = Model(
                inputDims = 6,
                layers = arrayOf(
                    Dense(12, ActivationOps.ReLU()),
                    Dense(6, ActivationOps.Sigmoid()),
                    Dense(6, ActivationOps.Softmax()),
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
                "Worker ended",
            )
            return Result.success()
        } else {
            return Result.success()
        }
    }

    private suspend fun saveMnemoType(prediction: DoubleArray) {
              saveMnemoType.execute(prediction)
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
        //  TypeOfMnemoTechnic 1 = LM ,2 =M, 3 =WA, 4 = SA, 5 = WrA, 6 =C

        return getMnemoTypeDataset.execute().map {
                (first, second) ->
            Pair(
                MatrixOps.uniform(1, first),
                MatrixOps.uniform(1, second),
            )
        }
    }
}
