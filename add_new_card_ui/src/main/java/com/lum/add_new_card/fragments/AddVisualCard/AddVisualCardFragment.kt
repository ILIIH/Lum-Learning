package com.lum.add_new_card.fragments.AddVisualCard

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.lum.add_new_card.R
import com.lum.add_new_card.adapters.AddCardAnimations
import com.lum.add_new_card.adapters.AnswersAdapters
import com.lum.add_new_card.databinding.FragmentAddVisualCardBinding
import com.lum.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.lum.add_new_card.navigation.AddNewCardNavigation
import com.lum.core.data.PhotoManager
import com.lum.core.domain.ILError
import com.lum.core.domain.Scopes
import com.lum.core.ui.MediaFragment
import com.lum.core.util.hideKeyboard
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.*

class AddVisualCardFragment : MediaFragment() {

    private val cardViewModel: AddVisualCardViewmodel by viewModel()
    private lateinit var themeInfoProvider: ThemeInfoProvider
    private lateinit var animationManager : AddCardAnimations

    private val adapter = AnswersAdapters()
    private val photoManage: PhotoManager by inject()
    private val navigator: AddNewCardNavigation by inject()

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

        animationManager.addTopBardCloseAnimation(view.topBar, view.nestedScrollView )

        view.answers.adapter = adapter
        adapter.submitList(cardViewModel.getAnswers())

        view.addNewAnswer.setOnClickListener {
            cardViewModel.addAnswer()
            adapter.submitList(cardViewModel.getAnswers())
            adapter.notifyItemInserted(cardViewModel.getAnswers().size)
        }

        val themeId = themeInfoProvider.getThemeId()

        view.addPhotoLayout.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val chooserIntent = Intent.createChooser(galleryIntent, getString(com.lum.core.R.string.choose_an_option)).apply {
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
                    cardViewModel.addNewCard(
                        themeId = themeId,
                        question = view.question.editText!!.text.toString(),
                        answers,
                        SimpleDateFormat(getString(com.lum.core.R.string.data_format)).format(Date()),
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
                    if(!adapter.validateAnswers() && !validateCard(view)){
                        cardViewModel.addNewCard(
                            themeId = themeId,
                            question = view.question.editText!!.text.toString(),
                            answers,
                            SimpleDateFormat(getString(com.lum.core.R.string.data_format)).format(Date()),
                        )
                        hideKeyboard(requireActivity())
                        navigator.saveNewCardAndExit(themeId)
                    }
                }
                .setIcon(R.drawable.baseline_credit_card_24)
                .show()
        }

        cardViewModel._photo.onEach {
            it?.let { photo ->
                view.addPhoto.setImageDrawable(null)
                if(!photo.isRecycled) {
                    view.addPhoto.setImageBitmap(photo)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        view.lifecycleOwner = viewLifecycleOwner
        return view.root
    }

    override fun onAttach(context: Context) {
        val  scope = getKoin().getOrCreateScope(Scopes.ADD_NEW_CARD_SCOPE.scope, named(Scopes.ADD_NEW_CARD_SCOPE.scope))
        animationManager = scope.get<AddCardAnimations>()
        themeInfoProvider = scope.get<ThemeInfoProvider>()
        super.onAttach(context)
    }

    fun validateCard(view: FragmentAddVisualCardBinding): Boolean {
        var result = false
        if(view.questionInputText.text.toString().isEmpty()){
            view.question.error = getString(R.string.enter_question)
            view.question.requestFocus()
            result = true
        }

        if(cardViewModel._photo.value == null){
            showError(ILError.VALIDATION_PHOTO)
            result = true
        }
        return result
    }
    private fun initEmptyAnswers() {
        val size = cardViewModel.getAnswers().size-1
        cardViewModel.reInitAnswers()
        adapter.submitList(cardViewModel.getAnswers())
        adapter.notifyItemRangeRemoved(1, size)
        adapter.notifyItemChanged(0)
    }

    override fun saveGalleryImageData(uri: Uri) {
        val bitmap =photoManage.imageDecode(uri)
        cardViewModel.setPhoto(photoManage.getResizedBitmap(bitmap,1000))
        view.overlayPhoto.alpha = 0.5f
    }
    override fun saveCameraImageData(bitmap: Bitmap){
        cardViewModel.setPhoto(photoManage.getResizedBitmap(bitmap, 1000))
        view.overlayPhoto.alpha = 0.5f
    }
}
