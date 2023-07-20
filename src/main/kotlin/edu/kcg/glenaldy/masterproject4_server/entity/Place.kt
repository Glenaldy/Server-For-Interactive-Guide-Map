package edu.kcg.glenaldy.masterproject4_server.entity

import javax.persistence.*

@Entity(name = "place")
class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    var id: Long? = null

    @Column(name = "place_name")
    var name: String? = ""

    @Column(name = "place_identifier", unique = true)
    var identifier: String? = ""

    @Column(name = "place_pos")
    var pos: Pos? = Pos(0.0, 0.0)

    @CollectionTable(name = "place_area")
    @OrderColumn(name = "area_index")
    @ElementCollection
    var area: MutableList<Pos> = mutableListOf()

    @ManyToOne
    @JoinColumn(name = "type_id")
    var type: PlaceType? = null

    @Column(name = "place_zoom")
    var zoom: Long? = 10

    @ManyToOne
    @JoinColumn(name = "region_id")
    var region: Place? = null

    @ManyToOne
    @JoinColumn(name = "prefecture_id")
    var prefecture: Place? = null

    @ManyToOne
    @JoinColumn(name = "city_id")
    var city: Place? = null
}

@Embeddable
class Pos(var lat: Double? = 0.0,
          var lng: Double? = 0.0)



