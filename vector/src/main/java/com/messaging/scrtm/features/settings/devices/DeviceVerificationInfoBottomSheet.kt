/*
 * Copyright 2020 New Vector Ltd
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
package com.messaging.scrtm.features.settings.devices

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.parentFragmentViewModel
import com.airbnb.mvrx.withState
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.core.extensions.cleanup
import com.messaging.scrtm.core.extensions.configureWith
import com.messaging.scrtm.core.platform.VectorBaseBottomSheetDialogFragment
import com.messaging.scrtm.databinding.BottomSheetGenericListWithTitleBinding
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class DeviceVerificationInfoArgs(
        val userId: String,
        val deviceId: String
) : Parcelable

@AndroidEntryPoint
class DeviceVerificationInfoBottomSheet :
        VectorBaseBottomSheetDialogFragment<BottomSheetGenericListWithTitleBinding>(),
        DeviceVerificationInfoBottomSheetController.Callback {

    private val viewModel: DeviceVerificationInfoBottomSheetViewModel by fragmentViewModel(DeviceVerificationInfoBottomSheetViewModel::class)

    private val sharedViewModel: DevicesViewModel by parentFragmentViewModel(DevicesViewModel::class)

    @Inject lateinit var controller: DeviceVerificationInfoBottomSheetController

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): BottomSheetGenericListWithTitleBinding {
        return BottomSheetGenericListWithTitleBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views.bottomSheetRecyclerView.configureWith(
                controller,
                hasFixedSize = false
        )
        controller.callback = this
        views.bottomSheetTitle.isVisible = false
    }

    override fun onDestroyView() {
        views.bottomSheetRecyclerView.cleanup()
        super.onDestroyView()
    }

    override fun invalidate() = withState(viewModel) {
        controller.setData(it)
        super.invalidate()
    }

    companion object {
        fun newInstance(userId: String, deviceId: String): DeviceVerificationInfoBottomSheet {
            return DeviceVerificationInfoBottomSheet().apply {
                setArguments(DeviceVerificationInfoArgs(userId, deviceId))
            }
        }
    }

    override fun onAction(action: DevicesAction) {
        dismiss()
        sharedViewModel.handle(action)
    }
}
