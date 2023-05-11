/*
 * Copyright 2020 The Matrix.org Foundation C.I.C.
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

package org.matrix.android.sdk.api.session.room.model.message

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.matrix.android.sdk.api.session.crypto.model.EncryptedFileInfo
import org.matrix.android.sdk.api.session.events.model.Content
import org.matrix.android.sdk.api.session.room.model.relation.RelationDefaultContent

@JsonClass(generateAdapter = true)
data class MessageOfferContent(
    @Json(name = "body") override val body: String = "",
    @Json(name = "trade") val trade: String = "",
    @Json(name = "msgtype") override val msgType: String = MessageType.MSGTYPE_TEXT,
    override val relatesTo: RelationDefaultContent? = null,
    override val newContent: Content? = null,
): MessageContent

//this MessageOfferContent2 is class check offer. I have no better way
@JsonClass(generateAdapter = true)
data class MessageOfferContent2(
    @Json(name = "body") override val body: String = "",
    val sender: String = "",
    @Json(name = "msgtype") override val msgType: String = MessageType.MSGTYPE_TEXT,
    override val relatesTo: RelationDefaultContent? = null,
    @Json(name = "content") override val newContent: Content? = null,
    val event_id: String? = null,
    val room_id: String? = null,
): MessageContent
