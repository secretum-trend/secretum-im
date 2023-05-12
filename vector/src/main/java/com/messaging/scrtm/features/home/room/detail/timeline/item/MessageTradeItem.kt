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

import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.auth.GetTradeByPkQuery
import com.messaging.lib.core.utils.view.hide
import com.messaging.lib.core.utils.view.invisible
import com.messaging.lib.core.utils.view.show
import com.messaging.scrtm.R
import com.messaging.scrtm.data.SessionPref
import com.messaging.scrtm.data.trade.entity.TradeStatus
import com.messaging.scrtm.features.home.room.detail.RoomDetailAction

@EpoxyModelClass
abstract class MessageTradeItem : AbsMessageLocationItem<MessageTradeItem.Holder>() {

    @EpoxyAttribute
    var currentUserId: String? = null

    @EpoxyAttribute
    var sessionPref: SessionPref? = null


    @EpoxyAttribute
    var tradeByPkOutput: GetTradeByPkQuery.Data? = null

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

        holder.layoutTradeQuestion.hide()
        holder.layoutTradeAccepted.hide()
        when (TradeStatus.valueOf(tradeByPkOutput?.trades_by_pk?.status.toString())) {
            TradeStatus.WAIT_FOR_APPROVAL -> {
                holder.layoutTradeQuestion.show()
                if (sessionPref?.address == tradeByPkOutput?.trades_by_pk?.sending_address) {
                    holder.tvAcceptTrade.invisible()
                    holder.tvDismiss.text = holder.view.context.getString(R.string.cancel_offer)
                } else {
                    holder.tvAcceptTrade.show()
                    holder.tvDismiss.text = holder.view.context.getString(R.string.action_dismiss)
                }
            }
            TradeStatus.CANCELLED -> {
                holder.layoutTradeAccepted.show()
            }
            TradeStatus.ACCEPTED -> {
                holder.layoutTradeAccepted.show()
            }
            TradeStatus.INITIALIZED -> {
                holder.layoutTradeQuestion
            }
            TradeStatus.SUCCESSFUL -> {

            }
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
        //layout trade question
        val layoutTradeQuestion by bind<FrameLayout>(R.id.layoutTradeQuestion)
        val tvAcceptTrade by bind<AppCompatTextView>(R.id.tvAcceptTrade)
        val tvDismiss by bind<AppCompatTextView>(R.id.tvDismiss)

        val tvSendingToken by bind<AppCompatTextView>(R.id.tvSendingToken)
        val tvRecipientToken by bind<AppCompatTextView>(R.id.tvRecipientToken)
        val tvFromAddress by bind<AppCompatTextView>(R.id.tvFromAddress)

        //layout trade accepted
        val layoutTradeAccepted by bind<FrameLayout>(R.id.layoutTradeAccepted)

    }

    companion object {
        private val STUB_ID = R.id.messageContentTradeStub
    }
}
