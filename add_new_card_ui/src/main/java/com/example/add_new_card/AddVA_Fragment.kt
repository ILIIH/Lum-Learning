package com.example.add_new_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.add_new_card.databinding.FragmentAddVABinding

class AddVA_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAddVABinding.inflate(inflater, container, false)
        return view.root
    }
}
