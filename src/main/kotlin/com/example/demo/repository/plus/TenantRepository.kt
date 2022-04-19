package com.example.demo.repository.plus

import com.example.demo.domain.Tenant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface TenantRepository : JpaRepository<Tenant, Long> {

    @Query("FROM Tenant t ORDER BY t.name")
    fun findAllSorted(): List<Tenant>

    @Query("FROM Tenant t INNER JOIN t.owner c WHERE c.id IN (SELECT cu.id FROM Customer cu INNER JOIN cu.tenants tn WHERE tn.id = :tenantId) ORDER BY t.name")
    fun findForTenantIdSorted(tenantId : Long): List<Tenant>

    @Query("FROM Tenant t WHERE t.id=:id")
    fun getTenantById(id: Long): Tenant?

    @Query("SELECT t.id FROM Tenant t WHERE t.isAdmin=TRUE")
    fun getAdminTenantId(): Long?

    @Query("SELECT t FROM Tenant t WHERE t.owner.id IS NULL OR t.owner.id=:customerId ORDER BY t.name")
    fun findTenantsForLinkingToCustomer(customerId: Long): List<Tenant>

    @Query("SELECT t FROM Tenant t WHERE t.owner.id=:customerId ORDER BY t.name")
    fun findTenantsForCustomer(customerId: Long): List<Tenant>

    @Transactional
    @Modifying
    @Query(value = "UPDATE Tenant t SET t.isActive=false, t.deletedAt=NOW() WHERE t.isActive=true AND t.id NOT IN :ids")
    fun deactivateTenantWithIdNotIn(ids: List<Long>)
}