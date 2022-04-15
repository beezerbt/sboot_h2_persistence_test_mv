package com.example.demo.repository

import com.example.demo.domain.BillingOrder
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface BillingOrderRepository : JpaRepository<BillingOrder, Long>{
    fun  findByBillingOrderId(billingOrderId:String): BillingOrder;
}