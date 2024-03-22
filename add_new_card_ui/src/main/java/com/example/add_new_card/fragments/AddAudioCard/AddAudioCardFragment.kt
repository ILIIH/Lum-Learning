package com.example.add_new_card.fragments.AddAudioCard

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.animation.ValueAnimator
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.adapters.AddCardAnimations
import com.example.add_new_card.adapters.AnswersAdapters
import com.example.add_new_card.databinding.FragmentAddAudioCardBinding
import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.example.add_new_card.navigation.AddNewCardNavigation
import com.example.core.domain.Scopes
import com.example.core.util.hideKeyboard
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import java.io.File
import java.io.FileOutputStream
import java.util.*

class AddAudioCardFragment : Fragment() {

    val adapter = AnswersAdapters()


    lateinit var mr: MediaRecorder
    private lateinit var animationManager : AddCardAnimations
    private lateinit var themeInfoProvider: ThemeInfoProvider

    val viewModel: AddAudioCardViewmodel by inject()
    private val navigator: AddNewCardNavigation by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: FragmentAddAudioCardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_audio_card, container, false)

        view.answers.adapter = adapter
        adapter.submitList(viewModel.getAnswers())

        animationManager.addTopBardCloseAnimation(view.topBar, view.nestedScrollView )


        view.questionInputText.addTextChangedListener {
            view.question.error = null
        }

        view.addNewAnswer.setOnClickListener {
            viewModel.addAnswer()
            adapter.submitList(viewModel.getAnswers())
            adapter.notifyItemInserted(viewModel.getAnswers().size)
        }

        viewModel._clickableStopBtn.onEach{ status ->
            if (status) {
                view.stopRecord.setBackgroundResource(R.drawable.baseline_stop_circle_24)
            } else {
                view.stopRecord.setBackgroundResource(R.drawable.baseline_stop_circle_non_clicable)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        val themeId = themeInfoProvider.getThemeId()

        mr = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            MediaRecorder(requireContext())
        } else {
            MediaRecorder()
        }
        if (ContextCompat.checkSelfPermission(requireContext(), RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            RequestPermissions()
        }

        view.stardRecord.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            viewModel.setStopBtnClickable()

            viewModel.addRecordPath(
                themeId
            ) { max ->
                val audioFile = File(requireActivity().cacheDir, "record$max")
                mr.setOutputFile(FileOutputStream(audioFile).fd)
                mr.prepare()
                mr.start()
            } }

            view.stopRecord.setOnClickListener {
            mr.stop()
            viewModel.setStopBtnNonClickable()
        }

        view.playAudio.setOnClickListener {
            viewModel.getAudioFilePath { max ->
                val mp = MediaPlayer.create(requireContext(), File(requireActivity().cacheDir, "record$max").toUri())
                mp.start()
            }
        }

        view.continueBtn.setOnClickListener {
            val answers = adapter.getAllAnswers()

            AlertDialog.Builder(context)
                .setTitle(getString(R.string.card_creation_title))
                .setMessage(getString(R.string.card_creation_message))
                .setPositiveButton(
                    getString(R.string.continue_creation),
                ) { _, _ ->
                    viewModel.addNewCard(
                        themeId = themeId,
                        question = view.question.editText!!.text.toString(),
                        answers = answers,
                        currentDate = SimpleDateFormat(getString(com.example.core.R.string.data_format)).format(Date()),
                    )
                    if(!adapter.validateAnswers()&& !validateCard(view)){
                        view.questionInputText.text?.clear()
                        initEmptyAnswers()
                    }
                }
                .setNegativeButton(
                    R.string.save_and_exit,
                ) { _, _ ->
                    if(!adapter.validateAnswers() && !validateCard(view)){
                        viewModel.addNewCard(
                            themeId = themeId,
                            question = view.question.editText!!.text.toString(),
                            currentDate = SimpleDateFormat(getString(com.example.core.R.string.data_format)).format(Date()),
                            answers = answers

                        )
                        hideKeyboard(activity as Activity)
                        navigator.saveNewCardAndExit(themeId)
                    }
                }
                .setIcon(R.drawable.baseline_credit_card_24)
                .show()
        }
        view.lifecycleOwner = viewLifecycleOwner
        return view.root
    }

    fun validateCard(view: FragmentAddAudioCardBinding): Boolean {
        return if(view.questionInputText.text.toString().isEmpty()){
            view.question.error = getString(R.string.enter_question)
            view.question.requestFocus()
            true
        }
        else {
            false
        }
    }

    private fun RequestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            1,
        )
    }
    private fun initEmptyAnswers() {
        val size = viewModel.getAnswers().size-1
        viewModel.reInitAnswers()
        adapter.submitList(viewModel.getAnswers())
        adapter.notifyItemRangeRemoved(1, size)
        adapter.notifyItemChanged(0)
    }

    override fun onAttach(context: Context) {
        val  scope = getKoin().getOrCreateScope(Scopes.ADD_NEW_CARD_SCOPE.scope, named(Scopes.ADD_NEW_CARD_SCOPE.scope))
        themeInfoProvider = scope.get<ThemeInfoProvider>()
        animationManager = scope.get<AddCardAnimations>()
        super.onAttach(context)
    }

}
