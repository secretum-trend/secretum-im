package com.messaging.scrtm.trade.creatingoffer

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.messaging.scrtm.databinding.ActivityCreateOfferBinding
import com.messaging.scrtm.features.themes.ActivityOtherThemes
import com.messaging.scrtm.features.themes.ThemeUtils
import com.messaging.scrtm.trade.choosenft.ChooseNFTActivity
import com.messaging.scrtm.trade.previewtrade.PreviewTradeBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateOfferActivity : AppCompatActivity() {
    val binding by lazy { ActivityCreateOfferBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActions()
    }

    private fun initActions() {
        binding.tokenOrNft.layoutNft.setOnClickListener {
            val intent = Intent(this, ChooseNFTActivity::class.java)
            startActivity(intent)
        }

        binding.formSubmitButton.setOnClickListener {
            PreviewTradeBottomSheet().show(supportFragmentManager,null)
        }
    }

}