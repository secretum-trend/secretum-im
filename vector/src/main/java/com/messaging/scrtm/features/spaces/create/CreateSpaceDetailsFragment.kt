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

package com.messaging.scrtm.features.spaces.create

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.activityViewModel
import com.messaging.scrtm.R
import com.messaging.scrtm.core.dialogs.GalleryOrCameraDialogHelper
import com.messaging.scrtm.core.dialogs.GalleryOrCameraDialogHelperFactory
import com.messaging.scrtm.core.extensions.configureWith
import com.messaging.scrtm.core.extensions.hideKeyboard
import com.messaging.scrtm.core.platform.OnBackPressed
import com.messaging.scrtm.core.platform.VectorBaseFragment
import com.messaging.scrtm.data.SessionPref
import com.messaging.scrtm.databinding.FragmentSpaceCreateGenericEpoxyFormBinding
import com.messaging.scrtm.features.spaces.popup.MessagePopup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class CreateSpaceDetailsFragment :
    VectorBaseFragment<FragmentSpaceCreateGenericEpoxyFormBinding>(),
    SpaceDetailEpoxyController.Listener,
    GalleryOrCameraDialogHelper.Listener,
    OnBackPressed {

    @Inject
    lateinit var epoxyController: SpaceDetailEpoxyController
    @Inject
    lateinit var galleryOrCameraDialogHelperFactory: GalleryOrCameraDialogHelperFactory
    @Inject
    lateinit var sessionPref: SessionPref

    private val sharedViewModel: CreateSpaceViewModel by activityViewModel()

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSpaceCreateGenericEpoxyFormBinding.inflate(layoutInflater, container, false)

    private lateinit var galleryOrCameraDialogHelper: GalleryOrCameraDialogHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryOrCameraDialogHelper = galleryOrCameraDialogHelperFactory.create(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views.recyclerView.configureWith(epoxyController)
        epoxyController.listener = this

        sharedViewModel.onEach {
            epoxyController.setData(it)
        }

        views.nextButton.debouncedClicks {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    showLoading(getString(R.string.loading))
                }
                view.hideKeyboard()
                val value = sharedViewModel.apolloSpaceClient.getSpaceWhiteList()
                val address = value?.space_creation_whitelist?.find { it.address == sessionPref.address }
                withContext(Dispatchers.Main) {
                    dismissLoadingDialog()
                }
                if (address != null){
                    withContext(Dispatchers.Main){
                        sharedViewModel.handle(CreateSpaceAction.NextFromDetails)
                    }
                }else{
                    withContext(Dispatchers.Main){
                        MessagePopup(getString(R.string.cant_creat_space), getString(R.string.cant_creat_space_des)).show(childFragmentManager, null)
                    }
                }
            }
        }
    }

    override fun onImageReady(uri: Uri?) {
        sharedViewModel.handle(CreateSpaceAction.SetAvatar(uri))
    }

    // -----------------------------
    // Epoxy controller listener methods
    // -----------------------------

    override fun onAvatarDelete() {
        sharedViewModel.handle(CreateSpaceAction.SetAvatar(null))
    }

    override fun onAvatarChange() {
        galleryOrCameraDialogHelper.show()
    }

    override fun onNameChange(newName: String) {
        sharedViewModel.handle(CreateSpaceAction.NameChanged(newName))
    }

    override fun onTopicChange(newTopic: String) {
        sharedViewModel.handle(CreateSpaceAction.TopicChanged(newTopic))
    }

    override fun setAliasLocalPart(aliasLocalPart: String) {
        sharedViewModel.handle(CreateSpaceAction.SpaceAliasChanged(aliasLocalPart))
    }

    override fun onBackPressed(toolbarButton: Boolean): Boolean {
        sharedViewModel.handle(CreateSpaceAction.OnBackPressed)
        return true
    }
}
