package com.messaging.scrtm.trade.previewtrade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.messaging.scrtm.R
import com.messaging.scrtm.databinding.BottomsheetPreviewTradeBinding

class PreviewTradeBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding : BottomsheetPreviewTradeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetPreviewTradeBinding.inflate(inflater, container, false)
        return binding.root
    }

}