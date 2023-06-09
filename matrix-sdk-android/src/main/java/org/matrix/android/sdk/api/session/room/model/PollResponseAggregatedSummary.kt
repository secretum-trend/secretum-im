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
package org.matrix.android.sdk.api.session.room.model

data class PollResponseAggregatedSummary(
        val aggregatedContent: PollSummaryContent? = null,
        // If set the poll is closed (Clients SHOULD NOT consider responses after the close event)
        val closedTime: Long? = null,
        // Clients SHOULD validate that the option in the relationship is a valid option, and ignore the response if invalid
        val nbOptions: Int = 0,
        // The list of the eventIDs used to build the summary (might be out of sync if chunked received from message chunk)
        val sourceEvents: List<String>,
        val localEchos: List<String>,
        // list of related event ids which are encrypted due to decryption failure
        val encryptedRelatedEventIds: List<String>,
)
