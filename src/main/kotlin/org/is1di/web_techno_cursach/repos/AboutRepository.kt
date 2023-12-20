package org.is1di.web_techno_cursach.repos

import org.is1di.web_techno_cursach.domain.About
import org.springframework.data.jpa.repository.JpaRepository


interface AboutRepository : JpaRepository<About, Long>
