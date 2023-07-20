package edu.kcg.glenaldy.masterproject4_server.entity

import javax.persistence.*

@Entity(name = "image")
class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "image_title")
    var title: String? = ""

    @Column(name = "image_identifier", unique = true)
    var identifier: String? = ""

    @Column(name = "image_description", columnDefinition = "TEXT")
    var description: String? = ""

    @Lob
    var image: ByteArray? = null

    @ManyToOne(fetch = FetchType.LAZY)
    var article: Article? = null
}