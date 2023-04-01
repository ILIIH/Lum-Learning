package com.example.add_new_card.fragments.AddAudioCard

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.add_new_card.databinding.FragmentAddAudioCardBinding


class AddAudioCardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAddAudioCardBinding.inflate(inflater, container, false)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) RequestPermissions()
        return view.root
    }

    private fun RequestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf<String>(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE),
            1
        )
    }
}
