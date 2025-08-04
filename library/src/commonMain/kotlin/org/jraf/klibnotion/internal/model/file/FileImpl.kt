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

package org.jraf.klibnotion.internal.model.file

import org.jraf.klibnotion.internal.api.model.file.ApiId
import org.jraf.klibnotion.internal.api.model.file.ApiUrl
import org.jraf.klibnotion.model.file.FILE_TYPE_EXTERNAL
import org.jraf.klibnotion.model.file.FILE_TYPE_FILE
import org.jraf.klibnotion.model.file.FILE_TYPE_FILE_UPLOAD
import org.jraf.klibnotion.model.file.File

data class FileImpl(
    override val type: String = FILE_TYPE_FILE, //"file", "file_upload", "external"
    override val external: ApiUrl? = null,
    override val file_upload: ApiId? = null,
    override val file: ApiUrl? = null,
) : File

data class UploadFileImpl(
    override val type: String = FILE_TYPE_FILE_UPLOAD, //"file", "file_upload", "external"
    override val external: ApiUrl? = null,
    override val file_upload: ApiId? = null,
    override val file: ApiUrl? = null,
) : File

data class ExternalFileImpl(
    override val type: String = FILE_TYPE_EXTERNAL, //"file", "file_upload", "external"
    override val external: ApiUrl? = null,
    override val file_upload: ApiId? = null,
    override val file: ApiUrl? = null,
) : File
