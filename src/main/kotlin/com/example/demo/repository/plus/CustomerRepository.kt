package com.example.demo.repository.plus

import com.example.demo.domain.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface CustomerRepository : JpaRepository<Customer, Long> {
    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.tenants WHERE c.id = :id")
    fun getCustomerById(id: Long): Customer?

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.tenants")
    fun getAllCustomers(): List<Customer>

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.tenants t WHERE t.id = :tenantId")
    fun getCustomersByTenantId(tenantId: Long): List<Customer>

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.tenants t WHERE c.id = :id AND t.id = :tenantId")
    fun getCustomerByIdAndTenantId(id: Long, tenantId: Long): Customer?
}