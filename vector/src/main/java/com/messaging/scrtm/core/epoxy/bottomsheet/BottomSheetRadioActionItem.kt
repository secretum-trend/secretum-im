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
 *
 */
package com.messaging.scrtm.core.epoxy.bottomsheet

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.messaging.scrtm.R
import com.messaging.scrtm.core.epoxy.ClickListener
import com.messaging.scrtm.core.epoxy.VectorEpoxyHolder
import com.messaging.scrtm.core.epoxy.VectorEpoxyModel
import com.messaging.scrtm.core.epoxy.onClick
import com.messaging.scrtm.core.extensions.setAttributeTintedImageResource
import com.messaging.scrtm.core.extensions.setTextOrHide

/**
 * A action for bottom sheet.
 */
@EpoxyModelClass
abstract class BottomSheetRadioActionItem : VectorEpoxyModel<BottomSheetRadioActionItem.Holder>(R.layout.item_bottom_sheet_radio) {

    @EpoxyAttribute
    var title: String? = null

    @StringRes
    @EpoxyAttribute
    var titleRes: Int? = null

    @EpoxyAttribute
    var selected = false

    @EpoxyAttribute
    var description: String? = null

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var listener: ClickListener

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.view.onClick(listener)
        if (titleRes != null) {
            holder.titleText.setText(titleRes!!)
        } else {
            holder.titleText.text = title
        }
        holder.descriptionText.setTextOrHide(description)

        if (selected) {
            holder.radioImage.setAttributeTintedImageResource(R.drawable.ic_radio_on, R.attr.colorPrimary)
            holder.radioImage.contentDescription = holder.view.context.getString(R.string.a11y_checked)
        } else {
            holder.radioImage.setImageDrawable(ContextCompat.getDrawable(holder.view.context, R.drawable.ic_radio_off))
            holder.radioImage.contentDescription = holder.view.context.getString(R.string.a11y_unchecked)
        }
    }

    class Holder : VectorEpoxyHolder() {
        val titleText by bind<TextView>(R.id.actionTitle)
        val descriptionText by bind<TextView>(R.id.actionDescription)
        val radioImage by bind<ImageView>(R.id.radioIcon)
    }
}
