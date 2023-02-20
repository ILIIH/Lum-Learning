package com.example.plain_ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.plain_ui.customView.Task
import com.example.plain_ui.databinding.FragmentPlainBinding
import java.time.LocalDate

class PlainFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentPlainBinding.inflate(inflater, container, false)
        val now = LocalDate.now()
        view.plainView.setTasks(
            listOf(
                Task(
                    name = "Task 1",
                    dateStart = now.minusMonths(1),
                    dateEnd = now
                ),
                Task(
                    name = "Task 2 long name",
                    dateStart = now.minusWeeks(2),
                    dateEnd = now.plusWeeks(1)
                ),
                Task(
                    name = "Task 3",
                    dateStart = now.minusMonths(2),
                    dateEnd = now.plusMonths(2)
                ),
                Task(
                    name = "Some Task 4",
                    dateStart = now.plusWeeks(2),
                    dateEnd = now.plusMonths(2).plusWeeks(1)
                ),
                Task(
                    name = "Task 5",
                    dateStart = now.minusMonths(2).minusWeeks(1),
                    dateEnd = now.plusWeeks(1)
                )
            )
        )
        return view.root
    }
}
