/*
 * Copyright (c) 2023 New Vector Ltd
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

package com.messaging.scrtm.features.roomprofile.polls.list.ui

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.messaging.scrtm.R
import com.messaging.scrtm.core.epoxy.ClickListener
import com.messaging.scrtm.core.epoxy.VectorEpoxyHolder
import com.messaging.scrtm.core.epoxy.VectorEpoxyModel
import com.messaging.scrtm.core.epoxy.onClick
import com.messaging.scrtm.core.extensions.setTextOrHide
import com.messaging.scrtm.features.home.room.detail.timeline.item.PollOptionView
import com.messaging.scrtm.features.home.room.detail.timeline.item.PollOptionViewState

@EpoxyModelClass
abstract class RoomPollItem : VectorEpoxyModel<RoomPollItem.Holder>(R.layout.item_poll) {

    @EpoxyAttribute
    lateinit var formattedDate: String

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    var winnerOptions: List<PollOptionViewState.PollEnded> = emptyList()

    @EpoxyAttribute
    var totalVotesStatus: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var clickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(clickListener)
        holder.date.text = formattedDate
        holder.title.text = title
        holder.winnerOptions.removeAllViews()
        holder.winnerOptions.isVisible = winnerOptions.isNotEmpty()
        for (winnerOption in winnerOptions) {
            val optionView = PollOptionView(holder.view.context)
            holder.winnerOptions.addView(optionView)
            optionView.render(winnerOption)
        }
        holder.totalVotes.setTextOrHide(totalVotesStatus)
    }

    class Holder : VectorEpoxyHolder() {
        val date by bind<TextView>(R.id.pollDate)
        val title by bind<TextView>(R.id.pollTitle)
        val winnerOptions by bind<LinearLayout>(R.id.pollWinnerOptionsContainer)
        val totalVotes by bind<TextView>(R.id.pollTotalVotes)
    }
}
