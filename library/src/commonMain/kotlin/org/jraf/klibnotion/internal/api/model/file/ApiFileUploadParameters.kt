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

import kotlinx.serialization.Serializable

/**
 * See [Reference](https://developers.notion.com/reference/create-a-file-upload).
 */
@Serializable
internal data class ApiFileUploadParameters(
    val mode: String = "single_part", // "single_part" , "multi_part" or "external_url"
    val filename: String? = null, // Required when mode is multi_part or external_url
    val content_type: String? = null, // Recommended when sending the file in multiple parts. Must match the content type of the file that's sent
    val number_of_parts: Int = 1, // 1-1000, Required when mode is multi_part. The number of parts that will be sent in the request
    val external_url: String? = null, // Required when mode is external_url. The URL of the file to upload
)