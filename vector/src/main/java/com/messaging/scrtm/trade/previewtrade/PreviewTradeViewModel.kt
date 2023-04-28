package com.messaging.scrtm.trade.previewtrade

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.auth.CreateOfferByMutation
import com.auth.GetNonceByUserIdQuery
import com.auth.type.CreateOfferPayload
import com.messaging.scrtm.R
import com.messaging.scrtm.core.utils.Resource
import com.messaging.scrtm.data.SessionPref
import com.messaging.scrtm.data.trade.repository.TradeRepository
import com.messaging.scrtm.features.onboarding.OnboardingViewModel
import com.messaging.scrtm.features.onboarding.usecase.Base58DecodeUseCase
import com.messaging.scrtm.features.onboarding.usecase.Base58EncodeUseCase
import com.messaging.scrtm.features.onboarding.usecase.MobileWalletAdapterUseCase
import com.messaging.scrtm.features.onboarding.usecase.OffChainMessageSigningUseCase
import com.solana.mobilewalletadapter.clientlib.protocol.MobileWalletAdapterClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewTradeViewModel @Inject constructor(
    val tradeRepository: TradeRepository,
    val sessionPref : SessionPref
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingViewModel.UiState())
    val uiState = _uiState.asStateFlow()

    private val _signature = MutableLiveData<String?>()
    val signature = _signature

    private val _message = MutableLiveData<Int>()
    val message = _message

    private val _createOffer = MutableLiveData<Resource<CreateOfferByMutation.Data?>>()
    val createOffer = _createOffer

    fun signNonce(
        intentLauncher: ActivityResultLauncher<MobileWalletAdapterUseCase.StartMobileWalletAdapterActivity.CreateParams>,
        nonce: Int
    ) = viewModelScope.launch {
        val message = nonce.toString().toByteArray(Charsets.UTF_8)
        val arrayMessage = arrayOf(message.copyOfRange(0, message.size))
        val signedMessages = try {
            doLocalAssociateAndExecute(intentLauncher, _uiState.value.walletUriBase) { client ->
                doReauthorize(client, OnboardingViewModel.IDENTITY, sessionPref.authToken)
                client.signMessagesDetached(arrayMessage, arrayOf(Base58DecodeUseCase(sessionPref.address)))
            }
        } catch (e: MobileWalletAdapterUseCase.LocalAssociationFailedException) {
            showMessage(R.string.msg_association_failed)
            return@launch
        } catch (e: MobileWalletAdapterUseCase.MobileWalletAdapterOperationFailedException) {
            Log.d("elloo", e.message.toString())
            showMessage(R.string.msg_request_failed)
            return@launch
        }

        try {
            OffChainMessageSigningUseCase.verify(
                signedMessages[0].message,
                signedMessages[0].signatures[0],
                _uiState.value.publicKey!!,
                nonce.toString().toByteArray(Charsets.UTF_8)
            )
            _signature.value = Base58EncodeUseCase.invoke(signedMessages[0].signatures[0])
            showMessage(R.string.msg_request_succeeded)
        } catch (e: IllegalArgumentException) {
            Log.d("elloo", e.message.toString())
            showMessage(R.string.msg_request_failed)
        }
    }

    private suspend fun doReauthorize(
        client: MobileWalletAdapterUseCase.Client,
        identity: MobileWalletAdapterUseCase.DappIdentity,
        currentAuthToken: String
    ): MobileWalletAdapterClient.AuthorizationResult {
        val result = try {
            client.reauthorize(identity, currentAuthToken)
        } catch (e: MobileWalletAdapterUseCase.MobileWalletAdapterOperationFailedException) {
            _uiState.update {
                it.copy(
                    authToken = null,
                    publicKey = null,
                    accountLabel = null,
                    walletUriBase = null
                )
            }
            throw e
        }

        _uiState.update {
            it.copy(
                authToken = result.authToken,
                publicKey = result.publicKey,
                accountLabel = result.accountLabel,
                walletUriBase = result.walletUriBase
            )

        }
        sessionPref.authToken = _uiState.value.authToken!!
        return result
    }

    private suspend fun <T> doLocalAssociateAndExecute(
        intentLauncher: ActivityResultLauncher<MobileWalletAdapterUseCase.StartMobileWalletAdapterActivity.CreateParams>,
        uriPrefix: Uri? = null,
        action: suspend (MobileWalletAdapterUseCase.Client) -> T
    ): T {
        return try {
            MobileWalletAdapterUseCase.localAssociateAndExecute(intentLauncher, uriPrefix, action)
        } catch (e: MobileWalletAdapterUseCase.NoWalletAvailableException) {
            showMessage(R.string.msg_no_wallet_found)
            throw e
        }
    }


    private fun showMessage(resId: Int) {
        _message.value = resId
    }

    fun getNonceByUserId(userId: Int) = liveData {
        emit(Resource.loading())
        try {
            val data = tradeRepository.getNonceByUserId(userId)
            emit(Resource.success(data))
        } catch (_: Throwable) {
        }
    }


    fun createOffer(payload: CreateOfferPayload) {
        viewModelScope.launch {
            _createOffer.value = Resource.loading()
            try {
                _createOffer.value = Resource.success(tradeRepository.createOffer(payload))
            } catch (_: Throwable) {
                _createOffer.value = Resource.error("Null")
            }
        }
    }

}