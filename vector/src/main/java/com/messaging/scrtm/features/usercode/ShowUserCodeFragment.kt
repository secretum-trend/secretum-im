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

package com.messaging.scrtm.features.usercode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.core.extensions.setTextOrHide
import com.messaging.scrtm.core.platform.VectorBaseFragment
import com.messaging.scrtm.core.utils.PERMISSIONS_FOR_TAKING_PHOTO
import com.messaging.scrtm.core.utils.checkPermissions
import com.messaging.scrtm.core.utils.registerForPermissionsResult
import com.messaging.scrtm.core.utils.startSharePlainTextIntent
import com.messaging.scrtm.databinding.FragmentUserCodeShowBinding
import com.messaging.scrtm.features.home.AvatarRenderer
import javax.inject.Inject

@AndroidEntryPoint
class ShowUserCodeFragment :
        VectorBaseFragment<FragmentUserCodeShowBinding>() {

    @Inject lateinit var avatarRenderer: AvatarRenderer

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentUserCodeShowBinding {
        return FragmentUserCodeShowBinding.inflate(inflater, container, false)
    }

    val sharedViewModel: UserCodeSharedViewModel by activityViewModel()

    private val openCameraActivityResultLauncher = registerForPermissionsResult { allGranted, deniedPermanently ->
        if (allGranted) {
            doOpenQRCodeScanner()
        } else {
            sharedViewModel.handle(UserCodeActions.CameraPermissionNotGranted(deniedPermanently))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(views.showUserCodeToolBar)
                .allowBack(useCross = true)

        views.showUserCodeScanButton.debouncedClicks {
            if (checkPermissions(PERMISSIONS_FOR_TAKING_PHOTO, requireActivity(), openCameraActivityResultLauncher)) {
                doOpenQRCodeScanner()
            }
        }
        views.showUserCodeShareButton.debouncedClicks {
            sharedViewModel.handle(UserCodeActions.ShareByText)
        }

        sharedViewModel.observeViewEvents {
            if (it is UserCodeShareViewEvents.SharePlainText) {
                startSharePlainTextIntent(
                        context = requireContext(),
                        activityResultLauncher = null,
                        chooserTitle = it.title,
                        text = it.text,
                        extraTitle = it.richPlainText
                )
            }
        }
    }

    private fun doOpenQRCodeScanner() {
        sharedViewModel.handle(UserCodeActions.SwitchMode(UserCodeState.Mode.SCAN))
    }

    override fun invalidate() = withState(sharedViewModel) { state ->
        state.matrixItem?.let { avatarRenderer.render(it, views.showUserCodeAvatar) }
        state.shareLink?.let { views.showUserCodeQRImage.setData(it) }
        views.showUserCodeCardNameText.setTextOrHide(state.matrixItem?.displayName)
        views.showUserCodeCardUserIdText.setTextOrHide(state.matrixItem?.id)
    }
}
