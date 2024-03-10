package com.example.memorizable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.ai.workers.DayPredictionWorker
import com.example.ai.workers.MnemoTypeWorker
import com.example.ai.workers.SpacedFactorWorker
import com.example.ai.workers.ThemeTypeWorker
import com.example.ai.workers.TimeLearningWorker
import com.example.core.data.usecases.SetRandomLearningMethodType
import com.example.navigation.CoreNavigation
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigator: CoreNavigation by inject()
    private val initMethodType: SetRandomLearningMethodType by inject()
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

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        if(!resources.getStringArray(R.array.return_blocked_fragments_labels)
            .contains(navController.currentDestination?.label)){
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initWorkers() {
        val mnemoTypeWorkRequest = OneTimeWorkRequestBuilder<MnemoTypeWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(mnemoTypeWorkRequest)

        val spacedFactorWorkRequest = OneTimeWorkRequestBuilder<SpacedFactorWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(spacedFactorWorkRequest)

        val dayPredictionWorkRequest = OneTimeWorkRequestBuilder<DayPredictionWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(dayPredictionWorkRequest)

        val themeTypeWorkRequest = OneTimeWorkRequestBuilder<ThemeTypeWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(themeTypeWorkRequest)

        val timeLearningWorkerRequest = OneTimeWorkRequestBuilder<TimeLearningWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(timeLearningWorkerRequest)
    }
}
