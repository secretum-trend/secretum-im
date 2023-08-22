package com.messaging.scrtm.trade.choosenft

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.messaging.lib.core.utils.compat.getParcelableExtraCompat
import com.messaging.scrtm.core.utils.SolanaUtils.isNFT
import com.messaging.scrtm.data.solana.entity.Result
import com.messaging.scrtm.databinding.ActivityChooseNftBinding
import com.messaging.scrtm.trade.creatingoffer.CreateOfferActivity
import com.messaging.scrtm.trade.creatingoffer.CreateOfferActivity.Companion.TYPE_TOKEN
import com.messaging.scrtm.trade.custom.GridSpacingItemDecoration

class ChooseNFTActivity : AppCompatActivity() {
    val binding by lazy { ActivityChooseNftBinding.inflate(layoutInflater) }
    private val listToken by lazy { intent.getParcelableExtraCompat<Result>(CreateOfferActivity.KEY_NFT)?.value?.filter { it.isNFT() } ?: emptyList() }
    private val typeToken by lazy { intent.getStringExtra(TYPE_TOKEN) }
    private val chooseNFTAdapter by lazy {
        ChooseNFTAdapter().apply {
            data = listToken
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initActions()
    }

    private fun initActions() {
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            chooseNFTAdapter.data = listToken.filter { it.isNFT() && it.account.data.parsed.info.mint.contains(text.toString(),true) }
        }

        binding.btnConfirm.setOnClickListener {
            val intent = Intent().apply {
                putExtra(NFT_SELECTED_KEY, chooseNFTAdapter.itemSelected)
                putExtra(TYPE_TOKEN, typeToken)
            }
            setResult(NFT_SELECTED_CODE, intent)
            finish()
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        binding.rvNft.apply {
            adapter = chooseNFTAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 0, 16, false))
        }
    }

    companion object {
        const val NFT_SELECTED_CODE = 123
        const val NFT_SELECTED_KEY = "NFT_SELECTED_KEY"
    }

}