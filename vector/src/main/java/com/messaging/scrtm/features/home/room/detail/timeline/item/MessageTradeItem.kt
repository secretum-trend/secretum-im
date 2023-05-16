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
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat.getColor
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.auth.GetTradeByPkQuery
import com.messaging.lib.core.utils.view.hide
import com.messaging.lib.core.utils.view.invisible
import com.messaging.lib.core.utils.view.show
import com.messaging.scrtm.R
import com.messaging.scrtm.data.SessionPref
import com.messaging.scrtm.data.trade.entity.TradeStatus
import com.messaging.scrtm.trade.eventBus.TradeEventBus
import com.messaging.scrtm.trade.eventBus.TradeEventType
import org.greenrobot.eventbus.EventBus
import org.matrix.android.sdk.api.session.events.model.Event

@EpoxyModelClass
abstract class MessageTradeItem : AbsMessageLocationItem<MessageTradeItem.Holder>() {

    @EpoxyAttribute
    var currentUserId: String? = null

    @EpoxyAttribute
    var sessionPref: SessionPref? = null


    @EpoxyAttribute
    var tradeByPkOutput: GetTradeByPkQuery.Data? = null

    @EpoxyAttribute
    var event: Event? = null

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
            //WaitApproval
            tvDismissWaitApproval.setOnClickListener {
                EventBus.getDefault().post(TradeEventBus(offer = tradeByPkOutput, tradeEventType = TradeEventType.CANCEL, event!!))
            }
            tvAcceptTradeWaitApproval.setOnClickListener {
                EventBus.getDefault().post(TradeEventBus(offer = tradeByPkOutput, tradeEventType = TradeEventType.ACCEPT,event!!))
            }

            //ACCEPTED,Accepted
            cancelAccepted.setOnClickListener {
                EventBus.getDefault().post(TradeEventBus(offer = tradeByPkOutput, tradeEventType = TradeEventType.CANCEL,event!!))
            }
            initialTradeAccepted.setOnClickListener {
                EventBus.getDefault().post(TradeEventBus(offer = tradeByPkOutput, tradeEventType = TradeEventType.INITIATE,event!!))
            }

            //INITIALIZED
            tvConfirmInitialized.setOnClickListener {
                EventBus.getDefault().post(TradeEventBus(offer = tradeByPkOutput, tradeEventType = TradeEventType.CONFIRM,event!!))
            }

            //UI
            layoutWaitApproval.hide()
            layoutCanceled.hide()
            layoutAccepted.hide()
            layoutInitialized.hide()
            layoutSuccessful.hide()
            when (TradeStatus.valueOf(tradeByPkOutput?.trades_by_pk?.status.toString())) {
                TradeStatus.WAIT_FOR_APPROVAL -> {
                    layoutWaitApproval.show()
                    if (isSender()) {
                        tvTradedSuccessfullyWaitApproval.text = view.context.getString(R.string.you_made_an_offer)
                        tvAcceptTradeWaitApproval.invisible()
                    } else {
                        tvTradedSuccessfullyWaitApproval.text = view.context.getString(R.string.you_have_an_offer)
                        tvAcceptTradeWaitApproval.show()
                    }

                }
                TradeStatus.CANCELLED -> {
                    layoutCanceled.show()
                    if (isSender()) {
                        tvTradedSuccessfullyCanceled.text = view.context.getString(R.string.you_made_an_offer)
                    } else {
                        tvTradedSuccessfullyCanceled.text = view.context.getString(R.string.you_have_an_offer)
                    }
                }

                TradeStatus.ACCEPTED -> {
                    layoutAccepted.show()

                    if (isSender()) {
                        initialTradeAccepted.text = view.context.getString(R.string.initial_trade)
                    } else {
                        initialTradeAccepted.isEnabled = false
                        initialTradeAccepted.text = view.context.getString(R.string.wait_seller_start)
                        initialTradeAccepted.backgroundTintList =
                            ColorStateList.valueOf(getColor(view.context, R.color.navigation))
                        initialTradeAccepted.setTextColor(getColor(holder.view.context, R.color.white_50))
                    }

                }
                TradeStatus.INITIALIZED -> {
                    layoutInitialized.show()
                    if (isSender()) {
                        tvConfirmInitialized.isEnabled = false
                        tvConfirmInitialized.text = view.context.getString(R.string.wait_buyer_exchange_token)
                        tvConfirmInitialized.backgroundTintList =
                            ColorStateList.valueOf(getColor(view.context, R.color.navigation))
                        tvConfirmInitialized.setTextColor(getColor(holder.view.context, R.color.white_50))
                    } else {
                        tvConfirmInitialized.text = view.context.getString(R.string.confirm_escrow)
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
//            formAddress2.text = attributes.tradeInfo?.sending_address
//            tvDate2.text = tradeByPkOutput?.trades_by_pk?.created_at?.toFormattedDate()
//            nameOfferTrade2.text = tradeByPkOutput?.trades_by_pk?.sending_address
//            tvAcceptby2.text = tradeByPkOutput?.trades_by_pk?.recipient_address
        }

    }

    private fun isSender() = sessionPref?.address == tradeByPkOutput?.trades_by_pk?.sending_address

    override fun getViewStubId() = STUB_ID

    class Holder : AbsMessageLocationItem.Holder(STUB_ID) {
        //WAIT_FOR_APPROVAL,WaitApproval
        val layoutWaitApproval by bind<FrameLayout>(R.id.layoutWaitForApproval)
        val tvTradedSuccessfullyWaitApproval by bind<AppCompatTextView>(R.id.tvTradedSuccessfullyWaitApproval)
        val tvDismissWaitApproval by bind<AppCompatTextView>(R.id.tvDismissWaitApproval)
        val tvAcceptTradeWaitApproval by bind<AppCompatTextView>(R.id.tvAcceptTradeWaitApproval)

        //CANCELLED,Canceled
        val layoutCanceled by bind<FrameLayout>(R.id.layoutCanceled)
        val tvTradedSuccessfullyCanceled by bind<AppCompatTextView>(R.id.tvTradedSuccessfullyCanceled)



        //ACCEPTED,Accepted
        val layoutAccepted by bind<FrameLayout>(R.id.layoutAccepted)
        val cancelAccepted by bind<AppCompatTextView>(R.id.cancelAccepted)
        val initialTradeAccepted by bind<AppCompatTextView>(R.id.initialTradeAccepted)

        //INITIALIZED,
        val layoutInitialized by bind<FrameLayout>(R.id.layoutInitialized)
        val tvConfirmInitialized by bind<AppCompatTextView>(R.id.tvConfirmInitialized)


        //SUCCESSFUL,
        val layoutSuccessful by bind<FrameLayout>(R.id.layoutSuccessful)


        //common
        val tvSendingToken2 by bind<AppCompatTextView>(R.id.tvSendingToken2)
        val tvRecipientToken2 by bind<AppCompatTextView>(R.id.tvSendingToken)
        //

        val tvRecipientToken by bind<AppCompatTextView>(R.id.tvRecipientToken)
        val tvSendingToken by bind<AppCompatTextView>(R.id.tvSendingToken)
        val tvFromAddress by bind<AppCompatTextView>(R.id.tvFromAddress)
//        val tvDate2 by bind<AppCompatTextView>(R.id.tvDate2)
//        val formAddress2 by bind<AppCompatTextView>(R.id.formAddress2)
//        val nameOfferTrade2 by bind<AppCompatTextView>(R.id.nameOfferTrade2)
//        val tvAcceptby2 by bind<AppCompatTextView>(R.id.tvAcceptby2)


    }

    companion object {
        private val STUB_ID = R.id.messageContentTradeStub
    }

}
