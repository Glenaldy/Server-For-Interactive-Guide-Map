package edu.kcg.glenaldy.masterproject4_server.controller

import edu.kcg.glenaldy.masterproject4_server.entity.*
import edu.kcg.glenaldy.masterproject4_server.repository.ArticleRepository
import edu.kcg.glenaldy.masterproject4_server.repository.ImageRepository
import edu.kcg.glenaldy.masterproject4_server.repository.PlaceRepository
import edu.kcg.glenaldy.masterproject4_server.repository.PlaceTypeRepository
import edu.kcg.glenaldy.masterproject4_server.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Controller
@RequestMapping
class PlaceController(
        @Autowired val placeRepository: PlaceRepository,
        @Autowired val articleRepository: ArticleRepository,
        @Autowired val placeTypeRepository: PlaceTypeRepository,
        @Autowired val imageRepository: ImageRepository,
        @Autowired val imageService: ImageService
) {

    @GetMapping("/")
    @CrossOrigin(origins = ["http://localhost:3000"])
    fun getHome(): ResponseEntity<String> {
        return ResponseEntity("Master Project 4 Server by Glenaldy", HttpStatus.OK)
    }

    @GetMapping("/places")
    @CrossOrigin(origins = ["http://localhost:3000"])
    fun getAllPlace(): ResponseEntity<List<Any>> {
            val places = placeRepository.findAll()
            val placeSanitized = mutableListOf<PlaceSanitized>()

            places.forEach { place ->
                val article = place.let { articleRepository.findByPlaceOrNull(it) }
                placeSanitized.add(PlaceSanitized(place, article))
            }

            return ResponseEntity(placeSanitized, HttpStatus.OK)
    }

    @GetMapping("/types")
    @CrossOrigin(origins = ["http://localhost:3000"])
    fun getAllType(): ResponseEntity<List<Any>> {
        val types = placeTypeRepository.findAll()
        val places = placeRepository.findAll()
        val typeSanitized = mutableListOf<TypeSanitized>()

        types.forEach { type ->
            typeSanitized.add(TypeSanitized(type, places))
        }

        return ResponseEntity(typeSanitized, HttpStatus.OK)
    }

    @GetMapping("/articles")
    @CrossOrigin(origins = ["http://localhost:3000"])
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    fun getAllArticle(): ResponseEntity<List<Any>> {
        val articles = articleRepository.findAll()
        val articleSanitized = mutableListOf<ArticleSanitized>()

        articles.forEach { article ->
            articleSanitized.add(ArticleSanitized(article, articles))
        }
        return ResponseEntity(articleSanitized, HttpStatus.OK)
    }

    @PostMapping("/types", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postTypes(@RequestBody reqTypes: List<PlaceTypeSanitized>): ResponseEntity<Any> {
        reqTypes.forEach { type ->
            placeTypeRepository.save(PlaceType().also {
                it.name = type.name
            })
        }
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/places", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postPlaces(@RequestBody reqPlaces: List<PlaceSchema>): ResponseEntity<Any> {
        reqPlaces.forEach { place ->
            placeRepository.save(Place().also { newPlace ->
                newPlace.name = place.name
                newPlace.pos = place.pos
                newPlace.area = place.area?.let { it.toMutableList() } ?: mutableListOf()
                newPlace.type = placeTypeRepository.findByTypeNameOrNull(place.type ?: "")
                newPlace.identifier = place.identifier
                newPlace.zoom = place.zoom
                newPlace.region = placeRepository.findByIdentifierOrNull(place.region ?: "")
                newPlace.prefecture = placeRepository.findByIdentifierOrNull(place.prefecture ?: "")
                newPlace.city = placeRepository.findByIdentifierOrNull(place.city ?: "")
            })
        }
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/articles", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postArticles(@RequestBody reqArticles: List<ArticleSchema>): ResponseEntity<Any> {
        reqArticles.forEach { article ->
            articleRepository.save(Article().also { newArticle ->
                newArticle.place = placeRepository.findByIdentifierOrNull(article.place ?: "")
                newArticle.title = article.title
                newArticle.subtitle = article.subtitle
                newArticle.content = article.content
                article.placeReferences.forEach { ref ->
                    newArticle.placeReferences.add(PlaceReference().also {
                        it.reference = ref.reference
                        it.text = ref.text
                        it.place = placeRepository.findByIdentifierOrNull(ref.place ?: "")
                    })
                }
                newArticle.ranking = article.ranking
            })
        }
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("/images")
    fun postImage(@RequestPart("jsonData") jsonData: ImageSchema,
                  @RequestPart("image") image: MultipartFile
    ): ResponseEntity<Any> {
        if (!image.isEmpty) {
            val image = imageService.saveImage(image.bytes, jsonData)
            println(image)
        }
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/images/{imageIdentifier}")
    @CrossOrigin(origins = ["http://localhost:3000"])
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    fun getImageByPlace(@PathVariable imageIdentifier: String): ResponseEntity<InputStreamResource> {
        val image = imageRepository.findByIdentifierOrNull(imageIdentifier)
        println(image)
        if (image != null) {
            val imageData = image.image
            val inputStream = ByteArrayInputStream(imageData)
            val inputStreamResource = InputStreamResource(inputStream)
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(inputStreamResource)
        }
        return ResponseEntity(HttpStatus.BAD_REQUEST)

    }
}
