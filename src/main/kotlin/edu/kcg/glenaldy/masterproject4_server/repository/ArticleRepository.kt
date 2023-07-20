package edu.kcg.glenaldy.masterproject4_server.repository

import edu.kcg.glenaldy.masterproject4_server.entity.Article
import edu.kcg.glenaldy.masterproject4_server.entity.Place
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ArticleRepository : JpaRepository<Article, Long?> {
    @Query("SELECT a FROM article a WHERE a.place = :place")
    fun findByPlaceOrNull(@Param("place") place: Place): Article?
}
