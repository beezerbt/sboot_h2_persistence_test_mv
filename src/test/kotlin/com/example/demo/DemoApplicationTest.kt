package com.example.demo

import com.example.demo.domain.BillingOrder
import com.example.demo.repository.BillingOrderRepository
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

@Profile("test")
@RunWith(SpringRunner::class)
@TestPropertySource(locations=["classpath:test.properties"])
@SpringBootTest
class DemoApplicationTest {
	private val log: Log = LogFactory.getLog(DemoApplicationTest::class.java)

	@Autowired
	val billingOrderRepository: BillingOrderRepository? = null

	@Test
	fun givenCUT_whenAutowired_thenItIsNotNull() {
		assertNotNull(billingOrderRepository)
	}

	@Test
	fun givenCUT_whenAValidBillingOrderIsCreated_thenItSavesItSuccessfullyLocally() {
		val billingOrder = BillingOrder()
		billingOrder.id=1
		billingOrder.billingOrderId="HCMOR433015839358976-1"
		billingOrder.ssid="HCMORa5861379072"
		billingOrder.billing_action="PROVIDE"
		billingOrder.processId="27682158"
		billingOrder.billingOrderRequest="should be XML but not for this test"

		billingOrderRepository!!.save(billingOrder)
		val matchingBillingOrder = billingOrderRepository!!.findByBillingOrderId("HCMOR433015839358976-1")
		assertNotNull(matchingBillingOrder)
		log.info("matchingBillingOrder::$matchingBillingOrder")
	}
}
