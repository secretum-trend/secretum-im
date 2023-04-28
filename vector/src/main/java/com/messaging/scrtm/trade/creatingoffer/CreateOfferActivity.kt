package com.messaging.scrtm.trade.creatingoffer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.messaging.lib.core.utils.view.hide
import com.messaging.lib.core.utils.view.show
import com.messaging.scrtm.R
import com.messaging.scrtm.core.utils.Resource
import com.messaging.scrtm.data.SessionPref
import com.messaging.scrtm.data.solana.entity.Value
import com.messaging.scrtm.databinding.ActivityCreateOfferBinding
import com.messaging.scrtm.trade.choosenft.ChooseNFTActivity
import com.messaging.scrtm.trade.creatingoffer.adapter.SpinnerTokens
import com.messaging.scrtm.trade.custom.ViewSelectSending
import com.messaging.scrtm.trade.previewtrade.PreviewTradeBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CreateOfferActivity : AppCompatActivity() {
    val binding by lazy { ActivityCreateOfferBinding.inflate(layoutInflater) }
    val viewModel by viewModels<CreateOfferViewModel>()
    private val chooseNftResultContracts =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }

    @Inject
    lateinit var sessionPref: SessionPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initActions()
        observingValue()
    }

    private fun initViews() {
        binding.tvYourAddress.text = sessionPref.address
        viewModel.getTokensList(sessionPref.address)

        val data = intent.getStringExtra("userId")
        data?.toInt()?.let { viewModel.getPartnerAddress(it) }
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
            partner.observe(this@CreateOfferActivity) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.loading.hide()
                        try {
                            val address = it.data?.wallets?.first()?.address.toString()
                            binding.tvRecipientAddress.text = address
                            viewModel.getRecipientTokensList(address)
                        } catch (_: Throwable) {
                        }
                    }
                    Resource.Status.ERROR -> {
                        binding.loading.hide()
                    }
                    Resource.Status.LOADING -> binding.loading.show()
                }
            }

            yourToken.observe(this@CreateOfferActivity) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.loading.hide()
                        binding.tokenOrNft.spinerToken.adapter =
                            it.data?.result?.value?.let { value ->
                                SpinnerTokens(
                                    this@CreateOfferActivity,
                                    value
                                )
                            }
                    }
                    Resource.Status.ERROR -> {}
                    Resource.Status.LOADING ->    binding.loading.show()
                }

            }

            recipientToken.observe(this@CreateOfferActivity) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        binding.loading.hide()
                        binding.tokenOrNft2.spinerToken.adapter = it.data?.result?.value?.let { value ->
                            SpinnerTokens(
                                this@CreateOfferActivity,
                                value
                            )
                        }
                    }
                    Resource.Status.ERROR -> {
                        binding.loading.hide()
                    }
                    Resource.Status.LOADING -> binding.loading.show()
                }


            }
        }
    }

    private fun initActions() {
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.tokenOrNft.layoutNft.setOnClickListener {
            val intent = Intent(this, ChooseNFTActivity::class.java).apply {
            }
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


        binding.tokenOrNft.spinerToken.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedObject = parent.getItemAtPosition(position) as Value
                    binding.tokenOrNft.tvBalances.text = getString(R.string.unknown_token,selectedObject.account.data.parsed.info.tokenAmount.uiAmountString )
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        binding.tokenOrNft2.spinerToken.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedObject = parent.getItemAtPosition(position) as Value
                    binding.tokenOrNft2.tvBalances.text = getString(R.string.unknown_token,selectedObject.account.data.parsed.info.tokenAmount.uiAmountString )

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

    }


}