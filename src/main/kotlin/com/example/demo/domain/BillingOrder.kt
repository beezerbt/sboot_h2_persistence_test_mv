package com.example.demo.domain

import java.time.LocalDate
import javax.persistence.*

/** Represents one of the supported Service Level Agreement options. */
@Entity
@Table(name = "billingorder")
class BillingOrder () {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0L

    //e.g.HCMOR520978299453440-1
    @Column(name = "orderid", nullable = false, length = 100)
    var billingOrderId: String = ""

    //e.g. HCMORa8300502016
    @Column(name = "ssid", nullable = true, length = 100)
    var ssid: String = ""

    //PROVIDE or CEASE
    @Column(name = "action", nullable = false, length = 100)
    var billing_action: String = ""

    //UTC Date/Time of above action
    @Column(name = "installation_date", nullable = true)
    var installationDate: LocalDate = LocalDate.now()

    //e.g. 123456789015
    @Column(name = "processid", nullable = true, length = 100)
    var processId: String = ""

    //ACTIVATED or FAILED
    @Column(name = "status", nullable = true, length = 100)
    var billingStatus: String = ""

    //Order Request
    @Lob
    @Column(name = "order_request", nullable = false, length = 10000)
    var billingOrderRequest: String = ""

    //Order Response (concomitant)
    @Lob
    @Column(name = "order_response", nullable = true, length = 10000)
    var billingOrderResponse: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BillingOrder

        if (billingOrderId != other.billingOrderId) return false
        if (ssid != other.ssid) return false
        if (billing_action != other.billing_action) return false
        if (installationDate != other.installationDate) return false
        if (processId != other.processId) return false
        if (billingStatus != other.billingStatus) return false
        if (billingOrderRequest != other.billingOrderRequest) return false
        if (billingOrderResponse != other.billingOrderResponse) return false

        return true
    }

    override fun hashCode(): Int {
        var result = billingOrderId.hashCode()
        result = 31 * result + ssid.hashCode()
        result = 31 * result + billing_action.hashCode()
        result = 31 * result + installationDate.hashCode()
        result = 31 * result + processId.hashCode()
        result = 31 * result + billingStatus.hashCode()
        result = 31 * result + billingOrderRequest.hashCode()
        result = 31 * result + billingOrderResponse.hashCode()
        return result
    }

    override fun toString(): String {
        return "BillingOrder(id=$id, billingOrderId='$billingOrderId', ssid='$ssid', billing_action='$billing_action', installationDate=$installationDate, processId='$processId', billingStatus='$billingStatus', billingOrderRequest='$billingOrderRequest', billingOrderResponse='$billingOrderResponse')"
    }


}