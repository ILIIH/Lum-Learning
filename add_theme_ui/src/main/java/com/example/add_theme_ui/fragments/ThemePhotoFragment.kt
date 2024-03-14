package com.example.add_theme_ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.core.R
import com.example.add_theme_ui.databinding.FragmentThemePhotoBinding
import com.example.add_theme_ui.viewModels.ThemeAddViewModel
import com.example.core.data.PhotoManager
import com.example.core.ui.MediaFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ThemePhotoFragment : MediaFragment() {

    private val viewModule: ThemeAddViewModel by sharedViewModel()
    private val photoManage: PhotoManager by inject()

    private lateinit var view : FragmentThemePhotoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentThemePhotoBinding.inflate(inflater,container, false )
        view.continueBtn.setOnClickListener { viewModule.addTheme() }

        viewModule._validation.observe(requireActivity()) {
            showError(it)
        }

        viewModule._photo.observe(requireActivity()) {
            view.themePhoto.setImageDrawable(null)
            if(!it.isRecycled){
                view.themePhoto.setImageBitmap(it)
            }
        }

        view.themePhoto.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.choose_an_option)).apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
            }
            launcher.launch(chooserIntent)
        }
        return view.root
    }

    override fun saveGalleryImageData(uri: Uri) {
        val bitmap =photoManage.imageDecode(uri)
        viewModule.setPhoto(photoManage.getResizedBitmap(bitmap,1000))
        view.overlayPhoto.alpha = 0.8f
        viewModule.setPhotoURI(uri.toString())
    }
    override fun saveCameraImageData(bitmap: Bitmap){
        viewModule.setPhoto(photoManage.getResizedBitmap(bitmap, 1000))
        view.overlayPhoto.alpha = 0.8f
        val photoUri =  photoManage.saveImageToGallery(bitmap)
        if(photoUri != null) {
            viewModule.setPhotoURI(photoUri.toString())
        }
    }



}
