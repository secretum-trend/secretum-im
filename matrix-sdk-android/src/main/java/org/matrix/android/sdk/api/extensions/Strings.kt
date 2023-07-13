/*
 * Copyright 2020 The Matrix.org Foundation C.I.C.
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

package org.matrix.android.sdk.api.extensions

fun CharSequence.ensurePrefix(prefix: CharSequence): CharSequence {
    return when {
        startsWith(prefix) -> this
        else -> "$prefix$this"
    }
}

/**
 * Append a new line and then the provided string.
 */
fun StringBuilder.appendNl(str: String) = append("\n").append(str)

/**
 * Returns null if the string is empty.
 */
fun String.ensureNotEmpty() = ifEmpty { null }

fun String.truncate(numberFirst: Int = 6, numberLast : Int = 3): String {
    return if (length > numberFirst + numberLast) {
        val firstThreeChars = take(numberFirst)
        val lastThreeChars = takeLast(numberLast)
        val middleDots = "..."
        "$firstThreeChars$middleDots$lastThreeChars"
    } else {
        this
    }
}
