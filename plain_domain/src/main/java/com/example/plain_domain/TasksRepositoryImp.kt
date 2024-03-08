package com.example.plain_domain

import android.os.Build
import android.util.Log
import com.example.core.DB.Entities.ThemeEntity
import com.example.core.DB.GamesDatabase
import com.example.core.DB.ThemeDatabase
import com.example.plain_data.Task
import com.example.plain_data.TasksRepository
import com.example.plain_domain.mapper.learningIntervalEnd
import com.example.plain_domain.mapper.learningIntervalStart
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

class TasksRepositoryImp(private val gameDB: GamesDatabase, private val themeDB: ThemeDatabase): TasksRepository {
    override suspend fun getAllTasks(): List<Task?> {
        val learningMethods = gameDB.learningMethodDAO().getAllLearningMethod()
        val themes = themeDB.themeDao().getAllTheme()
        return themes.map{ theme ->

            val learningMethod = learningMethods.lastOrNull { it.themeType == theme.themeType }
            val startH = learningMethod?.timeLearningInterval?.learningIntervalStart()?: Random.nextInt(1,23)
            var endH = learningMethod?.timeLearningInterval?.learningIntervalEnd()?: Random.nextInt(startH,23)
            if (endH < startH ) {
                endH = 23
            }
            else if(endH == startH ) {
                endH += 1
            }

            val startD = learningMethod?.learningDay?: Random.nextInt(1,7)

            val startM = learningMethod?.timeLearningInterval?: Random.nextInt(1,12)

            var startDate = LocalDateTime.now()
            startDate =  startDate.withHour(startH)
            startDate =  startDate.plusDays(startD.toLong())
            startDate = startDate.plusMonths(startM.toLong())


            var endDate = LocalDateTime.now()
            endDate = endDate.withHour(endH)
            endDate = endDate.plusDays((startD+1).toLong())
            endDate = endDate.plusMonths((startM + 2).toLong())


             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                 Task(theme.themeName?:"", startDate , endDate)
             } else {
                 null
                // TODO("VERSION.SDK_INT < O")
             }
         }.toList()


    }
}