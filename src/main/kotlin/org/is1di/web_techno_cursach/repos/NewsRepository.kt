package org.is1di.web_techno_cursach.repos

import org.is1di.web_techno_cursach.domain.News
import org.springframework.data.jpa.repository.JpaRepository


interface NewsRepository : JpaRepository<News, Long> {

    fun existsByTitleIgnoreCase(title: String?): Boolean

}
