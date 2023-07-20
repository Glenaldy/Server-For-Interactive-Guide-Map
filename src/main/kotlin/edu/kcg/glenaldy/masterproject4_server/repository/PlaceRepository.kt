package edu.kcg.glenaldy.masterproject4_server.repository

import edu.kcg.glenaldy.masterproject4_server.entity.Place
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PlaceRepository : JpaRepository<Place, Long?> {
    @Query("Select p FROM place p WHERE p.identifier = :identifier")
    fun findByIdentifierOrNull(@Param("identifier")identifier: String): Place?
}