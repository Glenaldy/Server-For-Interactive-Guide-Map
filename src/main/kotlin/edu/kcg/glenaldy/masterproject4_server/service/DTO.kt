package edu.kcg.glenaldy.masterproject4_server.service

import edu.kcg.glenaldy.masterproject4_server.entity.*

class PlaceSanitized(place: Place, article: Article?) {
    val id: Long = place.id ?: (-1)
    val name: String = place.name ?: ("")
    val pos: Pos = place.pos ?: Pos(0.0, 0.0)
    val area: List<Pos> = place.area
    val type: String = place.type?.let { it.name } ?: ""
    val article: Long = article?.let { article.id } ?: -1
    val identifier: String? = place.identifier
    val zoom: Long? = place.zoom
    val region: Long? = place.region?.let { it.id }
    val prefecture: Long? = place.prefecture?.let { it.id }
    val city: Long? = place.city?.let { it.id }
}

/**
 * Type which does not have reference of the place
 */
class PlaceTypeSanitized(placeType: PlaceType) {
    val id: Long = placeType.id ?: (-1)
    val name: String = placeType.name ?: ("")
}

/**
 * Type with reference to the place, the place's type inside doesn't have reference back to the place again
 */
class TypeSanitized(placeType: PlaceType, placeDB: List<Place>) {
    val id: Long = placeType.id ?: (-1)
    val name: String = placeType.name ?: ("")
    val places: MutableSet<Long> = mutableSetOf<Long>().also { placeCollection ->
        placeDB.map { place ->
            if (place.type?.id == placeType.id) place.id?.let { placeCollection.add(it) }
        }
    }
}

class ArticleSanitized(article: Article, articleDB: List<Article>) {
    val id: Long = article.id ?: (-1)

    val place: PlaceSanitized? = article.place?.let { PlaceSanitized(it, article) }

    val title: String = article.title ?: ("")

    val subtitle: String = article.subtitle ?: ("")

    val content: String = article.content ?: ("")

    val placeReferences: MutableList<ReferenceSanitized> = mutableListOf<ReferenceSanitized>().also {
        article.placeReferences.forEach { reference ->
            val articleCorrespondence = articleDB.find { article ->
                article.place == reference.place
            }
            it.add(ReferenceSanitized(reference, articleCorrespondence))
        }
    }
    val ranking: Long? = article.ranking

    val images: MutableList<ImageSanitized> = mutableListOf<ImageSanitized>().also {
        article.images.forEach { image ->
            it.add(ImageSanitized(image))
        }
    }
}

class ImageSanitized(image: Image) {
    val id: Long? = image.id
    val title: String? = image.title
    val identifier: String? = image.identifier
    val description: String? = image.description
}

class ReferenceSanitized(reference: PlaceReference, article: Article?) {
    val reference: String = reference.reference ?: ("")
    val text: String = reference.text ?: ("")
    val place: PlaceSanitized? = reference.place?.let { PlaceSanitized(it, article) }
}

