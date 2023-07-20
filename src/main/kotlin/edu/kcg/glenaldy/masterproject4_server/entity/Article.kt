package edu.kcg.glenaldy.masterproject4_server.entity

import javax.persistence.*

@Entity(name = "article")
class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    var place: Place? = null

    @Column(name = "article_title")
    var title: String? = ""

    @Column(name = "article_subtitle", columnDefinition = "TEXT")
    var subtitle: String? = ""

    @Column(name = "article_content", columnDefinition = "TEXT")
    var content: String? = ""

    @ElementCollection
    @CollectionTable(name = "article_reference")
    var placeReferences: MutableList<PlaceReference> = mutableListOf()

    @Column(name ="ranking")
    var ranking: Long? = null

    @OneToMany(mappedBy = "article", cascade = [CascadeType.ALL], orphanRemoval = true)
    var images: MutableList<Image> = mutableListOf()
}

@Embeddable
class PlaceReference {
    var reference: String? = ""
    var text: String? = ""

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    var place: Place? = null
}
