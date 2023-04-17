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

package com.messaging.scrtm.features.onboarding.ftueauth

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.core.extensions.hasContent
import com.messaging.scrtm.core.platform.SimpleTextWatcher
import com.messaging.scrtm.databinding.FragmentFtueDisplayNameBinding
import com.messaging.scrtm.features.onboarding.OnboardingAction
import com.messaging.scrtm.features.onboarding.OnboardingViewEvents
import com.messaging.scrtm.features.onboarding.OnboardingViewState

@AndroidEntryPoint
class FtueAuthChooseDisplayNameFragment :
        AbstractFtueAuthFragment<FragmentFtueDisplayNameBinding>() {

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFtueDisplayNameBinding {
        return FragmentFtueDisplayNameBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        views.displayNameInput.editText?.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable) {
                val newContent = s.toString()
                views.displayNameSubmit.isEnabled = newContent.isNotEmpty()
            }
        })
        views.displayNameInput.editText?.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    updateDisplayName()
                    true
                }
                else -> false
            }
        }

        views.displayNameSubmit.debouncedClicks { updateDisplayName() }
        views.displayNameSkip.debouncedClicks { viewModel.handle(OnboardingAction.UpdateDisplayNameSkipped) }
    }

    private fun updateDisplayName() {
        val newDisplayName = views.displayNameInput.editText?.text.toString()
        viewModel.handle(OnboardingAction.UpdateDisplayName(newDisplayName))
    }

    override fun updateWithState(state: OnboardingViewState) {
        views.displayNameInput.editText?.setText(state.personalizationState.displayName)
        views.displayNameSubmit.isEnabled = views.displayNameInput.hasContent()
    }

    override fun resetViewModel() {
        // Nothing to do
    }

    override fun onBackPressed(toolbarButton: Boolean): Boolean {
        viewModel.handle(OnboardingAction.PostViewEvent(OnboardingViewEvents.OnTakeMeHome))
        return true
    }
}