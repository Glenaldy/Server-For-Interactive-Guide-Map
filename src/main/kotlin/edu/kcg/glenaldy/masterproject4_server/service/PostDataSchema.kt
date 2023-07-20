package edu.kcg.glenaldy.masterproject4_server.service

import edu.kcg.glenaldy.masterproject4_server.entity.Pos

class ArticleSchema(
        val place: String? = "",
        val title: String? = "",
        val subtitle: String? = "",
        val content: String? = "",
        val placeReferences: List<PlaceReferenceSchema> = listOf(),
        val ranking: Long? = null
)

class PlaceReferenceSchema(
        val reference: String? = "",
        val text: String? = "",
        val place: String? = ""
)

class PlaceSchema(
        val identifier: String? = "",
        val zoom: Long? = 10,
        val region: String? = "",
        val prefecture: String? = "",
        val city: String? = "",
        val name: String? = "",
        val pos: Pos? = Pos(),
        val area: List<Pos>? = listOf(),
        val type: String? = "",
        val article: Long? = -1
)

class TypeSchema(
        val name: String? = ""
)

class ImageSchema(
        val title: String? = "",
        val identifier: String? = "",
        val description: String? = "",
        val place: String = ""
)