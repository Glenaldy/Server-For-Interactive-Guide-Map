package edu.kcg.glenaldy.masterproject4_server.service

import edu.kcg.glenaldy.masterproject4_server.entity.Image
import edu.kcg.glenaldy.masterproject4_server.repository.ArticleRepository
import edu.kcg.glenaldy.masterproject4_server.repository.ImageRepository
import edu.kcg.glenaldy.masterproject4_server.repository.PlaceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ImageService(
        @Autowired val placeRepository: PlaceRepository,
        @Autowired val articleRepository: ArticleRepository,
        @Autowired val imageRepository: ImageRepository,
) {
    @Transactional
    fun saveImage(imageData: ByteArray, jsonData: ImageSchema): Image? {
        val place = placeRepository.findByIdentifierOrNull(jsonData.place)
        if (place != null) {
            val image = imageRepository.save(Image().also { newImage ->
                newImage.title = jsonData.title
                newImage.description = jsonData.description
                newImage.image = imageData
                newImage.article = articleRepository.findByPlaceOrNull(place)
                newImage.identifier = jsonData.identifier
            })
            return image
        }
        return null
    }
}