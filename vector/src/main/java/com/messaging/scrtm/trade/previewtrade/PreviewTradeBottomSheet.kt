package com.messaging.scrtm.trade.previewtrade

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.auth.type.CreateOfferPayload
import com.auth.type.Trade
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.messaging.lib.ui.styles.dialogs.MaterialProgressDialog
import com.messaging.scrtm.R
import com.messaging.scrtm.core.utils.Resource
import com.messaging.scrtm.core.utils.showToast
import com.messaging.scrtm.data.SessionPref
import com.messaging.scrtm.data.trade.entity.CreateOfferPayloadModel
import com.messaging.scrtm.databinding.BottomsheetPreviewTradeBinding
import com.messaging.scrtm.features.onboarding.usecase.MobileWalletAdapterUseCase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class PreviewTradeBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: BottomsheetPreviewTradeBinding
    private var progress: AlertDialog? = null

    val viewModel by viewModels<PreviewTradeViewModel>()
    private val recipientUserId by lazy { arguments?.getInt(USER_ID) ?: 0 }
    private val createOfferPayload by lazy {
        arguments?.getSerializable(
            OFFER_PAYLOAD,
            CreateOfferPayloadModel::class.java
        )

    }

    var action: ((Boolean,Int?) -> Unit)? = null

    @Inject
    lateinit var sessionPref: SessionPref

    private val mwaLauncher = registerForActivityResult(
        MobileWalletAdapterUseCase.StartMobileWalletAdapterActivity(lifecycle)
    ) {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomsheetPreviewTradeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initActions()
        observingValues()
    }

    private fun initViews() {
        binding.apply {
            tvAmount.text = createOfferPayload?.trade?.sending_token_amount
            tvToken.text = createOfferPayload?.trade?.sending_token_address

            tvAmount2.text = createOfferPayload?.trade?.recipient_token_amount
            tvToken2.text = createOfferPayload?.trade?.recipient_token_address
        }
    }

    private fun initActions() {
        binding.btnOk.setOnClickListener {
            viewModel.getNonce().observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        it.data?.nonce?.let { it1 ->
                            viewModel.signNonce(
                                mwaLauncher,
                                it1
                            )
                        }
                    }
                    Resource.Status.ERROR -> {}
                    Resource.Status.LOADING -> {}
                }
            }
        }

        binding.tvTo.setOnLongClickListener {
            viewModel.signNonce(mwaLauncher, binding.tvTo.text.toString().toInt())
            true
        }
    }

    private fun observingValues() {
        viewModel.apply {
            message.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
            }
            signature.observe(viewLifecycleOwner) { signature ->
                val createOfferPayload = CreateOfferPayload(
                    Trade(
                        createOfferPayload!!.trade.recipient_address,
                        createOfferPayload!!.trade.recipient_token_address,
                        createOfferPayload!!.trade.recipient_token_amount,
                        createOfferPayload!!.trade.recipient_user_id,
                        createOfferPayload!!.trade.sending_token_address,
                        createOfferPayload!!.trade.sending_token_amount,
                    ),
                    createOfferPayload!!.publicKey,
                    signature.toString()
                )

                binding.tvTo.setText(signature.toString())
                Timber.tag("signature").d(signature.toString())
                Timber.tag("signature").d(createOfferPayload.toString())

                viewModel.createOffer(createOfferPayload).observe(viewLifecycleOwner) {
                    when (it.status) {
                        Resource.Status.ERROR -> {
                            dismissLoadingDialog()
                            requireActivity().showToast(it.message.toString())
                            action?.invoke(false, null)

                        }
                        Resource.Status.SUCCESS -> {
                            dismissLoadingDialog()
                            dismiss()
//                            viewModel.getTradeByPk(it.data?.createOffer?.trade_id)
                            action?.invoke(it.data?.createOffer?.trade_id.toString().isNotEmpty(),it.data?.createOffer?.trade_id )
                        }
                        Resource.Status.LOADING -> {
                            showLoadingDialog()
                        }
                    }
                }
            }
        }
    }

    private fun showLoadingDialog(message: CharSequence? = null) {
        progress?.dismiss()
        progress = MaterialProgressDialog(requireContext())
            .show(message ?: getString(R.string.please_wait))
    }

    private fun dismissLoadingDialog() {
        progress?.dismiss()
    }


    companion object {
        private const val OFFER_PAYLOAD = "offer_payload"
        private const val USER_ID = "user_id"
        fun newInstance(
            createOfferPayload: CreateOfferPayloadModel,
            userId: Int,
        ): PreviewTradeBottomSheet {
            val args = Bundle().apply {
                putSerializable(OFFER_PAYLOAD, createOfferPayload)
                putInt(USER_ID, userId)
            }
            val fragment = PreviewTradeBottomSheet()
            fragment.arguments = args
            return fragment
        }

    }
}