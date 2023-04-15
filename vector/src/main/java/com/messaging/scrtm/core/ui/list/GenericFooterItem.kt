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

import android.view.Gravity
import android.widget.TextView
import androidx.annotation.ColorInt
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
abstract class GenericFooterItem : VectorEpoxyModel<GenericFooterItem.Holder>(R.layout.item_generic_footer) {

    @EpoxyAttribute
    var text: EpoxyCharSequence? = null

    @EpoxyAttribute
    var style: ItemStyle = ItemStyle.NORMAL_TEXT

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    var itemClickAction: ClickListener? = null

    @EpoxyAttribute
    var centered: Boolean = true

    @EpoxyAttribute
    @ColorInt
    var textColor: Int? = null

    override fun bind(holder: Holder) {
        super.bind(holder)

        holder.text.setTextOrHide(text?.charSequence)
        holder.text.typeface = style.toTypeFace()
        holder.text.textSize = style.toTextSize()
        holder.text.gravity = if (centered) Gravity.CENTER_HORIZONTAL else Gravity.START

        if (textColor != null) {
            holder.text.setTextColor(textColor!!)
        } else {
            holder.text.setTextColor(ThemeUtils.getColor(holder.view.context, R.attr.vctr_content_secondary))
        }

        holder.view.onClick(itemClickAction)
    }

    class Holder : VectorEpoxyHolder() {
        val text by bind<TextView>(R.id.itemGenericFooterText)
    }
}
