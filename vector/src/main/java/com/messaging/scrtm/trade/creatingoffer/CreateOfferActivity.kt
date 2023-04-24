package com.messaging.scrtm.trade.creatingoffer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.messaging.lib.core.utils.view.hide
import com.messaging.lib.core.utils.view.show
import com.messaging.scrtm.R
import com.messaging.scrtm.databinding.ActivityCreateOfferBinding
import com.messaging.scrtm.trade.choosenft.ChooseNFTActivity
import com.messaging.scrtm.trade.custom.ViewSelectSending
import com.messaging.scrtm.trade.previewtrade.PreviewTradeBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateOfferActivity : AppCompatActivity() {
    val binding by lazy { ActivityCreateOfferBinding.inflate(layoutInflater) }
    val viewModel by viewModels<CreateOfferViewModel>()
    val chooseNftResultContracts = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActions()
        observingValue()
    }

    private fun observingValue() {
        viewModel.apply {
            attentionExpands.observe(this@CreateOfferActivity) {
                if (it) {
                    binding.msgAttention.visibility = View.VISIBLE
                    binding.icArrow.setImageResource(R.drawable.ic_arrow_top)
                } else {
                    binding.msgAttention.visibility = View.GONE
                    binding.icArrow.setImageResource(R.drawable.ic_arrow_bottom)
                }
            }

            sendingType.observe(this@CreateOfferActivity) {
                when (it) {
                    ViewSelectSending.TypeSending.Token -> {
                        binding.tokenOrNft.layoutToken.show()
                        binding.tokenOrNft.layoutNft.hide()
                    }
                    ViewSelectSending.TypeSending.Nft -> {
                        binding.tokenOrNft.layoutToken.hide()
                        binding.tokenOrNft.layoutNft.show()
                    }
                    else -> {}
                }
            }
            receiveType.observe(this@CreateOfferActivity) {
                when (it) {
                    ViewSelectSending.TypeSending.Token -> {
                        binding.tokenOrNft2.layoutToken.show()
                        binding.tokenOrNft2.layoutNft.hide()
                    }
                    ViewSelectSending.TypeSending.Nft -> {
                        binding.tokenOrNft2.layoutToken.hide()
                        binding.tokenOrNft2.layoutNft.show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initActions() {
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.tokenOrNft.layoutNft.setOnClickListener {
            val intent = Intent(this, ChooseNFTActivity::class.java)
            chooseNftResultContracts.launch(intent)
        }

        binding.formSubmitButton.setOnClickListener {
            PreviewTradeBottomSheet().show(supportFragmentManager, null)
        }

        binding.icArrow.setOnClickListener {
            viewModel.attentionExpands.value = !viewModel.attentionExpands.value!!
        }

        binding.selectSending.onClickListener = {
            viewModel.sendingType.value = it
        }
    }

}