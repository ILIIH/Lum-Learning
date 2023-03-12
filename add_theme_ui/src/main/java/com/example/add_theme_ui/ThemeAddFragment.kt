
package com.example.add_theme_ui

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.add_theme_ui.databinding.FragmentThemeAddBinding
import org.koin.android.ext.android.inject

class ThemeAddFragment : Fragment() {

    val viewModule: ThemeAddViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentThemeAddBinding.inflate(inflater, container, false)

        view.continueBtn.setOnClickListener {
            viewModule.addTheme(
                view.themeTitleTextInput.text.toString(),
                view.yesrsExpirenceTextInput.text.toString().toInt(),
                view.themeImportanceSpinner.selectedItem.toString(),
                view.themeTypeSpinner.selectedItem.toString()
            )
        }

        viewModule._photo.observe(requireActivity()) {
            view.themeAvatar.setImageBitmap(it)
        }

        view.themeAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher.launch(intent)
        }

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.theme_importance,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.themeImportanceSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.theme_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            view.themeTypeSpinner.adapter = adapter
        }

        return view.root
    }

    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK &&
                result.data != null
            ) {
                val photoUri: Uri = result.data!!.data!!
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, photoUri))
                } else {
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, photoUri)
                }
                viewModule.setPhoto(bitmap)
                viewModule.setPhotoURI(photoUri.toString())
            }
        }
}
