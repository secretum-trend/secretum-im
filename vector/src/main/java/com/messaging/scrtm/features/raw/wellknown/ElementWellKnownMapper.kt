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

package com.messaging.scrtm.features.raw.wellknown

import com.squareup.moshi.JsonAdapter
import org.matrix.android.sdk.api.extensions.tryOrNull
import org.matrix.android.sdk.api.util.MatrixJsonParser

object ElementWellKnownMapper {

    val adapter: JsonAdapter<ElementWellKnown> = MatrixJsonParser.getMoshi().adapter(ElementWellKnown::class.java)

    fun from(value: String): ElementWellKnown? {
        return tryOrNull("Unable to parse well-known data") { adapter.fromJson(value) }
    }
}