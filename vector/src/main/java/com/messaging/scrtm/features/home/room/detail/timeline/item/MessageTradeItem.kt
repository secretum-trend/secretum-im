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

import android.content.res.ColorStateList
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.auth.GetTradeByPkQuery
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.messaging.lib.core.utils.view.hide
import com.messaging.lib.core.utils.view.invisible
import com.messaging.lib.core.utils.view.show
import com.messaging.scrtm.R
import com.messaging.scrtm.data.SessionPref
import com.messaging.scrtm.data.trade.entity.TradeStatus
import com.messaging.scrtm.features.home.room.detail.RoomDetailAction
import com.messaging.scrtm.trade.eventBus.TradeEventBus
import com.messaging.scrtm.trade.eventBus.TradeEventType
import org.greenrobot.eventbus.EventBus
import org.matrix.android.sdk.api.MatrixPatterns.toFormattedDate

@EpoxyModelClass
abstract class MessageTradeItem : AbsMessageLocationItem<MessageTradeItem.Holder>() {

    @EpoxyAttribute
    var currentUserId: String? = null

    @EpoxyAttribute
    var sessionPref: SessionPref? = null


    @EpoxyAttribute
    var tradeByPkOutput: GetTradeByPkQuery.Data? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        bindLiveLocationBanner(holder)
    }

    private fun bindLiveLocationBanner(holder: Holder) {
        // TODO in a future PR add check on device id to confirm that is the one that sent the beacon
//        val isEmitter = currentUserId != null && currentUserId == locationUserId
//        val messageLayout = attributes.informationData.messageLayout
//        val viewState = buildViewState(holder, messageLayout, isEmitter)

        with(holder) {
            tvDismiss.setOnClickListener {
                EventBus.getDefault().post(TradeEventBus(offer = tradeByPkOutput, tradeEventType = TradeEventType.CANCEL))
            }
            tvDismiss2.setOnClickListener {
                EventBus.getDefault().post(TradeEventBus(offer = tradeByPkOutput, tradeEventType = TradeEventType.CANCEL))
            }

            tvAcceptTrade.setOnClickListener {
                EventBus.getDefault().post(TradeEventBus(offer = tradeByPkOutput, tradeEventType = TradeEventType.ACCEPT))
            }

            layoutTradeQuestion.hide()
            layoutTradeAccepted.hide()
            when (TradeStatus.valueOf(tradeByPkOutput?.trades_by_pk?.status.toString())) {
                TradeStatus.WAIT_FOR_APPROVAL -> {
                    layoutTradeQuestion.show()
                    if (isSender()) {
                        tvAcceptTrade.invisible()
                    } else {
                        tvAcceptTrade.show()
                    }

//                    layoutTradeAccepted.show()
//                    view2.setBackgroundColor(getColor(holder.view.context, R.color.gray))
//                    view3.setBackgroundColor(getColor(holder.view.context, R.color.gray))
//                    dot3.setCardBackgroundColor(getColor(holder.view.context, R.color.gray))
//                    dot4.setCardBackgroundColor(getColor(holder.view.context, R.color.gray))
                    //done
                }
                TradeStatus.CANCELLED -> {
                    layoutTradeAccepted.show()
                    tvAcceptTrade2.invisible()

                    tvDismiss2.setTextColor(getColor(holder.view.context, R.color.gray))
                    tvDismiss2.isEnabled = false
                }
                TradeStatus.ACCEPTED -> {
                    layoutTradeAccepted.show()
                    view2.setBackgroundColor(getColor(holder.view.context, R.color.gray))
                    view3.setBackgroundColor(getColor(holder.view.context, R.color.gray))
                    dot3.setCardBackgroundColor(getColor(holder.view.context, R.color.gray))
                    dot4.setCardBackgroundColor(getColor(holder.view.context, R.color.gray))
                    tvAcceptTrade2.text = view.context.getString(R.string.wait_seller_start)
                    tvAcceptTrade2.backgroundTintList =
                        ColorStateList.valueOf(getColor(view.context, R.color.navigation))
                    tvAcceptTrade2.setTextColor(getColor(holder.view.context, R.color.white_50))
                }
                TradeStatus.INITIALIZED -> {
                    layoutTradeQuestion.show()
                    if (isSender()) {
                        tvAcceptTrade.invisible()
                    } else {
                        tvAcceptTrade.show()
                    }
                }
                TradeStatus.SUCCESSFUL -> {

                }
            }

            //common UI
            tvSendingToken.text = String.format(
                "${attributes.tradeInfo?.sending_token_amount} %s",
                attributes.tradeInfo?.sending_token_address
            )
            tvRecipientToken.text = String.format(
                "${attributes.tradeInfo?.recipient_token_amount} %s",
                attributes.tradeInfo?.recipient_token_address
            )

            tvSendingToken2.text = String.format(
                "${attributes.tradeInfo?.sending_token_amount} %s",
                attributes.tradeInfo?.sending_token_address
            )
            tvRecipientToken2.text = String.format(
                "${attributes.tradeInfo?.recipient_token_amount} %s",
                attributes.tradeInfo?.recipient_token_address
            )

            tvFromAddress.text = attributes.tradeInfo?.sending_address
            formAddress2.text = attributes.tradeInfo?.sending_address

//            nameOfferTrade.text = tradeByPkOutput?.trades_by_pk?.sending_address
//            tvAcceptBy.text = tradeByPkOutput?.trades_by_pk?.recipient_address
            //
            tvDate2.text = tradeByPkOutput?.trades_by_pk?.created_at?.toFormattedDate()
            nameOfferTrade2.text = tradeByPkOutput?.trades_by_pk?.sending_address
            tvAcceptby2.text = tradeByPkOutput?.trades_by_pk?.recipient_address
        }

    }

    private fun isSender() = sessionPref?.address == tradeByPkOutput?.trades_by_pk?.sending_address

    override fun getViewStubId() = STUB_ID

    class Holder : AbsMessageLocationItem.Holder(STUB_ID) {
        //layout trade question
        val layoutTradeQuestion by bind<FrameLayout>(R.id.layoutTradeQuestion)
        val tvAcceptTrade by bind<AppCompatTextView>(R.id.tvAcceptTrade)
        val tvDismiss by bind<AppCompatTextView>(R.id.tvDismiss)
//        val tvDate by bind<AppCompatTextView>(R.id.tvDate)

        val tvSendingToken by bind<AppCompatTextView>(R.id.tvSendingToken)
        val tvRecipientToken by bind<AppCompatTextView>(R.id.tvRecipientToken)
        val tvFromAddress by bind<AppCompatTextView>(R.id.tvFromAddress)

        //layout trade accepted
        val layoutTradeAccepted by bind<FrameLayout>(R.id.layoutTradeAccepted)

        //        val nameOfferTrade by bind<AppCompatTextView>(R.id.nameOfferTrade)
//        val tvAcceptBy by bind<AppCompatTextView>(R.id.tvAcceptby)
        val tvDismiss2 by bind<AppCompatTextView>(R.id.tvDismiss2)

        val tvDate2 by bind<AppCompatTextView>(R.id.tvDate)
        val nameOfferTrade2 by bind<AppCompatTextView>(R.id.nameOfferTrade)
        val tvAcceptby2 by bind<AppCompatTextView>(R.id.tvAcceptby)


        val tvSendingToken2 by bind<AppCompatTextView>(R.id.tvSendingToken2)
        val tvRecipientToken2 by bind<AppCompatTextView>(R.id.tvRecipientToken2)
        val formAddress2 by bind<AppCompatTextView>(R.id.tvFromAddress2)
        val tvAcceptTrade2 by bind<AppCompatTextView>(R.id.tvAcceptTrade2)


        val view2 by bind<View>(R.id.view2)
        val view3 by bind<View>(R.id.view3)
        val dot3 by bind<CardView>(R.id.dot3)
        val dot4 by bind<CardView>(R.id.dot4)

    }

    companion object {
        private val STUB_ID = R.id.messageContentTradeStub
    }

}
