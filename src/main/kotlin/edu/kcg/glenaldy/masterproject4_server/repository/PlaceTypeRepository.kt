package edu.kcg.glenaldy.masterproject4_server.repository

import edu.kcg.glenaldy.masterproject4_server.entity.PlaceType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaceTypeRepository : JpaRepository<PlaceType, Long?> {
    @Query("Select t FROM type t WHERE t.name LIKE :name")
    fun findByTypeNameOrNull(name: String): PlaceType?
}