package com.example.plain_domain

import android.os.Build
import com.example.core.DB.Entities.ThemeEntity
import com.example.core.DB.GamesDatabase
import com.example.core.DB.ThemeDatabase
import com.example.plain_data.Task
import com.example.plain_data.TasksRepository
import com.example.plain_domain.mapper.learningIntervalEnd
import com.example.plain_domain.mapper.learningIntervalStart
import java.time.LocalDate
import kotlin.random.Random

class TasksRepositoryImp(private val gameDB: GamesDatabase, private val themeDB: ThemeDatabase): TasksRepository {
    override suspend fun getAllTasks(): List<Task?> {
        val learningMethods = gameDB.learningMethodDAO().getAllLearningMethod()
        val themes = themeDB.themeDao().getAllTheme()
        return themes.map{ theme ->

            val learningMethod = learningMethods.lastOrNull { it.themeType == theme.themeType }
            // val startH = learningMethod?.timeLearningInterval?.learningIntervalStart()
            // val endH = learningMethod?.timeLearningInterval?.learningIntervalEnd()
            val themeLearningInterval = learningMethod?.timeLearningInterval?.toLong() ?: Random.nextLong(1L,4L)
            // TODO: Change to new loacle date approach LocalDate.now().atTime(startH, 0)
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 Task(theme.themeName?:"",LocalDate.now() , LocalDate.now().plusMonths(themeLearningInterval))
             } else {
                 null
                // TODO("VERSION.SDK_INT < O")
             }
         }.toList()


    }
}