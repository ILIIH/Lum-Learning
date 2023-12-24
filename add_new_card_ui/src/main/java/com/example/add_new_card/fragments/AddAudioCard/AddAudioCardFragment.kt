package com.example.add_new_card.fragments.AddAudioCard

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.adapters.AnswersAdapters
import com.example.add_new_card.databinding.FragmentAddAudioCardBinding
import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.example.add_new_card.util.hideKeyboard
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

class AddAudioCardFragment : Fragment() {

    val textFields = ArrayList<TextInputLayout>(13)
    val adapter = AnswersAdapters()

    lateinit var mr: MediaRecorder
    val mainViewModel: ThemeInfoProvider by inject()
    val viewModel: AddAudioCardViewmodel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAddAudioCardBinding.inflate(inflater, container, false)

        textFields.add(view.question)

        view.answers.adapter = adapter
        adapter.submitList(viewModel.answers)

        view.addNewAnswer.setOnClickListener {
            viewModel.addAnswer()
            adapter.submitList(viewModel.answers)
            adapter.notifyItemInserted(viewModel.answers.size)
        }

        viewModel._ciclableStopBtn.observe(viewLifecycleOwner) { status ->
            if (status) {
                view.stopRecord.setBackgroundResource(R.drawable.baseline_stop_circle_24)
            } else {
                view.stopRecord.setBackgroundResource(R.drawable.baseline_stop_circle_non_clicable)
            }
        }

        val themeId = mainViewModel.getThemeId()

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            mr = MediaRecorder(requireContext())
        } else {
            mr = MediaRecorder()
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            RequestPermissions()
        }

        view.stardRecord.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            viewModel.setStopBtnClickable()

            viewModel.addRecordPath {
                    max ->
                val audioFile = File(requireActivity().cacheDir, "record$max")
                mr.setOutputFile(FileOutputStream(audioFile).fd)
                mr.prepare()
                mr.start()
            }
        }

        view.stopRecord.setOnClickListener {
            mr.stop()
            viewModel.setStopBtnNonClickable()
        }

        view.playAudio.setOnClickListener {
            viewModel.getAudioFilePath {
                    max ->
                val mp = MediaPlayer.create(requireContext(), File(requireActivity().cacheDir, "record$max").toUri())
                mp.start()
            }
        }

        view.continueBtn.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Creation card")
                .setMessage("Do you want to continue creation or add this card and exit?")
                .setPositiveButton(
                    getString(R.string.continue_creation),
                ) { _, _ ->
                    viewModel.addNewCard(
                        themeId = themeId,
                        question = view.question.editText!!.text.toString(),
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Date()),
                        Date().month,
                    )
                }
                .setNegativeButton(
                    R.string.save_and_exit,
                ) { _, _ ->
                    viewModel.addNewCard(
                        themeId = themeId,
                        question = view.question.editText!!.text.toString(),
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Date()),
                        Date().month,
                    )
                    hideKeyboard(activity as Activity)

                    findNavController().popBackStack()
                }
                .setIcon(R.drawable.baseline_credit_card_24)
                .show()
        }
        return view.root
    }

    private fun RequestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf<String>(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE),
            1,
        )
    }
}
