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

package org.jraf.klibnotion.internal.client

import io.ktor.client.*
import io.ktor.client.request.*
import org.jraf.klibnotion.internal.api.model.database.ApiDatabase
import org.jraf.klibnotion.internal.api.model.page.ApiPage
import org.jraf.klibnotion.internal.api.model.pagination.ApiResultPage
import org.jraf.klibnotion.internal.api.model.user.ApiUser
import org.jraf.klibnotion.model.base.UuidString

internal class NotionService(private val httpClient: HttpClient) {
    companion object {
        private const val BASE_URL = "https://api.notion.com/v1"

        private const val START_CURSOR = "start_cursor"

        private const val USERS = "users"
        private const val DATABASES = "databases"

    }

    // region Users

    suspend fun getUser(id: String): ApiUser {
        return httpClient.get("$BASE_URL/$USERS/$id")
    }


    suspend fun getUserList(startCursor: String?): ApiResultPage<ApiUser> {
        return httpClient.get("$BASE_URL/$USERS") {
            if (startCursor != null) parameter(START_CURSOR, startCursor)
        }
    }

    // endregion


    // region Databases

    suspend fun getDatabase(id: UuidString): ApiDatabase {
        return httpClient.get("$BASE_URL/$DATABASES/$id")
    }

    suspend fun queryDatabase(id: UuidString, startCursor: String?): ApiResultPage<ApiPage> {
        return httpClient.post("$BASE_URL/$DATABASES/$id/query") {
            if (startCursor != null) parameter(START_CURSOR, startCursor)
        }
    }

    // endregion

}
