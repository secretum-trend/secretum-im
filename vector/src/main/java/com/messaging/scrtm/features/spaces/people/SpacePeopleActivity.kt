/*
 * Copyright (c) 2021 New Vector Ltd
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

package com.messaging.scrtm.features.spaces.people

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.R
import com.messaging.scrtm.core.extensions.hideKeyboard
import com.messaging.scrtm.core.extensions.replaceFragment
import com.messaging.scrtm.core.platform.GenericIdArgs
import com.messaging.scrtm.core.platform.VectorBaseActivity
import com.messaging.scrtm.databinding.ActivitySimpleLoadingBinding
import com.messaging.scrtm.features.analytics.plan.ViewRoom
import com.messaging.scrtm.features.spaces.share.ShareSpaceBottomSheet
import com.messaging.lib.core.utils.compat.getParcelableExtraCompat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SpacePeopleActivity : VectorBaseActivity<ActivitySimpleLoadingBinding>() {

    override fun getBinding() = ActivitySimpleLoadingBinding.inflate(layoutInflater)

    private lateinit var sharedActionViewModel: SpacePeopleSharedActionViewModel

    override fun initUiAndData() {
        super.initUiAndData()
        waitingView = views.waitingView.waitingView
    }

    override fun showWaitingView(text: String?) {
        hideKeyboard()
        views.waitingView.waitingStatusText.isGone = views.waitingView.waitingStatusText.text.isNullOrBlank()
        super.showWaitingView(text)
    }

    override fun hideWaitingView() {
        views.waitingView.waitingStatusText.text = null
        views.waitingView.waitingStatusText.isGone = true
        views.waitingView.waitingHorizontalProgress.progress = 0
        views.waitingView.waitingHorizontalProgress.isVisible = false
        super.hideWaitingView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = intent?.getParcelableExtraCompat<GenericIdArgs>(Mavericks.KEY_ARG)
        if (isFirstCreation()) {
            replaceFragment(
                    views.simpleFragmentContainer,
                    SpacePeopleFragment::class.java,
                    args
            )
        }

        sharedActionViewModel = viewModelProvider.get(SpacePeopleSharedActionViewModel::class.java)
        sharedActionViewModel
                .stream()
                .onEach { sharedAction ->
                    when (sharedAction) {
                        SpacePeopleSharedAction.Dismiss -> finish()
                        is SpacePeopleSharedAction.NavigateToRoom -> navigateToRooms(sharedAction)
                        SpacePeopleSharedAction.HideModalLoading -> hideWaitingView()
                        SpacePeopleSharedAction.ShowModalLoading -> {
                            showWaitingView(getString(R.string.please_wait))
                        }
                        is SpacePeopleSharedAction.NavigateToInvite -> {
                            ShareSpaceBottomSheet.show(supportFragmentManager, sharedAction.spaceId)
                        }
                    }
                }.launchIn(lifecycleScope)
    }

    private fun navigateToRooms(action: SpacePeopleSharedAction.NavigateToRoom) {
        navigator.openRoom(
                context = this,
                roomId = action.roomId,
                trigger = ViewRoom.Trigger.MobileSpaceMembers
        )
        finish()
    }

    companion object {
        fun newIntent(context: Context, spaceId: String): Intent {
            return Intent(context, SpacePeopleActivity::class.java).apply {
                putExtra(Mavericks.KEY_ARG, GenericIdArgs(spaceId))
            }
        }
    }
}
