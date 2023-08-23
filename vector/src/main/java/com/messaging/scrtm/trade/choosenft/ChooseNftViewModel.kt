package com.messaging.scrtm.trade.choosenft

import androidx.lifecycle.ViewModel
import com.messaging.scrtm.data.solana.repository.SolanaRepository
import com.messaging.scrtm.data.trade.repository.TradeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseNftViewModel @Inject constructor(
    private val tradeRepository: TradeRepository,
    val solanaRepository: SolanaRepository
) : ViewModel() {


}