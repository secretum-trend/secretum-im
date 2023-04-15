/*
 * Copyright (c) 2020 New Vector Ltd
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
package com.messaging.scrtm.features.discovery

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.google.android.material.switchmaterial.SwitchMaterial
import com.messaging.scrtm.R
import com.messaging.scrtm.core.epoxy.ClickListener
import com.messaging.scrtm.core.epoxy.VectorEpoxyHolder
import com.messaging.scrtm.core.epoxy.VectorEpoxyModel
import com.messaging.scrtm.core.epoxy.onClick
import com.messaging.scrtm.core.extensions.setTextOrHide

@EpoxyModelClass
abstract class SettingsItem : VectorEpoxyModel<SettingsItem.Holder>(R.layout.item_settings_simple_item) {

    @EpoxyAttribute
    var title: String? = null

    @EpoxyAttribute
    @StringRes
    var titleResId: Int? = null

    @EpoxyAttribute
    @StringRes
    var descriptionResId: Int? = null

    @EpoxyAttribute
    var description: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickListener: ClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        if (titleResId != null) {
            holder.titleText.setText(titleResId!!)
        } else {
            holder.titleText.setTextOrHide(title)
        }

        if (descriptionResId != null) {
            holder.descriptionText.setText(descriptionResId!!)
        } else {
            holder.descriptionText.setTextOrHide(description)
        }

        holder.switchButton.isVisible = false

        holder.view.onClick(itemClickListener)
    }

    class Holder : VectorEpoxyHolder() {
        val titleText by bind<TextView>(R.id.settings_item_title)
        val descriptionText by bind<TextView>(R.id.settings_item_description)
        val switchButton by bind<SwitchMaterial>(R.id.settings_item_switch)
    }
}
