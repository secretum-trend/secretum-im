package com.messaging.scrtm.trade.creatingoffer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth.GetPartnerAddressByQuery
import com.messaging.scrtm.data.solana.entity.ApiResponse
import com.messaging.scrtm.data.solana.entity.TokenAccount
import com.messaging.scrtm.data.solana.repository.SolanaRepository
import com.messaging.scrtm.data.trade.repository.TradeRepository
import com.messaging.scrtm.trade.custom.ViewSelectSending
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CreateOfferViewModel @Inject constructor(
    private val tradeRepository: TradeRepository,
    private val solanaRepository: SolanaRepository

) : ViewModel() {
    val attentionExpands = MutableLiveData(false)
    val sendingType = MutableLiveData(ViewSelectSending.TypeSending.Token)
    val receiveType = MutableLiveData(ViewSelectSending.TypeSending.Token)

    private val _partner = MutableLiveData<GetPartnerAddressByQuery.Data?>()
    val partner : MutableLiveData<GetPartnerAddressByQuery.Data?> = _partner

    private val _tokenAccount = MutableLiveData<Response<ApiResponse<List<TokenAccount>>>>()
    val tokenAccount : MutableLiveData<Response<ApiResponse<List<TokenAccount>>>> = _tokenAccount

    fun getPartnerAddress(userId : Int){
        viewModelScope.launch {
            _partner.value = tradeRepository.getPartnerAddress(userId)
        }
    }

    fun getTokensList(address : String){
        viewModelScope.launch {
            _tokenAccount.value =  solanaRepository.getTokenAccountsByOwner(address)
        }
    }


}