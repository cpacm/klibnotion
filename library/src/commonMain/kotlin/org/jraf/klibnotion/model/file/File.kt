/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2021-present Benoit 'BoD' Lubek (BoD@JRAF.org)
 * and contributors (https://github.com/BoD/klibnotion/graphs/contributors)
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

package org.jraf.klibnotion.model.file

import org.jraf.klibnotion.model.base.EmojiOrFile
import org.jraf.klibnotion.model.base.UuidString

//interface File : EmojiOrFile {
//    val url: String
//}

interface File {
    val type: String // "external", "file_upload", "file", "emoji"
    val external: FileUrl? // Only for type "external"
    val file_upload: FileId? // Only for type "file_upload"
    val file: FileUrl? // Only for type "file"
}

interface FileId {
    val id: UuidString // Only for type "file_upload"
}

interface FileUrl {
    val url: String // Only for type "external" or "file"
}

data class Emoji(
    override val type: String = "emoji",
    override val emoji: String,
    override val external: FileUrl? =null,
    override val file_upload: FileId? =null,
    override val file: FileUrl? = null,
) : EmojiOrFile

