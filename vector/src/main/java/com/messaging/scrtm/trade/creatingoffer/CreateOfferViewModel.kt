package com.messaging.scrtm.trade.creatingoffer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.messaging.scrtm.trade.custom.ViewSelectSending
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateOfferViewModel @Inject constructor() : ViewModel() {
    val attentionExpands = MutableLiveData(false)

    val sendingType = MutableLiveData(ViewSelectSending.TypeSending.Token)
    val receiveType = MutableLiveData(ViewSelectSending.TypeSending.Token)



}