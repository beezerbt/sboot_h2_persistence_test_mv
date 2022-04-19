package com.example.demo

import com.example.demo.domain.*
import com.example.demo.repository.BillingOrderRepository
import com.example.demo.repository.plus.*
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Profile("test")
@RunWith(SpringRunner::class)
@TestPropertySource(locations=["classpath:test.properties"])
@SpringBootTest
class DemoApplicationTest {
	private val log: Log = LogFactory.getLog(DemoApplicationTest::class.java)

	@Autowired
	val billingOrderRepository: BillingOrderRepository? = null
	@Autowired
	val slaRepository: SlaRepository? = null
	@Autowired
	val careLevelRepository: CareLevelRepository? = null
	@Autowired
	val cloudTypeRepository: CloudTypeRepository? = null
	@Autowired
	val customerRepository: CustomerRepository? = null
	@Autowired
	val tenantRepository: TenantRepository? = null
	@Autowired
	val subscriptionRepository: SubscriptionRepository? = null

	@Test
	fun givenCUT_whenAutowired_thenItIsNotNull() {
		assertNotNull(billingOrderRepository)
		assertNotNull(slaRepository)
		assertNotNull(careLevelRepository)
		assertNotNull(cloudTypeRepository)
		assertNotNull(tenantRepository)
		assertNotNull(subscriptionRepository)
	}

	@Test
	fun givenCUT_whenAValidBillingOrderIsCreated_thenItSavesItSuccessfullyLocally() {
		//Setup Data
		addSLA()
		val matchingSLA = slaRepository!!.findById(1)
		assertNotNull(matchingSLA.get())
		addCareLevel()
		val matchingCareLevel = careLevelRepository!!.findById(1)
		assertNotNull(matchingCareLevel.get())
		addCloudType()
		val matchingCloudType = cloudTypeRepository!!.findById(1)
		assertNotNull(matchingCloudType.get())
		addCustomer()
		val matchingCustomer = customerRepository!!.findById(1L)
		assertNotNull(matchingCustomer.get())
		addTenant()
		val matchingTenant = tenantRepository!!.findById(1L)
		assertNotNull(matchingTenant.get())
		//TODO:Add Tenant to Customer now
		/*val customersTenant: MutableSet<Tenant>  = mutableSetOf()
		matchingCustomer.get().tenants.add(matchingTenant)*/

		//	fun addSubscription(customer: Customer, tenant: Tenant, cloudType: CloudType, sla: Sla, careLevel: CareLevel) {
		addSubscription(matchingCustomer, matchingTenant, matchingCloudType, matchingSLA, matchingCareLevel)
		val matchingSubscription = subscriptionRepository!!.findById(1L)
		assertNotNull(matchingSubscription)

		//CUT
		val billingOrder = BillingOrder()
		billingOrder.id=1
		billingOrder.billingOrderId="HCMOR433015839358976-1"
		billingOrder.ssid="HCMORa5861379072"
		billingOrder.billing_action="PROVIDE"
		billingOrder.processId="27682158"
		billingOrder.billingOrderRequest="should be XML but not for this test"
		billingOrder.subscription = matchingSubscription.get()

		billingOrderRepository!!.save(billingOrder)
		val matchingBillingOrder = billingOrderRepository!!.findByBillingOrderId("HCMOR433015839358976-1")
		assertNotNull(matchingBillingOrder)
		log.info("matchingBillingOrder::$matchingBillingOrder")
	}

	//(1, 'gold', 'Gold'),
	//(2, 'silver', 'Silver'),
	//(3, 'bronze', 'Bronze');
	fun addSLA() {
		require(slaRepository!=null)
		val sla = Sla()
		sla.key ="gold"
		sla.name ="Gold"
		slaRepository!!.save(sla)
	}
	//(1, 'no_care', 'No Care', 'There is no SLA'),
	//(2, 'reactive_care', 'Reactive Care', 'SLA on intervention time'),
	//(3, 'close_care', 'Close Care', 'SLA on restoration time'),
	//(4, 'full_care', 'Full Care', 'SLA on availability');
	fun addCareLevel() {
		require(careLevelRepository!=null)
		val careLevel = CareLevel()
		careLevel.key ="reactive_care"
		careLevel.name ="Reactive Care"
		careLevel.description ="SLA on intervention time"
		careLevelRepository!!.save(careLevel)
	}
	//(1, 'proximus_vmware', 'Proximus Private Cloud'),
	//(2, 'proximus_azure', 'Proximus Azure'),
	//(3, 'non_proximus', 'Other');
	fun addCloudType() {
		require(cloudTypeRepository!=null)
		val cloudType = CloudType()
		cloudType.key = "proximus_vmware"
		cloudType.description = "Proximus Private Cloud'"
		cloudTypeRepository!!.save(cloudType)
	}
	fun addCustomer() {
		require(customerRepository!=null)
		val customer = Customer()
		customer.cdbid = "1799683"
		customer.email = "devops@thecodingcompany.com"
		customer.isActive = true
		customer.name = "DevOps Intl."
		customer.trialEndDate = LocalDate.now()
		customerRepository!!.save(customer)
		//TODO::customer.tenants = emptyMutable<Tenant>()
	}
	fun addTenant() {
		require(tenantRepository!=null)
		val tenant = Tenant(1L)
		tenant.createdAt = LocalDateTime.now()
		tenant.isAdmin = false
		tenant.name = "Very IP"
		tenant.description = "Very IP Description"
		tenantRepository!!.save(tenant)
	}
	fun addSubscription(customer: Optional<Customer>, tenant: Optional<Tenant>, cloudType: Optional<CloudType>, sla: Optional<Sla>, careLevel: Optional<CareLevel>) {
		require(subscriptionRepository!=null)
		require(subscriptionRepository!=null)

		val subscription = Subscription()
		subscription.billingStatus = "Registered"
		subscription.billingType = "PPU"
		subscription.contractId = "100001203"
		subscription.contractStartDate = LocalDate.now()
		subscription.contractOfficer = "Leon the Great"
		subscription.accountId = "800128091"
		subscription.discountPercent = 0
		subscription.isActive = true
		subscription.isDefault = false
		subscription.customer = customer.get()
		subscription.tenant = tenant.get()
		subscription.cloudType = cloudType.get()
		subscription.sla = sla.get()
		subscription.careLevel = careLevel.get()

		subscriptionRepository!!.saveSubscription(subscription)
	}
}
