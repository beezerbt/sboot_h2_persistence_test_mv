package com.example.demo.repository.plus

import com.example.demo.domain.CareLevel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface CareLevelRepository : JpaRepository<CareLevel, Long>