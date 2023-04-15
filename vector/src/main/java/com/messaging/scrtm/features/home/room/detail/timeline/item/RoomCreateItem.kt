/*
 * Copyright 2019 New Vector Ltd
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

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.messaging.scrtm.R
import com.messaging.scrtm.core.epoxy.VectorEpoxyHolder
import com.messaging.scrtm.core.epoxy.VectorEpoxyModel
import com.messaging.lib.core.utils.epoxy.charsequence.EpoxyCharSequence
import me.saket.bettermovementmethod.BetterLinkMovementMethod

@EpoxyModelClass
abstract class RoomCreateItem : VectorEpoxyModel<RoomCreateItem.Holder>(R.layout.item_timeline_event_create) {

    @EpoxyAttribute lateinit var text: EpoxyCharSequence

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.description.movementMethod = BetterLinkMovementMethod.getInstance()
        holder.description.text = text.charSequence
    }

    class Holder : VectorEpoxyHolder() {
        val description by bind<TextView>(R.id.roomCreateItemDescription)
    }
}
