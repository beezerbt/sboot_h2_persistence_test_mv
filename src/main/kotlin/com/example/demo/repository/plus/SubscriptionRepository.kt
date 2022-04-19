package com.example.demo.repository.plus

import com.example.demo.domain.Subscription
import org.springframework.context.annotation.Lazy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface SubscriptionRepository : JpaRepository<Subscription, Long>, ExtraSubscriptionRepository {
    @Query("FROM Subscription s LEFT JOIN FETCH s.customer LEFT JOIN FETCH s.tenant LEFT JOIN FETCH s.sla LEFT JOIN FETCH s.careLevel LEFT JOIN FETCH s.cloudType ORDER BY s.name")
    fun getAllSubscriptions(): List<Subscription>

    @Query("FROM Subscription s LEFT JOIN FETCH s.customer c LEFT JOIN FETCH s.tenant LEFT JOIN FETCH s.sla LEFT JOIN FETCH s.careLevel LEFT JOIN FETCH s.cloudType WHERE c.id IN (SELECT cu.id FROM Customer cu INNER JOIN cu.tenants tn WHERE tn.id = :tenantId) ORDER BY s.name")
    fun getSubscriptionsByTenantId(tenantId: Long): List<Subscription>

    @Query("FROM Subscription s LEFT JOIN FETCH s.customer LEFT JOIN FETCH s.tenant LEFT JOIN FETCH s.sla LEFT JOIN FETCH s.careLevel LEFT JOIN FETCH s.cloudType WHERE s.id=:id")
    fun getSubscriptionById(id: Long): Subscription?

    @Query("FROM Subscription s LEFT JOIN FETCH s.customer c LEFT JOIN FETCH s.tenant LEFT JOIN FETCH s.sla LEFT JOIN FETCH s.careLevel LEFT JOIN FETCH s.cloudType WHERE s.id=:id AND c.id IN (SELECT cu.id FROM Customer cu INNER JOIN cu.tenants tn WHERE tn.id = :tenantId)")
    fun getSubscriptionByIdAndTenantId(id: Long, tenantId: Long): Subscription?

    @Query("FROM Subscription s WHERE s.customer.id = :customerId AND s.cloudType.id = :cloudTypeId AND s.isDefault = true")
    fun getDefaultSubscriptions(customerId: Long, cloudTypeId: Long): Collection<Subscription>
}

interface ExtraSubscriptionRepository {
    fun setDefault(id: Long)
    fun saveSubscription(subscription: Subscription): Subscription
}

@Component
class ExtraSubscriptionRepositoryImpl(
    @Lazy private val subscriptionRepository: SubscriptionRepository
) : ExtraSubscriptionRepository {
    @Transactional
    override fun setDefault(id: Long) {
        val subscription = subscriptionRepository.getSubscriptionById(id)
        if (subscription != null) {
            val defaultSubs = subscriptionRepository.getDefaultSubscriptions(subscription.customer.id, subscription.cloudType.id)

            defaultSubs.forEach { defaultSub ->
                defaultSub.isDefault = false
                subscriptionRepository.save(defaultSub)
            }

            subscription.isDefault = true
            subscriptionRepository.save(subscription)
        }
    }

    @Transactional
    override fun saveSubscription(subscription: Subscription): Subscription {
        if (subscription.isNew) {
            val defaultSubs = subscriptionRepository.getDefaultSubscriptions(subscription.customer.id, subscription.cloudType.id)
            subscription.isDefault = defaultSubs.isEmpty()
        } else {
            val oldSubscription = subscriptionRepository.getSubscriptionById(subscription.id)
            subscription.isDefault = oldSubscription?.isDefault ?: false
        }
        return subscriptionRepository.save(subscription)
    }

    // TODO Q: delete/deactivate subscription --> which one becomes default? lowest id?
}