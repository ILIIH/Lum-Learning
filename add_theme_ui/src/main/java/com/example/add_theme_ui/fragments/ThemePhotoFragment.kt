package com.example.add_theme_ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.add_theme_ui.databinding.FragmentThemePhotoBinding
import com.example.add_theme_ui.viewModels.ThemeAddViewModel
import com.example.core.ui.BaseFragment
import org.koin.android.ext.android.inject

class ThemePhotoFragment : BaseFragment() {

    val viewModule: ThemeAddViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentThemePhotoBinding.inflate(inflater,container, false )
        view.continueBtn.setOnClickListener {
            viewModule.addTheme()
        }

        viewModule._validation.observe(requireActivity()) {
            showError(it)
        }

        viewModule._photo.observe(requireActivity()) {
            view.themeAvatar.setImageDrawable(null)
            view.themeAvatar.setImageBitmap(it)
        }

        view.themeAvatar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            launcher.launch(intent)
        }
        return view.root
    }

    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
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
                viewModule.setPhoto(getResizedBitmap(bitmap))
                viewModule.setPhotoURI(photoUri.toString())
            }
        }

    private fun getResizedBitmap(image: Bitmap, maxSize: Int = 1000): Bitmap {
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