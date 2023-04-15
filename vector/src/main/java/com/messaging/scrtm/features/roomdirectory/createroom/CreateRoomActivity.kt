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

package com.messaging.scrtm.features.roomdirectory.createroom

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.core.extensions.addFragment
import com.messaging.scrtm.core.platform.VectorBaseActivity
import com.messaging.scrtm.databinding.ActivitySimpleBinding
import com.messaging.scrtm.features.analytics.plan.MobileScreen
import com.messaging.scrtm.features.roomdirectory.RoomDirectorySharedAction
import com.messaging.scrtm.features.roomdirectory.RoomDirectorySharedActionViewModel
import com.messaging.lib.core.utils.compat.getParcelableCompat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Simple container for [CreateRoomFragment].
 */
@AndroidEntryPoint
class CreateRoomActivity : VectorBaseActivity<ActivitySimpleBinding>() {

    private lateinit var sharedActionViewModel: RoomDirectorySharedActionViewModel

    override fun getBinding() = ActivitySimpleBinding.inflate(layoutInflater)

    override fun getCoordinatorLayout() = views.coordinatorLayout

    override fun initUiAndData() {
        if (isFirstCreation()) {
            val fragmentArgs: CreateRoomArgs = intent?.extras?.getParcelableCompat(Mavericks.KEY_ARG) ?: return
            addFragment(
                    views.simpleFragmentContainer,
                    CreateRoomFragment::class.java,
                    fragmentArgs
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsScreenName = MobileScreen.ScreenName.CreateRoom
        sharedActionViewModel = viewModelProvider.get(RoomDirectorySharedActionViewModel::class.java)
        sharedActionViewModel
                .stream()
                .onEach { sharedAction ->
                    when (sharedAction) {
                        is RoomDirectorySharedAction.Back,
                        is RoomDirectorySharedAction.Close -> finish()
                        is RoomDirectorySharedAction.CreateRoomSuccess -> {
                            setResult(Activity.RESULT_OK, Intent().apply { putExtra(RESULT_CREATED_ROOM_ID, sharedAction.createdRoomId) })
                            finish()
                        }
                        else -> {
                            // nop
                        }
                    }
                }
                .launchIn(lifecycleScope)
    }

    companion object {

        private const val RESULT_CREATED_ROOM_ID = "RESULT_CREATED_ROOM_ID"

        fun getIntent(
                context: Context,
                initialName: String = "",
                isSpace: Boolean = false,
                openAfterCreate: Boolean = true,
                currentSpaceId: String? = null
        ): Intent {
            return Intent(context, CreateRoomActivity::class.java).apply {
                putExtra(
                        Mavericks.KEY_ARG, CreateRoomArgs(
                        initialName = initialName,
                        isSpace = isSpace,
                        openAfterCreate = openAfterCreate,
                        parentSpaceId = currentSpaceId
                )
                )
            }
        }

        fun getCreatedRoomId(data: Intent?): String? {
            return data?.extras?.getString(RESULT_CREATED_ROOM_ID)
        }
    }
}
