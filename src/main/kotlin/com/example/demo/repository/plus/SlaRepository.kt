package com.example.demo.repository.plus

import com.example.demo.domain.Sla
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface SlaRepository : JpaRepository<Sla, Long> {
}