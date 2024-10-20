package com.lum.ask_answer_ui.fragments

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lum.core.R
import com.lum.ask_answer_ui.fragments.DAFragment.CardEndsDialog
import com.lum.ask_answer_ui.viewModel.cardProvider
import com.lum.core.domain.Scopes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import java.util.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lum.add_new_card_data.model.LearningCardDomain
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color

@Composable
fun LearningCardScreen(
    cardProvider: cardProvider,
    onAnswerSelected: (Boolean, LearningCardDomain?) -> Unit,
    onTimerFinished: (LearningCardDomain?) -> Unit
) {
    val currentCard by cardProvider.getCurrentCard().collectAsState(initial = null)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = colorResource(id = R.color.primary),
                    shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
                Text(
                    text = (currentCard as? LearningCardDomain)?.question?: String() ,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 35.dp, end = 20.dp, bottom = 30.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 24.sp
                )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (currentCard != null) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items((currentCard as LearningCardDomain).answers) { answer ->
                    AnswerItem(answer = answer.answer, onAnswerSelected = { })
                }
            }

        }

        TimeView(modifier = Modifier.padding(24.dp)) {
            onTimerFinished(currentCard as LearningCardDomain)
        }
    }
}
@Composable
fun AnswerItem( onAnswerSelected: () -> Unit , answer: String) {
    Box(
    ) {
        BasicText(text = answer)
    }
}

@Composable
fun TimeView(modifier: Modifier, onTimerFinished: () -> Unit) {
    // Placeholder for custom timeView logic
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(text = "Time View Placeholder")
        // Trigger onTimerFinished when the timer ends
    }
}


class LearningCardFragment : Fragment() {

    lateinit var cardProvider: cardProvider
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Returning a ComposeView instead of ViewBinding
        return ComposeView(requireContext()).apply {
            setContent {
                    LearningCardScreen(
                        cardProvider = cardProvider,  // Pass the card provider here
                        onAnswerSelected = { isCorrect, currentCard ->
                            val begin = System.nanoTime()
                            val result = isCorrect

                            // Handle answer selected logic (update card stats and metrics)
                            lifecycleScope.launch {
                                if(currentCard != null)
                                    cardProvider.updateCardStatsAndMetrics(
                                        currentDate = Date(),
                                        cardDateCreation = SimpleDateFormat(getString(R.string.data_format))
                                            .parse(currentCard.dateCreation),
                                        result = result,
                                        time = begin - System.nanoTime(),
                                        cardId = currentCard.id,
                                    )

                                goToNextCard()  // Navigate to the next card
                            }
                        },
                        onTimerFinished = { currentCard ->
                            // Logic for timer finishing and updating card stats
                            lifecycleScope.launch {
                                if (currentCard != null)
                                    cardProvider.updateCardStatsAndMetrics(
                                        currentDate = Date(),
                                        cardDateCreation = SimpleDateFormat(getString(R.string.data_format))
                                            .parse(currentCard.dateCreation),
                                        result = false,
                                        time = 0L,
                                        cardId = currentCard.id,
                                    )

                                if (isResumed) {
                                    CardEndsDialog(getString(com.lum.ask_answer_ui.R.string.tile_ended), ::goToNextCard)
                                        .show(parentFragmentManager, CardEndsDialog.DESCRIPTION_DIALOG_TAG)
                                }
                            }
                        }
                    )
                }
            }
        }
    fun goToNextCard() {
        cardProvider.goToNextCard()
        lifecycleScope.launchWhenResumed {
            delay(1000)
            findNavController().popBackStack()
        }
    }

    override fun onAttach(context: Context) {
        val  scope = getKoin().getOrCreateScope(Scopes.GAME_SCOPE.scope, named(Scopes.GAME_SCOPE.scope))
        cardProvider = scope.get<cardProvider>()
        super.onAttach(context)
    }
}