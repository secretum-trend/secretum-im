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
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.messaging.scrtm.R
import com.messaging.scrtm.core.date.DateFormatKind
import com.messaging.scrtm.core.date.VectorDateFormatter
import com.messaging.scrtm.core.resources.toTimestamp
import com.messaging.scrtm.core.utils.DimensionConverter
import com.messaging.scrtm.features.home.room.detail.RoomDetailAction
import com.messaging.scrtm.features.home.room.detail.timeline.style.TimelineMessageLayout
import com.messaging.scrtm.features.location.live.LiveLocationMessageBannerViewState
import com.messaging.scrtm.features.location.live.LiveLocationRunningBannerView
import org.threeten.bp.LocalDateTime

@EpoxyModelClass
abstract class MessageTradeItem : AbsMessageLocationItem<MessageTradeItem.Holder>() {

    @EpoxyAttribute
    var currentUserId: String? = null

//    @EpoxyAttribute
//    var endOfLiveDateTime: LocalDateTime? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var vectorDateFormatter: VectorDateFormatter

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
    }
//
//    private fun buildViewState(
//            holder: Holder,
//            messageLayout: TimelineMessageLayout,
//            isEmitter: Boolean
//    ): LiveLocationMessageBannerViewState {
//        return when {
//            messageLayout is TimelineMessageLayout.Bubble && isEmitter ->
//                LiveLocationMessageBannerViewState.Emitter(
//                        remainingTimeInMillis = getRemainingTimeOfLiveInMillis(),
//                        bottomStartCornerRadiusInDp = messageLayout.cornersRadius.bottomStartRadius,
//                        bottomEndCornerRadiusInDp = messageLayout.cornersRadius.bottomEndRadius,
//                        isStopButtonCenteredVertically = false
//                )
//            messageLayout is TimelineMessageLayout.Bubble ->
//                LiveLocationMessageBannerViewState.Watcher(
//                        bottomStartCornerRadiusInDp = messageLayout.cornersRadius.bottomStartRadius,
//                        bottomEndCornerRadiusInDp = messageLayout.cornersRadius.bottomEndRadius,
//                        formattedLocalTimeOfEndOfLive = getFormattedLocalTimeEndOfLive(),
//                )
//            isEmitter -> {
//                val cornerRadius = getBannerCornerRadiusForDefaultLayout(holder)
//                LiveLocationMessageBannerViewState.Emitter(
//                        remainingTimeInMillis = getRemainingTimeOfLiveInMillis(),
//                        bottomStartCornerRadiusInDp = cornerRadius,
//                        bottomEndCornerRadiusInDp = cornerRadius,
//                        isStopButtonCenteredVertically = true
//                )
//            }
//            else -> {
//                val cornerRadius = getBannerCornerRadiusForDefaultLayout(holder)
//                LiveLocationMessageBannerViewState.Watcher(
//                        bottomStartCornerRadiusInDp = cornerRadius,
//                        bottomEndCornerRadiusInDp = cornerRadius,
//                        formattedLocalTimeOfEndOfLive = getFormattedLocalTimeEndOfLive(),
//                )
//            }
//        }
//    }

//    private fun getBannerCornerRadiusForDefaultLayout(holder: Holder): Float {
//        val dimensionConverter = DimensionConverter(holder.view.resources)
//        return dimensionConverter.dpToPx(8).toFloat()
//    }
//
//    private fun getFormattedLocalTimeEndOfLive() =
//            endOfLiveDateTime?.toTimestamp()?.let { vectorDateFormatter.format(it, DateFormatKind.MESSAGE_SIMPLE) }.orEmpty()
//
//    private fun getRemainingTimeOfLiveInMillis() =
//            (endOfLiveDateTime?.toTimestamp() ?: 0) - LocalDateTime.now().toTimestamp()

    override fun getViewStubId() = STUB_ID

    class Holder : AbsMessageLocationItem.Holder(STUB_ID) {
        val tvAcceptTrade by bind<AppCompatTextView>(R.id.tvAcceptTrade)
        val tvDismiss by bind<AppCompatTextView>(R.id.tvDismiss)
    }

    companion object {
        private val STUB_ID = R.id.messageContentTradeStub
    }
}
