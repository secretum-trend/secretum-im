package com.messaging.scrtm.trade.creatingoffer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.auth.GetPartnerAddressByQuery
import com.auth.GetTradeByPkQuery
import com.messaging.scrtm.core.utils.Resource
import com.messaging.scrtm.data.solana.entity.ResponseSolana
import com.messaging.scrtm.data.solana.entity.Value
import com.messaging.scrtm.data.solana.repository.SolanaRepository
import com.messaging.scrtm.data.trade.repository.TradeRepository
import com.messaging.scrtm.trade.custom.ViewSelectSending
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateOfferViewModel @Inject constructor(
    private val tradeRepository: TradeRepository,
    private val solanaRepository: SolanaRepository

) : ViewModel() {
    val attentionExpands = MutableLiveData(false)
    val sendingType = MutableLiveData(ViewSelectSending.TypeSending.Token)
    val receiveType = MutableLiveData(ViewSelectSending.TypeSending.Token)

    var tokenSending  : Value? = null
    var tokenRecipient  : Value? = null

    var nftSending  : Value? = null
    var nftRecipient  : Value? = null

    private val _partner = MutableLiveData<Resource<GetPartnerAddressByQuery.Data?>>()
    val partner: MutableLiveData<Resource<GetPartnerAddressByQuery.Data?>> = _partner

    private val _yourToken = MutableLiveData<Resource<ResponseSolana>>()
    val yourToken: MutableLiveData<Resource<ResponseSolana>> = _yourToken

    private val _recipientToken = MutableLiveData<Resource<ResponseSolana>>()
    val recipientToken: MutableLiveData<Resource<ResponseSolana>> = _recipientToken

    fun getPartnerAddress(userId: Int) {
        viewModelScope.launch {
            _partner.value = Resource.loading()
            try {
                _partner.value =
                    Resource.success(tradeRepository.getPartnerAddress(userId))
            } catch (t: Throwable) {
                _partner.value = Resource.error(t.message.toString())
            }
        }
    }

    fun getTokensList(address: String) {
        viewModelScope.launch {
            _yourToken.value = Resource.loading()
            try {
                _yourToken.value =
                    Resource.success(solanaRepository.getTokenAccountsByOwner(address))
            } catch (t: Throwable) {
                _yourToken.value = Resource.error(t.message.toString())
            }
        }
    }

    fun getRecipientTokensList(address: String) {
        viewModelScope.launch {
            _recipientToken.value = Resource.loading()
            try {
                _recipientToken.value =
                    Resource.success(solanaRepository.getTokenAccountsByOwner(address))
            } catch (t: Throwable) {
                _recipientToken.value = Resource.error(t.message.toString())
            }
        }
    }

    fun tradeByPk(id: Int) = liveData {
        emit(Resource.loading())
        try {
            emit(Resource.success(tradeRepository.tradeByPK(id)))

        } catch (t: Throwable) {
           emit(Resource.error(t.message.toString()))
        }
    }

}