/*
 * Copyright (c) 2022 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.messaging.scrtm.features.home.room.detail.timeline.item

import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.messaging.scrtm.R
import com.messaging.scrtm.data.trade.repository.TradeRepository
import com.messaging.scrtm.features.home.room.detail.RoomDetailAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@EpoxyModelClass
abstract class MessageTradeItem : AbsMessageLocationItem<MessageTradeItem.Holder>() {

    @EpoxyAttribute
    var currentUserId: String? = null

//    @EpoxyAttribute
//    var endOfLiveDateTime: LocalDateTime? = null

//    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
//    lateinit var vectorDateFormatter: VectorDateFormatter

    override fun bind(holder: Holder) {
        super.bind(holder)
        bindLiveLocationBanner(holder)
    }

    private fun bindLiveLocationBanner(holder: Holder) {
        // TODO in a future PR add check on device id to confirm that is the one that sent the beacon
//        val isEmitter = currentUserId != null && currentUserId == locationUserId
//        val messageLayout = attributes.informationData.messageLayout
//        val viewState = buildViewState(holder, messageLayout, isEmitter)
        holder.tvDismiss.setOnClickListener {
            attributes.callback?.onTimelineItemAction(RoomDetailAction.StopLiveLocationSharing)
        }

        holder.tvAcceptTrade.setOnClickListener {
            attributes.callback?.onTimelineItemAction(RoomDetailAction.StopLiveLocationSharing)
        }

        holder.tvSendingToken.text = String.format(
            "${attributes.tradeInfo?.sending_token_amount} %s",
            attributes.tradeInfo?.sending_token_address
        )
        holder.tvRecipientToken.text = String.format(
            "${attributes.tradeInfo?.recipient_token_amount} %s",
            attributes.tradeInfo?.recipient_token_address
        )

        holder.tvFromAddress.text = attributes.tradeInfo?.sending_address
    }

    override fun getViewStubId() = STUB_ID

    class Holder : AbsMessageLocationItem.Holder(STUB_ID) {
        val tvAcceptTrade by bind<AppCompatTextView>(R.id.tvAcceptTrade)
        val tvDismiss by bind<AppCompatTextView>(R.id.tvDismiss)

        val tvSendingToken by bind<AppCompatTextView>(R.id.tvSendingToken)
        val tvRecipientToken by bind<AppCompatTextView>(R.id.tvRecipientToken)

        val tvFromAddress by bind<AppCompatTextView>(R.id.tvFromAddress)

    }

    companion object {
        private val STUB_ID = R.id.messageContentTradeStub
    }
}
