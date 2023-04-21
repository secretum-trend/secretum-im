package com.messaging.scrtm.trade.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.messaging.scrtm.databinding.ViewSelectSendingBinding

class ViewSelectSending@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    val binding by lazy { ViewSelectSendingBinding.inflate(LayoutInflater.from(context), this, true) }

    init {
        binding.layoutToken.setOnClickListener {
            binding.layoutToken.isSelected = true
            binding.radioToken.isChecked = true

            binding.layoutNft.isSelected = false
            binding.radioNft.isChecked = false

        }

        binding.layoutNft.setOnClickListener {
            binding.layoutToken.isSelected = false
            binding.radioToken.isChecked = false

            binding.layoutNft.isSelected = true
            binding.radioNft.isChecked = true
        }

    }

}