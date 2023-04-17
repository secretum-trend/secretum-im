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
package com.messaging.scrtm.core.ui.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.messaging.scrtm.R
import com.messaging.scrtm.core.epoxy.ClickListener
import com.messaging.scrtm.core.epoxy.VectorEpoxyHolder
import com.messaging.scrtm.core.epoxy.VectorEpoxyModel
import com.messaging.scrtm.core.epoxy.onClick
import com.messaging.scrtm.core.extensions.setTextOrHide
import com.messaging.scrtm.features.themes.ThemeUtils
import com.messaging.lib.core.utils.epoxy.charsequence.EpoxyCharSequence

/**
 * A generic list item.
 * Displays an item with a title, and optional description.
 * Can display an accessory on the right, that can be an image or an indeterminate progress.
 * If provided with an action, will display a button at the bottom of the list item.
 */
@EpoxyModelClass
abstract class GenericWithValueItem : VectorEpoxyModel<GenericWithValueItem.Holder>(R.layout.item_generic_with_value) {

    @EpoxyAttribute
    var title: EpoxyCharSequence? = null

    @EpoxyAttribute
    var value: String? = null

    @EpoxyAttribute
    @ColorInt
    var valueColorInt: Int? = null

    @EpoxyAttribute
    @DrawableRes
    var titleIconResourceId: Int = -1

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickAction: ClickListener? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemLongClickAction: View.OnLongClickListener? = null

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.titleText.setTextOrHide(title?.charSequence)

        if (titleIconResourceId != -1) {
            holder.titleIcon.setImageResource(titleIconResourceId)
            holder.titleIcon.isVisible = true
        } else {
            holder.titleIcon.isVisible = false
        }

        holder.valueText.setTextOrHide(value)

        if (valueColorInt != null) {
            holder.valueText.setTextColor(valueColorInt!!)
        } else {
            holder.valueText.setTextColor(ThemeUtils.getColor(holder.view.context, R.attr.vctr_content_primary))
        }

        holder.view.onClick(itemClickAction)
        holder.view.setOnLongClickListener(itemLongClickAction)
    }

    class Holder : VectorEpoxyHolder() {
        val titleIcon by bind<ImageView>(R.id.itemGenericWithValueTitleIcon)
        val titleText by bind<TextView>(R.id.itemGenericWithValueLabelText)
        val valueText by bind<TextView>(R.id.itemGenericWithValueValueText)
    }
}