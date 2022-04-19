package com.example.demo.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/** Represents one of the supported Service Level Agreement options. */
@Entity
@Table(name = "carelevel")
class CareLevel : GeneratedIdEntity() {
    @Column(name = "idkey", nullable = false, length = 50)
    var key: String = ""

    @Column(name = "name", nullable = false, length = 100)
    var name: String = ""

    @Column(name = "description", nullable = false, length = 100)
    var description: String = ""
}