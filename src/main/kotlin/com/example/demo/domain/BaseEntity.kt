package com.example.demo.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.domain.Persistable
import java.util.*
import javax.persistence.*

/** Base class for entities */
@MappedSuperclass
abstract class BaseEntity : Persistable<Long> {
    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if ((other == null) || (this.javaClass != other.javaClass)) return false
        val otherEntity: BaseEntity = other as BaseEntity
        return this.id == otherEntity.id
    }

    @JsonIgnore
    override fun isNew(): Boolean {
        // Having a nullable entity id is a PITA, but Hibernate sees id==null as "non persisted", so we need to tell H it's id==0 instead
        return id == 0L
    }
}

/** Base class for entities with an auto-generated 64-bit integer Id */
@MappedSuperclass
abstract class GeneratedIdEntity : BaseEntity() {
    // id is PK and generated with auto-increment on insert
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0L

    override fun getId(): Long? {
        return id
    }
}

/** Base class for entities with a provided 64-bit integer Id */
@MappedSuperclass
abstract class IdEntity : BaseEntity() {
    // id is PK and needs to be set before inserting
    @Id
    @Column(name = "id", nullable = false)
    var id: Long = 0L

    override fun getId(): Long? {
        return id
    }
}