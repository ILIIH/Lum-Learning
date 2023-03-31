package com.example.memorizable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.work.*
import com.example.ai.workers.*
import com.example.core.data.usecases.SetRandomLearningMethodType
import com.example.navigation.CoreNavigation
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val navigator: CoreNavigation by inject()
    val initMethodType: SetRandomLearningMethodType by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            initMethodType.execute()
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navigator.bind(navController)

        initWorkers()
    }

    fun initWorkers() {
        val uploadWorkRequest = OneTimeWorkRequestBuilder<AiTainingWorker>().build()
        WorkManager.getInstance().enqueue(uploadWorkRequest)

        val mnemoTypeWorkRequest = OneTimeWorkRequestBuilder<MnemoTypeWorker>().build()
        WorkManager.getInstance().enqueue(mnemoTypeWorkRequest)

        val spacedFactorWorkRequest = OneTimeWorkRequestBuilder<SpacedFactorWorker>().build()
        WorkManager.getInstance().enqueue(spacedFactorWorkRequest)

        val dayPredictionWorkRequest = OneTimeWorkRequestBuilder<DayPredictionWorker>().build()
        WorkManager.getInstance().enqueue(dayPredictionWorkRequest)

        val themeTypeWorkRequest = OneTimeWorkRequestBuilder<ThemeTypeWorker>().build()
        WorkManager.getInstance().enqueue(themeTypeWorkRequest)

        val timeLearningWorkerRequest = OneTimeWorkRequestBuilder<TimeLearningWorker>().build()
        WorkManager.getInstance().enqueue(timeLearningWorkerRequest)
    }
}
