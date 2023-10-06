package com.example.add_new_card.fragments.AddVisualCard

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.databinding.FragmentAddVisualCardBinding
import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.example.add_new_card.util.hideKeyboard
import com.example.add_new_card_data.model.Answer
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList

class AddVisualCardFragment : Fragment() {

    val textFields = ArrayList<TextInputLayout>(13)
    val viewModel: AddVisualCardViewmodel by inject()
    val mainViewModel: ThemeInfoProvider by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAddVisualCardBinding.inflate(inflater, container, false)
        view.answer1.editText!!.text

        textFields.add(view.question)
        textFields.add(view.answer1Discription)
        textFields.add(view.answer1)
        textFields.add(view.answer2Discription)
        textFields.add(view.answer2)
        textFields.add(view.answer3Discription)
        textFields.add(view.answer3)
        textFields.add(view.answer4Discription)
        textFields.add(view.answer4)

        val themeId = mainViewModel.getThemeId()

        view.addPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher.launch(intent)
        }

        view.continueBtn.setOnClickListener {
            var isAnyFieldEmpty = false
            textFields.forEach {
                if (it.editText!!.text.isEmpty()) {
                    isAnyFieldEmpty = true
                    it.apply {
                        boxStrokeColor = Color.parseColor("#FF0000")
                    }
                    it.requestFocus()
                }
            }

            if (!isAnyFieldEmpty) {
                val answers = listOf<Answer>(
                    Answer(
                        answer = view.answer1.editText!!.text.toString(),
                        description = view.answer1Discription.editText!!.text.toString(),
                        correct = view.answer1True.isChecked,
                    ),
                    Answer(
                        answer = view.answer2.editText!!.text.toString(),
                        description = view.answer2Discription.editText!!.text.toString(),
                        correct = view.answer2True.isChecked,
                    ),
                    Answer(
                        answer = view.answer3.editText!!.text.toString(),
                        description = view.answer3Discription.editText!!.text.toString(),
                        correct = view.answer3True.isChecked,
                    ),
                    Answer(
                        answer = view.answer4.editText!!.text.toString(),
                        description = view.answer4Discription.editText!!.text.toString(),
                        correct = view.answer4True.isChecked,
                    ),
                )

                AlertDialog.Builder(context)
                    .setTitle("Creation card")
                    .setMessage("Do you want to continue creation or add this card and exit?")
                    .setPositiveButton(
                        getString(R.string.continue_creation),
                    ) { _, _ ->
                        viewModel.addNewCard(
                            themeId = themeId,
                            question = view.question.editText!!.text.toString(),
                            answers,
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Date()),
                            monthNumber =  Date().month
                        )
                        textFields.forEach {
                            it.editText?.text?.clear()
                        }
                        view.Title.requestFocus()
                        view.addPhoto.setBackgroundResource(R.drawable.baseline_image_search_24)
                    }
                    .setNegativeButton(
                        R.string.save_and_exit,
                    ) { _, _ ->
                        viewModel.addNewCard(
                            themeId = themeId,
                            question = view.question.editText!!.text.toString(),
                            answers,
                            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Date()),
                            monthNumber =  Date().month
                        )
                        hideKeyboard(requireActivity())
                        findNavController().popBackStack()
                    }
                    .setIcon(R.drawable.baseline_credit_card_24)
                    .show()
            }
        }

        viewModel._photo.observe(requireActivity()) {
            view.addPhoto.setImageDrawable(null)
            view.addPhoto.setImageBitmap(it)
        }
        return view.root
    }

    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult(),
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK &&
                result.data != null
            ) {
                val photoUri: Uri = result.data!!.data!!
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            requireContext().contentResolver,
                            photoUri,
                        ),
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, photoUri)
                }
                viewModel.setPhoto(getResizedBitmap(bitmap, 1000)!!)
            }
        }
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap? {
        var width: Int = image.getWidth()
        var height: Int = image.getHeight()
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }
}
