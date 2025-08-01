/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2025-present Benoit 'BoD' Lubek (BoD@JRAF.org)
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

package org.jraf.klibnotion.internal.api.model.file

import org.jraf.klibnotion.internal.api.model.ApiConverter
import org.jraf.klibnotion.internal.model.file.FileUploadImpl
import org.jraf.klibnotion.model.file.FileUpload

internal object ApiFileUploadConverter : ApiConverter<ApiFileUpload, FileUpload>() {

    override fun apiToModel(apiModel: ApiFileUpload) = FileUploadImpl(
        id = apiModel.id,
        `object` = apiModel.`object`,
        created_time = apiModel.created_time,
        last_edited_time = apiModel.last_edited_time,
        file_upload = apiModel.file_upload,
        upload_url = apiModel.upload_url,
        archived = apiModel.archived,
        status = apiModel.status,
        fileName = apiModel.fileName,
        contentType = apiModel.contentType,
        content_length = apiModel.content_length
    )
}