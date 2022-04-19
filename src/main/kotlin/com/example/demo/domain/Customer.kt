package com.example.demo.domain

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.LocalDate
import javax.persistence.*

/** Each customer item contains basic information about a Cloud Fusion user, and which Morpheus tenants they have. */
@Entity
@Table(name = "customer")
class Customer : GeneratedIdEntity() {
  /*  TODO::@NotBlank
    @Size(max = 512)
*/    @Column(name = "name", nullable = false, length = 512, unique = true)
    var name: String = ""

/*    @NotBlank
    @Size(max = 20)*/
    @Column(name = "cdbid", nullable = false, length = 20)
    var cdbid: String = ""

/*    @NotBlank
    @Size(max = 100)*/
    @Column(name = "email", nullable = false, length = 100)
    var email: String = ""

//    @NotNull
    @Column(name = "trial_end", nullable = false)
    var trialEndDate: LocalDate = LocalDate.now()

//    @NotNull
/*    @Size(max = 10000)*/
    @Column(name = "description", nullable = false, length = 10000)
    var description: String = ""

//    @NotNull
    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

    /** List of tenants that are linked to a customer. A tenant can not be linked more than once to any customer. */
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    var tenants: MutableSet<Tenant> = mutableSetOf()
}
