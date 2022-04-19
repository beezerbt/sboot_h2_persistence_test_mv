package com.example.demo.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/** Represent one of the cloud types a customer can request a subscription for */
@Entity
@Table(name = "cloudtype")
class CloudType : GeneratedIdEntity() {
    @Column(name = "idkey", nullable = false, length = 50)
    var key: String = ""

    @Column(name = "description", nullable = false, length = 100)
    var description: String = ""
}