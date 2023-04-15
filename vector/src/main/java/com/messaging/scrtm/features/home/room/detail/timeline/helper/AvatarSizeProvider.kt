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

package com.messaging.scrtm.features.home.room.detail.timeline.helper

import com.messaging.scrtm.core.utils.DimensionConverter
import com.messaging.scrtm.features.home.room.detail.timeline.style.TimelineLayoutSettings
import com.messaging.scrtm.features.home.room.detail.timeline.style.TimelineLayoutSettingsProvider
import javax.inject.Inject

class AvatarSizeProvider @Inject constructor(
        private val dimensionConverter: DimensionConverter,
        private val layoutSettingsProvider: TimelineLayoutSettingsProvider
) {

    private val avatarStyle by lazy {
        when (layoutSettingsProvider.getLayoutSettings()) {
            TimelineLayoutSettings.MODERN -> AvatarStyle.SMALL
            TimelineLayoutSettings.BUBBLE -> AvatarStyle.BUBBLE
        }
    }

    val leftGuideline: Int by lazy {
        dimensionConverter.dpToPx(avatarStyle.avatarSizeDP + avatarStyle.marginDP)
    }

    val avatarSize: Int by lazy {
        dimensionConverter.dpToPx(avatarStyle.avatarSizeDP)
    }

    companion object {

        enum class AvatarStyle(val avatarSizeDP: Int, val marginDP: Int) {
            BIG(50, 8),
            MEDIUM(40, 8),
            SMALL(30, 8),
            BUBBLE(28, 4),
            NONE(0, 8)
        }
    }
}
