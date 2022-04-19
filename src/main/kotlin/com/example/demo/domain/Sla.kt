package com.example.demo.domain

import javax.persistence.*

/** Represents one of the supported Service Level Agreement options. */
@Entity
@Table(name = "sla")
class Sla  : GeneratedIdEntity() {
    @Column(name = "idkey", nullable = false, length = 50)
    var key: String = ""

    @Column(name = "name", nullable = false, length = 100)
    var name: String = ""
}