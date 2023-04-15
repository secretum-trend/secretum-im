/*
 * Copyright 2020 New Vector Ltd
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
package com.messaging.scrtm.features.roommemberprofile.devices

import com.airbnb.epoxy.TypedEpoxyController
import com.messaging.scrtm.R
import com.messaging.scrtm.core.resources.ColorProvider
import com.messaging.scrtm.core.resources.StringProvider
import com.messaging.scrtm.core.ui.list.ItemStyle
import com.messaging.scrtm.core.ui.list.genericFooterItem
import com.messaging.scrtm.core.ui.list.genericItem
import com.messaging.scrtm.core.ui.list.genericWithValueItem
import com.messaging.scrtm.core.utils.DimensionConverter
import com.messaging.scrtm.features.crypto.verification.epoxy.bottomSheetVerificationActionItem
import com.messaging.scrtm.features.settings.VectorPreferences
import com.messaging.lib.core.utils.epoxy.charsequence.toEpoxyCharSequence
import me.gujun.android.span.span
import org.matrix.android.sdk.api.session.crypto.model.CryptoDeviceInfo
import javax.inject.Inject

class DeviceTrustInfoEpoxyController @Inject constructor(
        private val stringProvider: StringProvider,
        private val colorProvider: ColorProvider,
        private val dimensionConverter: DimensionConverter,
        private val vectorPreferences: VectorPreferences
) :
        TypedEpoxyController<DeviceListViewState>() {

    interface InteractionListener {
        fun onVerifyManually(device: CryptoDeviceInfo)
    }

    var interactionListener: InteractionListener? = null

    override fun buildModels(data: DeviceListViewState?) {
        val host = this
        data?.selectedDevice?.let { cryptoDeviceInfo ->
            val isVerified = cryptoDeviceInfo.trustLevel?.isVerified() == true
            genericItem {
                id("title")
                style(ItemStyle.BIG_TEXT)
                titleIconResourceId(if (isVerified) R.drawable.ic_shield_trusted else R.drawable.ic_shield_warning)
                title(
                        host.stringProvider
                                .getString(if (isVerified) R.string.verification_profile_verified else R.string.verification_profile_warning)
                                .toEpoxyCharSequence()
                )
            }
            genericFooterItem {
                id("desc")
                centered(false)
                textColor(host.colorProvider.getColorFromAttribute(R.attr.vctr_content_primary))
                apply {
                    if (isVerified) {
                        // TODO FORMAT
                        text(
                                host.stringProvider.getString(
                                        R.string.verification_profile_device_verified_because,
                                        data.userItem?.displayName ?: "",
                                        data.userItem?.id ?: ""
                                ).toEpoxyCharSequence()
                        )
                    } else {
                        // TODO what if mine
                        text(
                                host.stringProvider.getString(
                                        R.string.verification_profile_device_new_signing,
                                        data.userItem?.displayName ?: "",
                                        data.userItem?.id ?: ""
                                ).toEpoxyCharSequence()
                        )
                    }
                }
//                    text(stringProvider.getString(R.string.verification_profile_device_untrust_info))
            }

            genericWithValueItem {
                id(cryptoDeviceInfo.deviceId)
                titleIconResourceId(if (isVerified) R.drawable.ic_shield_trusted else R.drawable.ic_shield_warning)
                title(
                        span {
                            +(cryptoDeviceInfo.displayName() ?: "")
                            span {
                                text = " (${cryptoDeviceInfo.deviceId})"
                                textColor = host.colorProvider.getColorFromAttribute(R.attr.vctr_content_secondary)
                                textSize = host.dimensionConverter.spToPx(14)
                            }
                        }.toEpoxyCharSequence()
                )
            }

            if (!isVerified) {
                genericFooterItem {
                    id("warn")
                    centered(false)
                    textColor(host.colorProvider.getColorFromAttribute(R.attr.vctr_content_primary))
                    text(host.stringProvider.getString(R.string.verification_profile_device_untrust_info).toEpoxyCharSequence())
                }

                bottomSheetVerificationActionItem {
                    id("verify")
                    title(host.stringProvider.getString(R.string.cross_signing_verify_by_emoji))
                    titleColor(host.colorProvider.getColorFromAttribute(R.attr.colorPrimary))
                    iconRes(R.drawable.ic_arrow_right)
                    iconColor(host.colorProvider.getColorFromAttribute(R.attr.colorPrimary))
                    listener {
                        host.interactionListener?.onVerifyManually(cryptoDeviceInfo)
                    }
                }
            }
        }
    }
}
