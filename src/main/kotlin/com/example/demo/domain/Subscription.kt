package com.example.demo.domain

import java.time.LocalDate
import javax.persistence.*

/** Each subscription item represents a contract that a certain customer has for a cloud type in one of their tenants */
@Entity
@Table(name = "subscription")
class Subscription : GeneratedIdEntity() {
    @Column(name = "name", nullable = false, length = 255)
    var name: String = ""

    //PPU or RES (pay per use or reserved)
    @Column(name = "billing_type", nullable = false, length = 100)
    var billingType: String = ""

    //Activated or Failed or Ceased
    @Column(name = "billing_status", nullable = false, length = 100)
    var billingStatus: String = ""

    @Column(name = "contractid", nullable = false, length = 100)
    var contractId: String = ""

    @Column(name = "contract_start", nullable = false)
    var contractStartDate: LocalDate = LocalDate.now()

    @Column(name = "contract_officer", nullable = false, length = 100)
    var contractOfficer: String = ""

    @Column(name = "accountid", nullable = false, length = 100)
    var accountId: String = ""

    @Column(name = "discount_percent", nullable = false)
    var discountPercent: Int = 0

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true

    @Column(name = "is_default", nullable = false)
    var isDefault: Boolean = false

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    lateinit var customer: Customer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    lateinit var tenant: Tenant

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cloudtype_id", nullable = false)
    lateinit var cloudType: CloudType

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sla_id", nullable = false)
    lateinit var sla: Sla

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carelevel_id", nullable = false)
    lateinit var careLevel: CareLevel
}
