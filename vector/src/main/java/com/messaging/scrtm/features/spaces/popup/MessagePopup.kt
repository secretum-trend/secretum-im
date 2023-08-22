package com.messaging.scrtm.features.spaces.popup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.messaging.scrtm.R
import com.messaging.scrtm.databinding.DialogMessageBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks

class MessagePopup(val title: String , val description : String) : DialogFragment() {

    lateinit var binding: DialogMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AlertDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DialogMessageBinding.inflate(inflater, container, false).also {
            binding = it
            return it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = title
        binding.tvDescription.text = description
        binding.btnClose.debouncedClicks {
            dismiss()
        }

    }

    fun View.debouncedClicks(onClicked: () -> Unit) {
        clicks()
            .onEach { onClicked() }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}