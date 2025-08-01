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

package org.jraf.klibnotion.internal.api.model.block

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject
import org.jraf.klibnotion.internal.api.model.ApiConverter
import org.jraf.klibnotion.internal.api.model.base.ApiOutEmojiOrFileConverter
import org.jraf.klibnotion.internal.api.model.modelToApi
import org.jraf.klibnotion.internal.api.model.richtext.ApiOutRichTextListConverter
import org.jraf.klibnotion.model.block.AudioBlock
import org.jraf.klibnotion.model.block.Block
import org.jraf.klibnotion.model.block.BookmarkBlock
import org.jraf.klibnotion.model.block.BulletedListItemBlock
import org.jraf.klibnotion.model.block.CalloutBlock
import org.jraf.klibnotion.model.block.ChildDatabaseBlock
import org.jraf.klibnotion.model.block.ChildPageBlock
import org.jraf.klibnotion.model.block.CodeBlock
import org.jraf.klibnotion.model.block.DividerBlock
import org.jraf.klibnotion.model.block.EmbedBlock
import org.jraf.klibnotion.model.block.EquationBlock
import org.jraf.klibnotion.model.block.FileBlock
import org.jraf.klibnotion.model.block.Heading1Block
import org.jraf.klibnotion.model.block.Heading2Block
import org.jraf.klibnotion.model.block.Heading3Block
import org.jraf.klibnotion.model.block.ImageBlock
import org.jraf.klibnotion.model.block.NumberedListItemBlock
import org.jraf.klibnotion.model.block.ParagraphBlock
import org.jraf.klibnotion.model.block.PdfBlock
import org.jraf.klibnotion.model.block.QuoteBlock
import org.jraf.klibnotion.model.block.SyncedBlock
import org.jraf.klibnotion.model.block.TableBlock
import org.jraf.klibnotion.model.block.TableOfContentsBlock
import org.jraf.klibnotion.model.block.TableRowBlock
import org.jraf.klibnotion.model.block.ToDoBlock
import org.jraf.klibnotion.model.block.ToggleBlock
import org.jraf.klibnotion.model.block.UnknownTypeBlock
import org.jraf.klibnotion.model.block.VideoBlock
import org.jraf.klibnotion.model.file.File
import org.jraf.klibnotion.model.richtext.RichTextList

internal object ApiOutBlockConverter : ApiConverter<JsonElement, Block>() {

    override fun modelToApi(model: Block): JsonElement {
        return buildJsonObject {
            put("object", "block")

            val type = when (model) {
                is ParagraphBlock -> "paragraph"
                is Heading1Block -> "heading_1"
                is Heading2Block -> "heading_2"
                is Heading3Block -> "heading_3"
                is BulletedListItemBlock -> "bulleted_list_item"
                is NumberedListItemBlock -> "numbered_list_item"
                is ToDoBlock -> "to_do"
                is ToggleBlock -> "toggle"
                is CodeBlock -> "code"
                is BookmarkBlock -> "bookmark"
                is EquationBlock -> "equation"
                is QuoteBlock -> "quote"
                is EmbedBlock -> "embed"
                is CalloutBlock -> "callout"
                is DividerBlock -> "divider"
                is TableOfContentsBlock -> "table_of_contents"
                is ImageBlock -> "image"
                is VideoBlock -> "video"
                is FileBlock -> "file"
                is SyncedBlock -> "synced_block"
                is TableBlock -> "table"
                is TableRowBlock -> "table_row"
                is PdfBlock -> "pdf"
                is AudioBlock -> "audio"

                is UnknownTypeBlock -> throw IllegalStateException("Unknown type: ${model.type}")
                else -> throw IllegalStateException("Converter not implemented for ${model::class.simpleName}")
            }
            put("type", type)
            putJsonObject(type) {
                when (model) {
                    is Heading1Block -> model.text?.let { richText(it) }
                    is Heading2Block -> model.text?.let { richText(it) }
                    is Heading3Block -> model.text?.let { richText(it) }
                    is BulletedListItemBlock -> model.text?.let { richText(it) }
                    is NumberedListItemBlock -> model.text?.let { richText(it) }
                    is ToggleBlock -> model.text?.let { richText(it) }
                    is ParagraphBlock -> model.text?.let { richText(it) }
                    is QuoteBlock -> model.text?.let { richText(it) }
                    is ToDoBlock -> {
                        model.text?.let { richText(it) }
                        put("checked", model.checked)
                    }

                    is CodeBlock -> {
                        model.text?.let { richText(it) }
                        put("language", model.language)
                    }

                    is BookmarkBlock -> {
                        put("url", model.url)
                        model.caption?.let {
                            put("caption", it.modelToApi(ApiOutRichTextListConverter))
                        }
                    }

                    is EquationBlock -> put("expression", model.expression)
                    is EmbedBlock -> put("url", model.url)
                    is CalloutBlock -> {
                        model.text?.let { richText(it) }
                        model.icon?.let {
                            put("icon", it.modelToApi(ApiOutEmojiOrFileConverter))
                        }
                    }

                    is ImageBlock -> {
                        putFileBlock(model.image){
                            model.caption?.let {
                                put("caption", it.modelToApi(ApiOutRichTextListConverter))
                            }
                        }
                    }

                    is FileBlock -> {
                        putFileBlock(model.file){
                            model.caption?.let {
                                put("caption", it.modelToApi(ApiOutRichTextListConverter))
                            }
                        }
                    }

                    is VideoBlock -> {
                        putFileBlock(model.video){
                            model.caption?.let {
                                put("caption", it.modelToApi(ApiOutRichTextListConverter))
                            }
                        }
                    }

                    is PdfBlock -> {
                        model.caption?.let {
                            put("caption", it.modelToApi(ApiOutRichTextListConverter))
                        }
                        putFileBlock(model.pdf){
                            model.caption?.let {
                                put("caption", it.modelToApi(ApiOutRichTextListConverter))
                            }
                        }
                    }

                    is AudioBlock -> {
                        putFileBlock(model.audio){
                            model.caption?.let {
                                put("caption", it.modelToApi(ApiOutRichTextListConverter))
                            }
                        }
                    }

                    is SyncedBlock -> {
                        putJsonObject("synced_from") {
                            put("block_id", model.syncedFrom)
                        }
                    }

                    is ChildPageBlock, is ChildDatabaseBlock -> {
                        put("type", "page_id")
                        put("page_id", model.id)
                    }

                    is TableBlock -> {
                        put("table_width", model.tableWidth)
                        put("has_column_header", model.hasColumnHeader)
                        put("has_row_header", model.hasRowHeader)
                    }

                    is TableRowBlock -> {
                        model.cells(this@putJsonObject)
                    }

                    is UnknownTypeBlock,
                    is DividerBlock,
                    is TableOfContentsBlock,
                        -> {
                    }
                }
                model.children?.let {
                    if (it.isNotEmpty()) put(
                        "children",
                        JsonArray(it.modelToApi(ApiOutBlockConverter))
                    )
                }
            }
        }
    }

    private fun JsonObjectBuilder.putFileBlock(file: File,builderAction: JsonObjectBuilder.() -> Unit) {
        when (file.type) {
            "file" -> {
                put("type", "file")
                putJsonObject("file") {
                    put("url", file.file?.url)
                }
                builderAction()
            }

            "external" -> {
                put("type", "external")
                putJsonObject("external") {
                    put("url", file.external?.url)
                }
                builderAction()
            }

            "file_upload" -> {
                put("type", "file_upload")
                putJsonObject("file_upload") {
                    put("id", file.file_upload?.id)
                }
                builderAction()
            }
        }
    }

    private fun TableRowBlock.cells(jsonBuilder: JsonObjectBuilder) {
        jsonBuilder.putJsonArray("cells") {
            cells.map { row ->
                row.modelToApi(ApiOutRichTextListConverter)
            }
        }
    }

    private fun JsonObjectBuilder.richText(richTextList: RichTextList) =
        put("rich_text", richTextList.modelToApi(ApiOutRichTextListConverter))
}
