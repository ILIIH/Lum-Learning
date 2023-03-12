package com.example.ai

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import org.jetbrains.kotlinx.dl.api.core.Sequential
import org.jetbrains.kotlinx.dl.api.core.WritingMode
import org.jetbrains.kotlinx.dl.api.core.activation.Activations
import org.jetbrains.kotlinx.dl.api.core.initializer.Constant
import org.jetbrains.kotlinx.dl.api.core.initializer.GlorotNormal
import org.jetbrains.kotlinx.dl.api.core.initializer.Zeros
import org.jetbrains.kotlinx.dl.api.core.layer.convolutional.Conv2D
import org.jetbrains.kotlinx.dl.api.core.layer.convolutional.ConvPadding
import org.jetbrains.kotlinx.dl.api.core.layer.core.Dense
import org.jetbrains.kotlinx.dl.api.core.layer.core.Input
import org.jetbrains.kotlinx.dl.api.core.layer.pooling.AvgPool2D
import org.jetbrains.kotlinx.dl.api.core.layer.reshaping.Flatten
import org.jetbrains.kotlinx.dl.api.core.loss.Losses
import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.core.optimizer.Adam
import org.jetbrains.kotlinx.dl.api.summary.printSummary
import org.jetbrains.kotlinx.dl.dataset.OnHeapDataset
import org.jetbrains.kotlinx.dl.dataset.embedded.NUMBER_OF_CLASSES
import java.io.File

private const val EPOCHS = 3
private const val TRAINING_BATCH_SIZE = 1000
private const val NUM_CHANNELS = 1L
private const val IMAGE_SIZE = 28L
private const val SEED = 12L
private const val TEST_BATCH_SIZE = 1000

private val model = Sequential.of(
    Input(
        IMAGE_SIZE,
        IMAGE_SIZE,
        NUM_CHANNELS
    ),
    Conv2D(
        filters = 6,
        kernelSize = intArrayOf(5, 5),
        strides = intArrayOf(1, 1, 1, 1),
        activation = Activations.Tanh,
        kernelInitializer = GlorotNormal(SEED),
        biasInitializer = Zeros(),
        padding = ConvPadding.SAME
    ),
    AvgPool2D(
        poolSize = intArrayOf(1, 2, 2, 1),
        strides = intArrayOf(1, 2, 2, 1),
        padding = ConvPadding.VALID
    ),
    Conv2D(
        filters = 16,
        kernelSize = intArrayOf(5, 5),
        strides = intArrayOf(1, 1, 1, 1),
        activation = Activations.Tanh,
        kernelInitializer = GlorotNormal(SEED),
        biasInitializer = Zeros(),
        padding = ConvPadding.SAME
    ),
    AvgPool2D(
        poolSize = intArrayOf(1, 2, 2, 1),
        strides = intArrayOf(1, 2, 2, 1),
        padding = ConvPadding.VALID
    ),
    Flatten(), // 3136
    Dense(
        outputSize = 120,
        activation = Activations.Tanh,
        kernelInitializer = GlorotNormal(SEED),
        biasInitializer = Constant(0.1f)
    ),
    Dense(
        outputSize = 84,
        activation = Activations.Tanh,
        kernelInitializer = GlorotNormal(SEED),
        biasInitializer = Constant(0.1f)
    ),
    Dense(
        outputSize = NUMBER_OF_CLASSES,
        activation = Activations.Linear,
        kernelInitializer = GlorotNormal(SEED),
        biasInitializer = Constant(0.1f)
    )
)

class AiTainingWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val trainData = floatArrayOf(22.2F, 21.2F, 20.2F, 21.2F)

        val (train, test) = Pair(
            OnHeapDataset.create(arrayOf(trainData), trainData),
            OnHeapDataset.create(arrayOf(trainData), trainData)
        )

        model.use {
            it.compile(
                optimizer = Adam(),
                loss = Losses.SOFT_MAX_CROSS_ENTROPY_WITH_LOGITS,
                metric = Metrics.ACCURACY
            )

            it.printSummary()

            // You can think of the training process as "fitting" the model to describe the given data :)
            it.fit(
                dataset = train,
                epochs = 10,
                batchSize = 100
            )

            val accuracy = it.evaluate(dataset = test, batchSize = 100).metrics[Metrics.ACCURACY]

            println("Accuracy: $accuracy")
            it.save(File("model/my_model"), writingMode = WritingMode.OVERRIDE)
        }

        return Result.success()
    }

    private fun uploadUserData(): Boolean {
        // do upload work here
        return true
    }
}
