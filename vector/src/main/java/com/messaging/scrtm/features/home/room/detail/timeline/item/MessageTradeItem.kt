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
import android.util.Log
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
                EventBus.getDefault().post(
                    TradeEventBus(
                        offer = tradeByPkOutput,
                        tradeEventType = TradeEventType.CANCEL,
                        event!!
                    )
                )
            }
            tvAcceptTradeWaitApproval.setOnClickListener {
                EventBus.getDefault().post(
                    TradeEventBus(
                        offer = tradeByPkOutput,
                        tradeEventType = TradeEventType.ACCEPT,
                        event!!
                    )
                )
            }

            //ACCEPTED,Accepted
            cancelAccepted.setOnClickListener {
                EventBus.getDefault().post(
                    TradeEventBus(
                        offer = tradeByPkOutput,
                        tradeEventType = TradeEventType.CANCEL,
                        event!!
                    )
                )
            }
            initialTradeAccepted.setOnClickListener {
                EventBus.getDefault().post(
                    TradeEventBus(
                        offer = tradeByPkOutput,
                        tradeEventType = TradeEventType.INITIATE,
                        event!!
                    )
                )
            }

            //INITIALIZED
            tvCancelInitial.setOnClickListener {
                EventBus.getDefault().post(
                    TradeEventBus(
                        offer = tradeByPkOutput,
                        tradeEventType = TradeEventType.CANCEL,
                        event!!
                    )
                )
            }

            tvConfirmInitialized.setOnClickListener {
                EventBus.getDefault().post(
                    TradeEventBus(
                        offer = tradeByPkOutput,
                        tradeEventType = TradeEventType.CONFIRM,
                        event!!
                    )
                )
            }

            //Successful

            tvOkSuccessful.setOnClickListener {
               //new offer
            }

            //UI
            layoutWaitApproval.hide()
            layoutCanceled.hide()
            layoutAccepted.hide()
            layoutInitialized.hide()
            layoutSuccessful.hide()
            Log.d("Trade Status", TradeStatus.valueOf(tradeByPkOutput?.trades_by_pk?.status.toString()).name)
            when (TradeStatus.valueOf(tradeByPkOutput?.trades_by_pk?.status.toString())) {
                TradeStatus.WAIT_FOR_APPROVAL -> {
                    layoutWaitApproval.show()
                    if (isSender()) {
                        tvTradedSuccessfullyWaitApproval.text =
                            view.context.getString(R.string.you_made_an_offer)
                        tvAcceptTradeWaitApproval.invisible()
                    } else {
                        tvTradedSuccessfullyWaitApproval.text =
                            view.context.getString(R.string.you_have_an_offer)
                        tvAcceptTradeWaitApproval.show()
                    }

                }
                TradeStatus.CANCELLED -> {
                    layoutCanceled.show()
                    if (isSender()) {
                        tvTradedSuccessfullyCanceled.text =
                            view.context.getString(R.string.you_made_an_offer)
                    } else {
                        tvTradedSuccessfullyCanceled.text =
                            view.context.getString(R.string.you_have_an_offer)
                    }
                }

                TradeStatus.ACCEPTED -> {
                    layoutAccepted.show()
                    if (isSender()) {
                        initialTradeAccepted.text = view.context.getString(R.string.initial_trade)
                    } else {
                        initialTradeAccepted.isEnabled = false
                        initialTradeAccepted.text =
                            view.context.getString(R.string.wait_seller_start)
                        initialTradeAccepted.backgroundTintList =
                            ColorStateList.valueOf(getColor(view.context, R.color.navigation))
                        initialTradeAccepted.setTextColor(
                            getColor(
                                holder.view.context,
                                R.color.white_50
                            )
                        )
                    }
                }

                TradeStatus.INITIALIZED -> {
                    layoutInitialized.show()
                    if (isSender()) {
                        tvConfirmInitialized.isEnabled = false
                        tvConfirmInitialized.text =
                            view.context.getString(R.string.wait_buyer_exchange_token)
                        tvConfirmInitialized.backgroundTintList =
                            ColorStateList.valueOf(getColor(view.context, R.color.navigation))
                        tvConfirmInitialized.setTextColor(
                            getColor(
                                holder.view.context,
                                R.color.white_50
                            )
                        )
                    } else {
                        tvConfirmInitialized.text = view.context.getString(R.string.confirm_escrow)
                    }
                }

                TradeStatus.SUCCESSFUL -> {
                    layoutSuccessful.show()

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
            tvFromAddress.text = attributes.tradeInfo?.sending_address


            //cancel
            tvSendingTokenCanceled.text = String.format(
                "${attributes.tradeInfo?.sending_token_amount} %s",
                attributes.tradeInfo?.sending_token_address
            )
            tvRecipientTokenCanceled.text = String.format(
                "${attributes.tradeInfo?.recipient_token_amount} %s",
                attributes.tradeInfo?.recipient_token_address
            )
            tvFromAddressCanceled.text = attributes.tradeInfo?.sending_address

            nameOfferTradeCanceled.text = attributes.tradeInfo?.sending_address
            tvAcceptbyCanceled.text = attributes.tradeInfo?.recipient_address

            //Accepted
            tvSendingTokenAccepted.text = String.format(
                "${attributes.tradeInfo?.sending_token_amount} %s",
                attributes.tradeInfo?.sending_token_address
            )
            tvRecipientTokenAccepted.text = String.format(
                "${attributes.tradeInfo?.recipient_token_amount} %s",
                attributes.tradeInfo?.recipient_token_address
            )
            tvFromAddressAccepted.text = attributes.tradeInfo?.sending_address

            nameOfferTradeAccepted.text = attributes.tradeInfo?.sending_address
            tvAcceptbyAccepted.text = attributes.tradeInfo?.recipient_address

            //Initialized
            tvSendingTokenInitialized.text = String.format(
                "${attributes.tradeInfo?.sending_token_amount} %s",
                attributes.tradeInfo?.sending_token_address
            )
            tvRecipientTokenInitialized.text = String.format(
                "${attributes.tradeInfo?.recipient_token_amount} %s",
                attributes.tradeInfo?.recipient_token_address
            )
            tvFromAddressInitialized.text = attributes.tradeInfo?.sending_address

            nameOfferTradeInitialized.text = attributes.tradeInfo?.sending_address
            tvAcceptbyInitialized.text = attributes.tradeInfo?.recipient_address

            //Successful
            tvSendingTokenSuccessful.text = String.format(
                "${attributes.tradeInfo?.sending_token_amount} %s",
                attributes.tradeInfo?.sending_token_address
            )
            tvRecipientTokenSuccessful.text = String.format(
                "${attributes.tradeInfo?.recipient_token_amount} %s",
                attributes.tradeInfo?.recipient_token_address
            )
            tvFromAddressSuccessful.text = attributes.tradeInfo?.sending_address


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

        //common
        val tvRecipientToken by bind<AppCompatTextView>(R.id.tvRecipientToken)
        val tvSendingToken by bind<AppCompatTextView>(R.id.tvSendingToken)
        val tvFromAddress by bind<AppCompatTextView>(R.id.tvFromAddress)

        //CANCELLED,Canceled
        val layoutCanceled by bind<FrameLayout>(R.id.layoutCanceled)
        val tvTradedSuccessfullyCanceled by bind<AppCompatTextView>(R.id.tvTradedSuccessfullyCanceled)

        val tvRecipientTokenCanceled by bind<AppCompatTextView>(R.id.tvRecipientTokenCanceled)
        val tvSendingTokenCanceled by bind<AppCompatTextView>(R.id.tvSendingTokenCanceled)
        val tvFromAddressCanceled by bind<AppCompatTextView>(R.id.tvFromAddressCanceled)

        val nameOfferTradeCanceled by bind<AppCompatTextView>(R.id.nameOfferTradeCanceled)
        val tvAcceptbyCanceled by bind<AppCompatTextView>(R.id.tvAcceptbyCanceled)


        //ACCEPTED,Accepted
        val layoutAccepted by bind<FrameLayout>(R.id.layoutAccepted)
        val cancelAccepted by bind<AppCompatTextView>(R.id.cancelAccepted)
        val initialTradeAccepted by bind<AppCompatTextView>(R.id.initialTradeAccepted)

        val tvRecipientTokenAccepted by bind<AppCompatTextView>(R.id.tvRecipientTokenAccepted)
        val tvSendingTokenAccepted by bind<AppCompatTextView>(R.id.tvSendingTokenAccepted)
        val tvFromAddressAccepted by bind<AppCompatTextView>(R.id.tvFromAddressAccepted)

        val nameOfferTradeAccepted by bind<AppCompatTextView>(R.id.nameOfferTradeAccepted)
        val tvAcceptbyAccepted by bind<AppCompatTextView>(R.id.tvAcceptbyAccepted)

        //INITIALIZED,Initialized
        val layoutInitialized by bind<FrameLayout>(R.id.layoutInitialized)
        val tvCancelInitial by bind<AppCompatTextView>(R.id.tvCancelInitial)
        val tvConfirmInitialized by bind<AppCompatTextView>(R.id.tvConfirmInitialized)

        val tvSendingTokenInitialized by bind<AppCompatTextView>(R.id.tvSendingTokenInitialized)
        val tvRecipientTokenInitialized by bind<AppCompatTextView>(R.id.tvRecipientTokenInitialized)
        val tvFromAddressInitialized by bind<AppCompatTextView>(R.id.tvFromAddressInitialized)

        val nameOfferTradeInitialized by bind<AppCompatTextView>(R.id.nameOfferTradeInitialized)
        val tvAcceptbyInitialized by bind<AppCompatTextView>(R.id.tvAcceptbyInitialized)


        //SUCCESSFUL,Successful
        val layoutSuccessful by bind<FrameLayout>(R.id.layoutSuccessful)

        val tvCancelSuccessful by bind<AppCompatTextView>(R.id.tvCancelSuccessful)
        val tvOkSuccessful by bind<AppCompatTextView>(R.id.tvOkSuccessful)

        val tvSendingTokenSuccessful by bind<AppCompatTextView>(R.id.tvSendingTokenSuccessful)
        val tvRecipientTokenSuccessful by bind<AppCompatTextView>(R.id.tvRecipientTokenSuccessful)
        val tvFromAddressSuccessful by bind<AppCompatTextView>(R.id.tvFromAddressSuccessful)

//        val tvDate2 by bind<AppCompatTextView>(R.id.tvDate2)
//        val formAddress2 by bind<AppCompatTextView>(R.id.formAddress2)
//        val nameOfferTrade2 by bind<AppCompatTextView>(R.id.nameOfferTrade2)
//        val tvAcceptby2 by bind<AppCompatTextView>(R.id.tvAcceptby2)


    }

    companion object {
        private val STUB_ID = R.id.messageContentTradeStub
    }

}
