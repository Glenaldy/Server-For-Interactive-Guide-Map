package edu.kcg.glenaldy.masterproject4_server.repository

import edu.kcg.glenaldy.masterproject4_server.entity.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.transaction.Transactional

interface ImageRepository:JpaRepository<Image, Long?> {
    @Query("Select i FROM image i WHERE i.identifier = :identifier")
    fun findByIdentifierOrNull(@Param("identifier")identifier: String): Image?
}