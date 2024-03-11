package com.example.add_new_card.fragments.AddVisualCard

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.adapters.AnswersAdapters
import com.example.add_new_card.databinding.FragmentAddVisualCardBinding
import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.example.core.data.PhotoManager
import com.example.core.domain.ILError
import com.example.core.ui.MediaFragment
import com.example.core.util.hideKeyboard
import org.koin.android.ext.android.inject
import java.util.*

class AddVisualCardFragment : MediaFragment() {

    private val viewModel: AddVisualCardViewmodel by inject()
    private val mainViewModel: ThemeInfoProvider by inject()

    private val adapter = AnswersAdapters()
    private val photoManage: PhotoManager by inject()

    private lateinit var view: FragmentAddVisualCardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        view = DataBindingUtil.inflate(inflater, R.layout.fragment_add_visual_card, container, false)

        view.questionInputText.addTextChangedListener {
            view.question.error = null
        }

        view.answers.adapter = adapter
        adapter.submitList(viewModel.getAnswers())

        view.addNewAnswer.setOnClickListener {
            viewModel.addAnswer()
            adapter.submitList(viewModel.getAnswers())
            adapter.notifyItemInserted(viewModel.getAnswers().size)
        }

        val themeId = mainViewModel.getThemeId()

        view.addPhotoLayout.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val chooserIntent = Intent.createChooser(galleryIntent, getString(com.example.core.R.string.choose_an_option)).apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
            }
            launcher.launch(chooserIntent)
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
                        answers,
                        SimpleDateFormat(getString(com.example.core.R.string.data_format)).format(Date()),
                    )
                    if(!adapter.validateAnswers() && !validateCard(view)){
                        view.Title.requestFocus()
                        view.addPhoto.setImageDrawable(null)
                        view.questionInputText.text?.clear()
                        initEmptyAnswers()
                    }
                }
                .setNegativeButton(
                    R.string.save_and_exit,
                ) { _, _ ->
                    viewModel.addNewCard(
                        themeId = themeId,
                        question = view.question.editText!!.text.toString(),
                        answers,
                        SimpleDateFormat(getString(com.example.core.R.string.data_format)).format(Date()),
                    )
                    hideKeyboard(requireActivity())
                    findNavController().popBackStack()
                }
                .setIcon(R.drawable.baseline_credit_card_24)
                .show()
        }

        viewModel._photo.observe(requireActivity()) {
            it?.let { photo ->
                view.addPhoto.setImageDrawable(null)
                if(!photo.isRecycled) {
                    view.addPhoto.setImageBitmap(photo)
                }
            }
        }
        view.lifecycleOwner = viewLifecycleOwner
        return view.root
    }

    fun validateCard(view: FragmentAddVisualCardBinding): Boolean {
        var result = false
        if(view.questionInputText.text.toString().isEmpty()){
            view.question.error = getString(R.string.enter_question)
            view.question.requestFocus()
            result = true
        }

        if(viewModel._photo.value == null){
            showError(ILError.VALIDATION_PHOTO)
            result = true
        }
        return result
    }
    private fun initEmptyAnswers() {
        val size = viewModel.getAnswers().size-1
        viewModel.reInitAnswers()
        adapter.submitList(viewModel.getAnswers())
        adapter.notifyItemRangeRemoved(1, size)
        adapter.notifyItemChanged(0)
    }

    override fun saveGalleryImageData(uri: Uri) {
        val bitmap =photoManage.imageDecode(uri)
        viewModel.setPhoto(photoManage.getResizedBitmap(bitmap,1000))
        view.overlayPhoto.alpha = 0.5f
    }
    override fun saveCameraImageData(bitmap: Bitmap){
        viewModel.setPhoto(photoManage.getResizedBitmap(bitmap, 1000))
        view.overlayPhoto.alpha = 0.5f
    }
}
