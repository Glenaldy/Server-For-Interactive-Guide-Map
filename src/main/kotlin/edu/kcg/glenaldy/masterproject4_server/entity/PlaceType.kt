package edu.kcg.glenaldy.masterproject4_server.entity

import javax.persistence.*

@Entity(name = "type")
class PlaceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    var id: Long? = null

    @Column(name = "type_name", unique = true)
    var name: String? = ""
}