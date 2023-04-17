/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.messaging.scrtm.features.roomdirectory.picker

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.messaging.scrtm.R
import com.messaging.scrtm.core.epoxy.ClickListener
import com.messaging.scrtm.core.epoxy.VectorEpoxyHolder
import com.messaging.scrtm.core.epoxy.VectorEpoxyModel
import com.messaging.scrtm.core.epoxy.onClick
import com.messaging.scrtm.core.extensions.setTextOrHide
import com.messaging.scrtm.core.glide.GlideApp

@EpoxyModelClass
abstract class RoomDirectoryItem : VectorEpoxyModel<RoomDirectoryItem.Holder>(R.layout.item_room_directory) {

    @EpoxyAttribute
    var directoryAvatarUrl: String? = null

    @EpoxyAttribute
    var directoryName: String? = null

    @EpoxyAttribute
    var directoryDescription: String? = null

    @EpoxyAttribute
    var includeAllNetworks: Boolean = false

    @EpoxyAttribute
    var checked: Boolean = false

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var globalListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.rootView.onClick(globalListener)

        // Avatar
        GlideApp.with(holder.avatarView)
                .load(directoryAvatarUrl)
                .let {
                    if (!includeAllNetworks) {
                        it.placeholder(R.drawable.network_matrix)
                    } else {
                        it
                    }
                }
                .into(holder.avatarView)
        holder.avatarView.isInvisible = directoryAvatarUrl.isNullOrBlank() && includeAllNetworks

        holder.nameView.text = directoryName
        holder.descriptionView.setTextOrHide(directoryDescription)
        holder.checkedView.isVisible = checked
    }

    class Holder : VectorEpoxyHolder() {
        val rootView by bind<ViewGroup>(R.id.itemRoomDirectoryLayout)

        val avatarView by bind<ImageView>(R.id.itemRoomDirectoryAvatar)
        val nameView by bind<TextView>(R.id.itemRoomDirectoryName)
        val descriptionView by bind<TextView>(R.id.itemRoomDirectoryDescription)
        val checkedView by bind<View>(R.id.itemRoomDirectoryChecked)
    }
}