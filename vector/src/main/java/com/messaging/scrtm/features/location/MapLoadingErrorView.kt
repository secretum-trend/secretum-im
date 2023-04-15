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

package com.messaging.scrtm.features.location

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.messaging.scrtm.R
import com.messaging.scrtm.core.glide.GlideApp
import com.messaging.scrtm.databinding.ViewMapLoadingErrorBinding
import com.messaging.scrtm.features.themes.ThemeUtils

/**
 * Custom view to display an error when map fails to load.
 */
class MapLoadingErrorView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewMapLoadingErrorBinding.inflate(
            LayoutInflater.from(context),
            this
    )

    init {
        context.obtainStyledAttributes(
                attrs,
                R.styleable.MapLoadingErrorView,
                0,
                0
        ).use {
            setErrorDescription(it)
        }
    }

    private fun setErrorDescription(typedArray: TypedArray) {
        val description = typedArray.getString(R.styleable.MapLoadingErrorView_mapErrorDescription)
        if (description.isNullOrEmpty()) {
            binding.mapLoadingErrorDescription.setText(R.string.location_share_loading_map_error)
        } else {
            binding.mapLoadingErrorDescription.text = description
        }
    }

    fun render(mapLoadingErrorViewState: MapLoadingErrorViewState) {
        GlideApp.with(binding.mapLoadingErrorBackground)
                .load(ColorDrawable(ThemeUtils.getColor(context, R.attr.vctr_system)))
                .transform(mapLoadingErrorViewState.backgroundTransformation)
                .into(binding.mapLoadingErrorBackground)
    }
}
