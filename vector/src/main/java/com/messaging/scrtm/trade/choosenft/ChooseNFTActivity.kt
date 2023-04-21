package com.messaging.scrtm.trade.choosenft

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.messaging.scrtm.databinding.ActivityChooseNftBinding
import com.messaging.scrtm.trade.custom.GridSpacingItemDecoration

class ChooseNFTActivity : AppCompatActivity() {
    val binding by lazy { ActivityChooseNftBinding.inflate(layoutInflater) }
    val chooseNFTAdapter  by lazy { ChooseNFTAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.rvNft.apply {
            adapter = chooseNFTAdapter
            addItemDecoration(GridSpacingItemDecoration(2, 0, 16, false))
        }
    }


}