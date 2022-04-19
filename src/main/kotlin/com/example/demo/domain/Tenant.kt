package com.example.demo.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.LocalDateTime
import javax.persistence.*

/** A Morpheus account/tenant item, data needed in Plus replicated from Morpheus */
@Table(name = "tenant")
@Entity
class Tenant() : IdEntity() {
    constructor(id: Long) : this() {
        this.id = id
    }

    constructor(id: Long, name: String, description: String, createdAt: LocalDateTime, isActive: Boolean, isAdmin: Boolean) : this() {
        this.id = id
        this.name = name
        this.description = description
        this.createdAt = createdAt
        this.isActive = isActive
        this.isAdmin = isAdmin
    }

    @Column(name = "name", nullable = false, length = 255)
    var name: String = ""

    @Column(name = "description", nullable = false, length = 255)
    var description: String = ""

    @Column(name = "created_at", nullable = true)
    var createdAt: LocalDateTime? = null

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime? = null

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

    /** If isAdmin==true, this item is the (one and only) admin tenant */
    @Column(name = "is_admin", nullable = false)
    var isAdmin: Boolean = false

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "customer_id")
    var owner: Customer? = null
}