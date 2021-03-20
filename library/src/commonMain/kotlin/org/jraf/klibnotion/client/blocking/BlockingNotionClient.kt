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

@file:JvmName("BlockingNotionClientUtils")

package org.jraf.klibnotion.client.blocking

import org.jraf.klibnotion.client.NotionClient
import org.jraf.klibnotion.internal.client.blocking.BlockingNotionClientImpl
import org.jraf.klibnotion.model.base.UuidString
import org.jraf.klibnotion.model.database.Database
import org.jraf.klibnotion.model.database.query.DatabaseQuery
import org.jraf.klibnotion.model.database.query.DatabaseQuerySort
import org.jraf.klibnotion.model.page.Page
import org.jraf.klibnotion.model.pagination.Pagination
import org.jraf.klibnotion.model.pagination.ResultPage
import org.jraf.klibnotion.model.property.content.ContentValueList
import org.jraf.klibnotion.model.property.content.ContentValueListProducer
import org.jraf.klibnotion.model.property.value.PropertyValueList
import org.jraf.klibnotion.model.user.User
import kotlin.jvm.JvmName

/**
 * A 'blocking' version of a Notion client.
 *
 * All the methods here are blocking, meaning the calling thread will wait for the
 * result to be available.
 *
 * This is useful from Java, which doesn't have a notion of `suspend` functions.
 */
interface BlockingNotionClient {
    /**
     * See [NotionClient.Users].
     */
    interface Users {
        /**
         * See [NotionClient.Users.getUser].
         */
        fun getUser(id: UuidString): User

        /**
         * See [NotionClient.Users.getUserList].
         */
        fun getUserList(pagination: Pagination = Pagination()): ResultPage<User>
    }

    /**
     * See [NotionClient.Databases].
     */
    interface Databases {
        /**
         * See [NotionClient.Databases.getDatabase].
         */
        fun getDatabase(id: UuidString): Database

        /**
         * See [NotionClient.Databases.queryDatabase].
         */
        fun queryDatabase(
            id: UuidString,
            query: DatabaseQuery? = null,
            sort: DatabaseQuerySort? = null,
            pagination: Pagination = Pagination(),
        ): ResultPage<Page>
    }

    /**
     * See [NotionClient.Pages].
     */
    interface Pages {
        /**
         * See [NotionClient.Pages.getPage].
         */
        fun getPage(id: UuidString, isArchived: Boolean = false): Page

        /**
         * See [NotionClient.Pages.createPage].
         */
        fun createPage(
            parentDatabaseId: UuidString,
            properties: PropertyValueList = PropertyValueList(),
            content: ContentValueList? = null,
        ): Page

        /**
         * See [NotionClient.Pages.createPage].
         */
        fun createPage(
            parentDatabaseId: UuidString,
            properties: PropertyValueList = PropertyValueList(),
            content: ContentValueListProducer,
        ): Page

        /**
         * See [NotionClient.Pages.updatePage].
         */
        fun updatePage(id: UuidString, properties: PropertyValueList): Page
    }

    /**
     * See [NotionClient.users].
     */
    val users: Users

    /**
     * See [NotionClient.databases].
     */
    val databases: Databases


    /**
     * See [NotionClient.pages].
     */
    val pages: Pages

    /**
     * See [NotionClient.close].
     */
    fun close()
}

/**
 * Get a blocking client from a [NotionClient].
 *
 * This is useful from Java, which doesn't have a notion of `suspend` functions.
 */
fun NotionClient.asBlockingNotionClient(): BlockingNotionClient {
    return BlockingNotionClientImpl(this)
}