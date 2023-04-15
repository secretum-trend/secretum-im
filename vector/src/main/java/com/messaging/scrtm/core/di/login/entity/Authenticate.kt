package com.messaging.scrtm.core.di.login.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Authenticate(
    val username: String?,
    val password: String?,
    val accessToken: String?,
): Parcelable